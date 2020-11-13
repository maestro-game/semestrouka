package servlets_jdbc.models.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import servlets_jdbc.models.Actor;

import java.io.IOException;
import java.util.*;

public class ActorDescriptionDeserializer extends StdDeserializer<Actor.Description> {
    protected ActorDescriptionDeserializer(Class<?> vc) {
        super(vc);
    }

    ActorDescriptionDeserializer() {
        this(null);
    }

    @Override
    public Actor.Description deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String img = node.get("img").asText();
        Integer yearB = node.get("yearB").asInt();
        Integer yearD = Optional.ofNullable(node.get("yearD")).orElse(IntNode.valueOf(-1)).asInt();
        String town = node.get("town").asText();
        JsonNode director = Optional.ofNullable(node.get("isDirector")).orElse(BooleanNode.FALSE);
        Boolean isDirector = director.isTextual() && director.asText().equals("on")
                ? Boolean.TRUE : director.isBoolean() ? director.asBoolean() : director.asBoolean();
        List<String> awardsList = nodeToListOfStrings(node, "awards");
        List<String> genresList = nodeToListOfStrings(node, "genres");
        return new Actor.Description(img, yearB, yearD, town, isDirector, genresList, awardsList);
    }

    private List<String> nodeToListOfStrings(JsonNode givenNode, String requiredField) {
        JsonNode node;
        String nodeText;
        String[] textArray;
        if ((node = givenNode.get(requiredField)) != null) {
            if (node.isArray()) {
                return new ObjectMapper().convertValue(node, ArrayList.class);
            } else if (node.isTextual() && !(nodeText = node.asText()).isBlank()) {
                System.out.println("is textual and not blank");
                if ((textArray = nodeText.split("\\s")).length > 1) {
                    System.out.println("length is more than 1");
                    return Arrays.asList(textArray.clone());
                } else {
                    return List.of(nodeText);
                }
            } else {
                System.out.println("HERE");
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

}
