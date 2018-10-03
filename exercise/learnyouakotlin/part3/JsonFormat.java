package learnyouakotlin.part3;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import learnyouakotlin.part1.Presenter;
import learnyouakotlin.part1.Session;
import learnyouakotlin.part1.Slots;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;
import static learnyouakotlin.part3.Json.*;

public class JsonFormat {
    public static JsonNode sessionToJson(Session session) {
        return obj(
            prop("title", session.title),
            session.subtitle == null ? null : prop("subtitle", session.subtitle),
            prop("slots", obj(
                prop("first", session.slots.start),
                prop("last", session.slots.endInclusive)
            )),
            prop("presenters", array(session.presenters, JsonFormat::presenterToJson)));
    }

    public static Session sessionFromJson(JsonNode json) throws JsonMappingException {
        String title = nonBlankText(json.path("title"));
        @Nullable String subtitle = optionalNonBlankText(json.path("subtitle"));

        JsonNode authorsNode = json.path("presenters");
        List<Presenter> presenters = stream(spliterator(authorsNode::elements), false)
            .map(JsonFormat::presenterFromJson)
            .collect(Collectors.toList());

        return new Session(title, subtitle, new Slots(1, 2), presenters);

    }

    private static Spliterator<JsonNode> spliterator(Iterable<JsonNode> elements) {
        return elements.spliterator();
    }

    private static ObjectNode presenterToJson(Presenter p) {
        return obj(prop("name", p.getName()));
    }

    private static Presenter presenterFromJson(JsonNode authorNode) {
        return new Presenter(authorNode.path("name").asText());
    }

    private static
    @Nullable
    String optionalNonBlankText(JsonNode node) throws JsonMappingException {
        if (node.isMissingNode()) {
            return null;
        } else {
            return nonBlankText(node);
        }
    }

    private static String nonBlankText(JsonNode node) throws JsonMappingException {
        String text = node.asText();
        if (node.isNull() || Objects.equals(text, "")) {
            throw new JsonMappingException(null, "missing or empty text");
        } else {
            return text;
        }
    }
}
