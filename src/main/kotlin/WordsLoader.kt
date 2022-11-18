import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import org.jsoup.Jsoup
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneOffset
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import kotlin.math.floor


class WordsLoader(private val isDebug: Boolean = false) {

    data class Words(
        val meta: HashMap<String, Long> = hashMapOf(),
        val dict: HashMap<String, List<String>> = hashMapOf()
    ) : Serializable

    private var words = Words()

    fun init(lang: String) {
        if (!checkLangAndLoad(lang)) {
            println("Dictionary $lang is missing or outdated. Downloading...")
            load(lang)
        } else {
            loadFile()
        }
    }

    fun load(lang: String) {
        when (lang) {
            "by" -> loadBy()
            "ru" -> loadRu()
            "en" -> loadEn()
        }
        words.meta[lang] = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        if (isDebug) println("words meta: ${words.meta}")
        writeFile()
        println("Done!\n")
    }

    fun getWords(lang: String): List<String>? = words.dict[lang]

    private fun diffDays(date: Long): Int = Period.between(LocalDate.ofEpochDay(date), LocalDate.now()).days

    private fun checkLangAndLoad(lang: String): Boolean {
        if (fileExists()) {
            loadFile()
            if (getWords(lang)?.isNotEmpty() == true && words.meta.containsKey(lang)) return diffDays(words.meta[lang]!!) < 30
        }
        return false
    }

    private fun fileExists(): Boolean = File(ZIP_FILE_NAME).exists()

    private fun loadFile() {
        FileInputStream(ZIP_FILE_NAME).use { fis ->
            ZipInputStream(fis).use { zis ->
                zis.nextEntry
                ObjectInputStream(zis).use {
                    words = it.readObject() as Words
                }
            }
        }
    }

    private fun writeFile() {
        FileOutputStream(ZIP_FILE_NAME).use { fos ->
            ZipOutputStream(fos).use { zos ->
                zos.putNextEntry(ZipEntry(WORDS_FILE_NAME))
                ObjectOutputStream(zos).use { oos ->
                    oos.writeObject(words)
                }
            }
        }
        if (isDebug) println("File was written")
    }

    private fun loadBy() {
        val by = mutableListOf<String>()
        BY_ALPHABET.forEach {
            print("\rReceiving words: ${floor((BY_ALPHABET.indexOf(it).toDouble() / BY_ALPHABET.size * 100)).toInt()}%")
            val document = Jsoup.connect(BASE_BY_URL + it).get()
            document
                .select(".container-fluid .span8 .row-fluid .span10 ul.unstyled li a")
                .forEach { el ->
                    val word = el.text().lowercase()
                    if (word.length == 5 && word.contains("-").not()) by.add(word)
                }
        }
        print("\b\b\b100%\n")
        if (fileExists()) loadFile()
        words.dict["by"] = by
    }

    private fun loadRu() {
        println("Receiving words...")
        if (fileExists()) loadFile()
        words.dict["ru"] = parseRuEnDict(requestUrl(RU_URL))
        if (isDebug) println("words dict ru size: ${words.dict["ru"]?.size}")
    }

    private fun loadEn() {
        println("Receiving words...")
        if (fileExists()) loadFile()
        words.dict["en"] = parseRuEnDict(requestUrl(EN_URL))
    }

    private fun parseRuEnDict(data: String): List<String> {
        if (isDebug) println("Start of contents:\n${data.substring(0, 60)}")
        val subData = data.split("nouns'] = ")[1].replace("'", "\"")
        val size = subData.length
        val array = JSONParser().parse(subData.substring(0, size - 3) + "]") as JSONArray
        val filteredArray = mutableListOf<String>()
        for (i in array.indices) {
            val str = array[i] as String
            if (str.length == 5 && str.contains("-").not()) filteredArray.add(str)
        }
        if (isDebug) println("Parsed dict size: ${filteredArray.size}")
        return filteredArray
    }

    private fun requestUrl(urlString: String): String {
        val url = URL(urlString)
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        if (isDebug) println("Content Type: ${con.contentType}")
        val str = con.inputStream.readBytes().toString(Charsets.UTF_8)
        con.disconnect()
        return str
    }

    private companion object {
        const val ZIP_FILE_NAME = "words.zip"
        const val WORDS_FILE_NAME = "words"
        const val RU_URL = "https://sanstv.ru/words/dict_ru_nouns.js"
        const val EN_URL = "https://sanstv.ru/words/dict_en_nouns.js"
        const val BASE_BY_URL = "https://www.skarnik.by/litara/"
        val BY_ALPHABET = listOf(
            "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "І", "Й", "К", "Л", "М", "Н",
            "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Э", "Ю", "Я"
        )
    }
}
