package learnyouakotlin.part3

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS
import com.fasterxml.jackson.databind.node.*

private val nodes = JsonNodeFactory.instance
private val stableMapper = ObjectMapper().enable(INDENT_OUTPUT, ORDER_MAP_ENTRIES_BY_KEYS)



infix fun String.of(textValue: String) = this.of(TextNode(textValue))

infix fun String.of(intValue: Int) = this.of(IntNode(intValue))

infix fun String.of(value: JsonNode) = this to value

fun obj(props: Iterable<Pair<String, JsonNode>?>) = ObjectNode(nodes).apply {
    props.filterNotNull().forEach {
        set(it.first, it.second)
    }
}

fun obj(vararg props: Pair<String, JsonNode>?) = obj(props.toList())

fun array(elements: Iterable<JsonNode>) = ArrayNode(nodes).apply {
    elements.forEach { add(it) }
}

fun <T> array(elements: List<T>, fn: (T) -> JsonNode) = array(elements.map(fn))

fun toStableJsonString(n: JsonNode): String = stableMapper.writeValueAsString(n)