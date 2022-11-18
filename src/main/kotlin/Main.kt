import picocli.CommandLine
import picocli.CommandLine.*
import kotlin.system.exitProcess

@Command(
    name = "wordle-solver",
    subcommands = [Solve::class, Load::class],
    version = ["1.0.2"],
    mixinStandardHelpOptions = true
)
class Main {
    @Spec
    lateinit var spec: Model.CommandSpec
}

fun main(args: Array<String>) {
    val exitCode = CommandLine(Main()).execute(*args)
    exitProcess(exitCode)
}
