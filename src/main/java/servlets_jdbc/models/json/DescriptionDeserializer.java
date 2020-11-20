package servlets_jdbc.models.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

interface DescriptionDeserializer {
    default List<String> nodeToListOfStrings(JsonNode givenNode, String requiredField, String separator) {
        JsonNode node;
        String nodeText;
        String[] textArray;
        if ((node = givenNode.get(requiredField)) != null) {
            if (node.isArray()) {
                return new ObjectMapper().convertValue(node, ArrayList.class);
            } else if (node.isTextual() && !(nodeText = node.asText()).isBlank()) {
                if ((textArray = nodeText.split(separator)).length > 1) {
                    return Arrays.asList(textArray.clone());
                } else {
                    return List.of(nodeText);
                }
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

    default List<String> nodeToListOfStrings(JsonNode givenNode, String requiredField) {
        return nodeToListOfStrings(givenNode, requiredField, "\\s");
    }
}
