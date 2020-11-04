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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Boolean isDirector = Optional.ofNullable(node.get("isDirector")).orElse(BooleanNode.FALSE).asBoolean();
        JsonNode awards;
        List<String> awardsArray = ((awards = node.get("awards")) != null && awards.isArray())
                ? new ObjectMapper().convertValue(awards, ArrayList.class)
                : Collections.emptyList();
        JsonNode genres;
        List<String> genresArray = ((genres = node.get("genres")) != null && genres.isArray())
                ? new ObjectMapper().convertValue(genres, ArrayList.class)
                : Collections.emptyList();

        return new Actor.Description(img, yearB, yearD, town, isDirector, genresArray, awardsArray);
    }

}
