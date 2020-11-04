package servlets_jdbc.models.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import servlets_jdbc.models.Actor;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActorDescriptionDeserializerTest {

    private final static String json =
            "{ " +
                    "\"img\": \"https://www.kinopoisk.ru/name/26070/photos/\", " +
                    "\"yearB\": 1962, " +
                    "\"town\": \"Los Angeles\" " +
                    "}";

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void testActorDescriptionDeserializer() throws JsonProcessingException {
        assertEquals(
                new ObjectMapper().readValue(json, Actor.Description.class),
                new Actor.Description(
                        "https://www.kinopoisk.ru/name/26070/photos/",
                        1962,
                        -1,
                        "Los Angeles",
                        false,
                        Collections.emptyList(), Collections.emptyList()
                )
        );
    }

    @Test
    void testActorParser() throws JsonProcessingException {
        when(request.getParameter("fullname")).thenReturn("Actor1");
        assertEquals(
                new Actor(
                        request.getParameter("fullname"),
                        new ObjectMapper().readValue(json, Actor.Description.class)
                ),
                new Actor(
                        "Actor1",
                        new Actor.Description(
                                "https://www.kinopoisk.ru/name/26070/photos/",
                                1962,
                                -1,
                                "Los Angeles",
                                false,
                                Collections.emptyList(), Collections.emptyList()
                        )
                )
        );
    }
}
