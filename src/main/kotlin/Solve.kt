import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    name = "solve",
    description = ["Solve"],
    sortOptions = false,
    version = ["1.0.0"],
    mixinStandardHelpOptions = true
)
class Solve : Runnable {
    @Option(
        names = ["-l", "--lang"],
        required = true,
        completionCandidates = LangCandidates::class,
        description = ["2-letter language code. Candidates: \${COMPLETION-CANDIDATES}"]
    )
    lateinit var lang: String

    @Option(names = ["-i", "--include"], split = ",", description = ["Included chars"])
    val include: List<Char> = listOf()

    @Option(names = ["-e", "--exclude"], split = ",", description = ["Excluded chars"])
    val exclude: List<Char> = listOf()

    @Option(names = ["-c1", "--char1"], description = ["Known char 1"])
    var char1: Char? = null

    @Option(names = ["-c2", "--char2"], description = ["Known char 2"])
    var char2: Char? = null

    @Option(names = ["-c3", "--char3"], description = ["Known char 3"])
    var char3: Char? = null

    @Option(names = ["-c4", "--char4"], description = ["Known char 4"])
    var char4: Char? = null

    @Option(names = ["-c5", "--char5"], description = ["Known char 5"])
    var char5: Char? = null

    @Option(names = ["-n1", "--not1"], split = ",", description = ["Chars not in 1st position"])
    val not1: List<Char> = listOf()

    @Option(names = ["-n2", "--not2"], split = ",", description = ["Chars not in 2nd position"])
    val not2: List<Char> = listOf()

    @Option(names = ["-n3", "--not3"], split = ",", description = ["Chars not in 3rd position"])
    val not3: List<Char> = listOf()

    @Option(names = ["-n4", "--not4"], split = ",", description = ["Chars not in 4th position"])
    val not4: List<Char> = listOf()

    @Option(names = ["-n5", "--not5"], split = ",", description = ["Chars not in 5th position"])
    val not5: List<Char> = listOf()

    override fun run() {
        printInput(
            lang, include, exclude,
            char1, char2, char3, char4, char5,
            not1, not2, not3, not4, not5
        )
        val wordsLoader = WordsLoader()
        wordsLoader.init(lang)
        val matchedWords = wordsLoader.getWords(lang)!!
            .asSequence()
            .filter { w -> include.all { w.contains(it) } }
            .filter { w -> exclude.any { w.contains(it) }.not() }
            .filter { w -> char1?.let { w[0] == it } ?: true }
            .filter { w -> char2?.let { w[1] == it } ?: true }
            .filter { w -> char3?.let { w[2] == it } ?: true }
            .filter { w -> char4?.let { w[3] == it } ?: true }
            .filter { w -> char5?.let { w[4] == it } ?: true }
            .filter { w -> not1.any { w[0] == it }.not() }
            .filter { w -> not2.any { w[1] == it }.not() }
            .filter { w -> not3.any { w[2] == it }.not() }
            .filter { w -> not4.any { w[3] == it }.not() }
            .filter { w -> not5.any { w[4] == it }.not() }
            .toList()
            .shuffled()
        printList(matchedWords)
    }
}
