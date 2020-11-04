package servlets_jdbc.models.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import servlets_jdbc.models.Film;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmDescriptionDeserializerTest {

    private final static String json =
            "{ " +
                    "\"img\": \"https://www.kinopoisk.ru/name/26070/photos/\", " +
                    "\"year\": 1962 " +
                    "}";

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void testFilmDescriptionDeserializer() throws JsonProcessingException {
        assertEquals(
                new ObjectMapper().readValue(json, Film.Description.class),
                new Film.Description("https://www.kinopoisk.ru/name/26070/photos/", 1962,
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
        );
    }

    @Test
    void testFilmParser() throws JsonProcessingException {
        when(request.getParameter("name")).thenReturn("Film1");
        assertEquals(
                new Film(
                        request.getParameter("name"),
                        new ObjectMapper().readValue(json, Film.Description.class)
                ),
                new Film(
                        "Film1",
                        new Film.Description(
                                "https://www.kinopoisk.ru/name/26070/photos/", 1962,
                                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
                        )
                )
        );
    }

}
