import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    name = "load",
    description = ["Load words from web"],
    sortOptions = false,
    version = ["1.0.3"],
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

    @Option(
        names = ["-d", "--debug"],
    )
    var isDebug: Boolean = false

    override fun run() {
        val wordsLoader = WordsLoader(isDebug)
        wordsLoader.load(lang)
    }
}
