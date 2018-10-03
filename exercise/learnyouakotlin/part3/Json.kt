package learnyouakotlin.part3

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS
import com.fasterxml.jackson.databind.node.*
import java.util.stream.Collectors.toList

object Json {
    private val nodes = JsonNodeFactory.instance
    private val stableMapper = ObjectMapper().enable(INDENT_OUTPUT, ORDER_MAP_ENTRIES_BY_KEYS)

    fun prop(name: String, textValue: String): Pair<String, JsonNode> {
        return prop(name, TextNode(textValue))
    }

    fun prop(name: String, intValue: Int): Pair<String, JsonNode> {
        return prop(name, IntNode(intValue))
    }

    fun prop(name: String, value: JsonNode): Pair<String, JsonNode> {
        return Pair(name, value)
    }

    fun obj(props: Iterable<Pair<String, JsonNode>?>) = ObjectNode(nodes).apply {
        props.filterNotNull().forEach {
            set(it.first, it.second)
        }
    }

    fun obj(vararg props: Pair<String, JsonNode>?): ObjectNode = obj(props.toList())

    fun array(elements: Iterable<JsonNode>): ArrayNode {
        val array = ArrayNode(nodes)
        elements.forEach { array.add(it) }
        return array
    }

    fun <T> array(elements: List<T>, fn: (T) -> JsonNode): ArrayNode {
        return array(elements.stream().map(fn).collect(toList()))
    }

    fun toStableJsonString(n: JsonNode): String {
        try {
            return stableMapper.writeValueAsString(n)
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("failed to convert JsonNode to JSON string", e)
        }

    }
}
