package learnyouakotlin.part3

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import learnyouakotlin.part1.Presenter
import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots

fun sessionToJson(session: Session): JsonNode {
    return obj(
        prop("title", session.title),
        if (session.subtitle == null) null else prop("subtitle", session.subtitle),
        prop(
            "slots", obj(
            prop("first", session.slots.start),
            prop("last", session.slots.endInclusive)
        )),
        prop("presenters", array(session.presenters, Presenter::toJson)))
}

fun sessionFromJson(json: JsonNode): Session {
    val title = nonBlankText(json.path("title"))
    val subtitle = optionalNonBlankText(json.path("subtitle"))

    val authorsNode = json.path("presenters")
    val presenters = authorsNode
        .map(JsonNode::toPresenter)
    return Session(title, subtitle, Slots(1, 2), presenters)
}

private fun Presenter.toJson(): JsonNode = obj(prop("name", name))

private fun JsonNode.toPresenter() = Presenter(path("name").asText())

private fun optionalNonBlankText(node: JsonNode): String? {
    return node.takeUnless {it.isMissingNode}?.let { nonBlankText(it) }
}

private fun nonBlankText(node: JsonNode): String {
    val text = node.asText()
    return if (node.isNull || text == "") throw JsonMappingException(null, "missing or empty text") else text
}
