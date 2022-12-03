fun printInput(
    lang: String,
    include: List<Char>,
    exclude: List<Char>,
    char1: Char?,
    char2: Char?,
    char3: Char?,
    char4: Char?,
    char5: Char?,
    not1: List<Char>,
    not2: List<Char>,
    not3: List<Char>,
    not4: List<Char>,
    not5: List<Char>,
) {
    println("${COLOR_BLUE}Input: {")
    println("  lang:$COLOR_RESET $lang")
    if (include.isNotEmpty()) println("$COLOR_BLUE  Included chars:$COLOR_RESET $include")
    if (exclude.isNotEmpty()) println("$COLOR_BLUE  Excluded chars:$COLOR_RESET $exclude")
    if (char1 != null) println("$COLOR_BLUE  Char1:$COLOR_RESET $char1")
    if (char2 != null) println("$COLOR_BLUE  Char2:$COLOR_RESET $char2")
    if (char3 != null) println("$COLOR_BLUE  Char3:$COLOR_RESET $char3")
    if (char4 != null) println("$COLOR_BLUE  Char4:$COLOR_RESET $char4")
    if (char5 != null) println("$COLOR_BLUE  Char5:$COLOR_RESET $char5")
    if (not1.isNotEmpty()) println("$COLOR_BLUE  Chars not in 1st position:$COLOR_RESET $not1")
    if (not2.isNotEmpty()) println("$COLOR_BLUE  Chars not in 2nd position:$COLOR_RESET $not2")
    if (not3.isNotEmpty()) println("$COLOR_BLUE  Chars not in 3rd position:$COLOR_RESET $not3")
    if (not4.isNotEmpty()) println("$COLOR_BLUE  Chars not in 4th position:$COLOR_RESET $not4")
    if (not5.isNotEmpty()) println("$COLOR_BLUE  Chars not in 5th position:$COLOR_RESET $not5")
    println("${COLOR_BLUE}}$COLOR_RESET\n")
}

fun printList(list: List<String>) {
    if (list.isEmpty()) {
        return println("${COLOR_RED}No matched words found$COLOR_RESET")
    }
    println("${COLOR_BLUE}Matched words: [$COLOR_RESET")
    val chunked = list.chunked(PRINT_SIZE_COLUMNS)
    val printSize = Integer.min(PRINT_SIZE_ROWS - 1, chunked.size - 1)
    for (i in 0..printSize) {
        val row = chunked[i].joinToString(", ")
        if (i == printSize) print(row) else println(row)
    }
    if (chunked.size > PRINT_SIZE_ROWS) {
        val moreItemsCount = list.size - (PRINT_SIZE_ROWS * PRINT_SIZE_COLUMNS)
        println(" ...\n$COLOR_BLUE] $moreItemsCount more items$COLOR_RESET")
    } else println("\n${COLOR_BLUE}]$COLOR_RESET")
}

// Everything after this is in red
private const val COLOR_RED = "\u001b[31m"
// Everything after this is in blue
private const val COLOR_BLUE = "\u001b[34m"
// Resets previous color codes
private const val COLOR_RESET = "\u001b[0m"

private const val PRINT_SIZE_COLUMNS = 7
private const val PRINT_SIZE_ROWS = 9
