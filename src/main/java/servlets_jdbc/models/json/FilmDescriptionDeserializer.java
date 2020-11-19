package servlets_jdbc.models.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import servlets_jdbc.models.Film;

import java.io.IOException;
import java.util.List;

public class FilmDescriptionDeserializer extends StdDeserializer<Film.Description> implements DescriptionDeserializer {
    protected FilmDescriptionDeserializer(Class<?> vc) {
        super(vc);
    }

    FilmDescriptionDeserializer() {
        this(null);
    }

    @Override
    public Film.Description deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String img = null;
        if (node.has("img")) {
            img = node.get("img").asText();
        }
        Integer year = node.get("year").asInt();
        if (year == 0) {
            year = null;
        }
        List<String> genresList = nodeToListOfStrings(node, "genres");
        List<String> actorsList = nodeToListOfStrings(node, "actors", ",");
        List<String> awardsList = nodeToListOfStrings(node, "awards");
        if (img != null) {
//            Adding film
            return new Film.Description(img, year, genresList, actorsList, awardsList);
        } else {
//            Filtering
            return new Film.Description(year, genresList, actorsList, awardsList);
        }
    }
}
