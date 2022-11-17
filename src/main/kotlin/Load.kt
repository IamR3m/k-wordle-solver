import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    name = "load",
    description = ["Load words from web"],
    sortOptions = false,
    version = ["1.0.0"],
    mixinStandardHelpOptions = true
)
class Load : Runnable {
    @Option(
        names = ["-l", "--lang"],
        required = true,
        completionCandidates = LangCandidates::class,
        description = ["2-letter language code. Candidates: \${COMPLETION-CANDIDATES}"]
    )
    lateinit var lang: String

    override fun run() {
        val wordsLoader = WordsLoader()
        wordsLoader.load(lang)
    }
}
