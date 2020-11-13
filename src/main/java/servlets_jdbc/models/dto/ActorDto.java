package servlets_jdbc.models.dto;

import servlets_jdbc.models.Actor;

import java.util.List;
import java.util.stream.Collectors;

public class ActorDto {
    public static Actor from(Actor actor) {
        return new Actor(actor.getId(), actor.getFullname(), SimplifiedDescription.simplify(actor.getDescription()));
    }

    public static List<Actor> from(List<Actor> actors) {
        return actors.stream().map(ActorDto::from).collect(Collectors.toList());
    }


    private static class SimplifiedDescription {
        public static Actor.Description simplify(Actor.Description description) {
            return new Actor.Description(description.getImg(), description.getGenres());
        }
    }
}
