package servlets_jdbc.models.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import servlets_jdbc.models.Film;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilmDescriptionDeserializer extends StdDeserializer<Film.Description> {
    protected FilmDescriptionDeserializer(Class<?> vc) {
        super(vc);
    }

    FilmDescriptionDeserializer() {
        this(null);
    }

    @Override
    public Film.Description deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String img = node.get("img").asText();
        Integer year = node.get("year").asInt();
        JsonNode awards;
        List<String> awardsList = ((awards = node.get("awards")) != null && awards.isArray())
                ? new ObjectMapper().convertValue(awards, ArrayList.class)
                : Collections.emptyList();
        JsonNode genres;
        List<String> genresList = ((genres = node.get("genres")) != null && genres.isArray())
                ? new ObjectMapper().convertValue(genres, ArrayList.class)
                : Collections.emptyList();
        JsonNode actors;
        List<String> actorsList = ((actors = node.get("actors")) != null && actors.isArray())
                ? new ObjectMapper().convertValue(actors, ArrayList.class)
                : Collections.emptyList();
        return new Film.Description(img, year, genresList, actorsList, awardsList);
    }
}
