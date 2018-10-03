@file: JvmName("Sessions")

package learnyouakotlin.part2

import learnyouakotlin.part1.Session


fun findWithTitle(sessions: List<Session>, title: String): Session? {
    return sessions.stream().filter { (title1) -> title1.equals(title, ignoreCase = true) }.findFirst().orElse(null)
}

fun Session.subtitleOrPrompt() = subtitle ?: "click to enter subtitle"
