@file: JvmName("Sessions")

package learnyouakotlin.part2

import learnyouakotlin.part1.Session


fun List<Session>.findWithTitle(other: String): Session? {
    return firstOrNull { it.title.equals(other, ignoreCase = true) }
}

fun Session.subtitleOrPrompt() = subtitle ?: "click to enter subtitle"
