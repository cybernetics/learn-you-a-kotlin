package learnyouakotlin.part3

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import learnyouakotlin.part1.Presenter
import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots

fun Session.toJson() =
    obj(
        "title" of title,
        if (subtitle == null) null else "subtitle" of subtitle,
        "slots" of obj(
            "first" of slots.start,
            "last" of slots.endInclusive
        ),
        "presenters" of array(presenters, Presenter::toJson))


fun JsonNode.toSession() =
    Session(
        title = path("title").nonBlankText(),
        subtitle = path("subtitle").optionalNonBlankText(),
        slots = Slots(1, 2),
        presenters = path("presenters").map(JsonNode::toPresenter))

private fun Presenter.toJson(): JsonNode = obj("name" of name)

private fun JsonNode.toPresenter() = Presenter(path("name").asText())

private fun JsonNode.optionalNonBlankText() = takeUnless { it.isMissingNode }?.nonBlankText()

private fun JsonNode.nonBlankText() = asText().let {
    if (it.isNullOrBlank()) throw JsonMappingException(null, "missing or empty text") else it
}
