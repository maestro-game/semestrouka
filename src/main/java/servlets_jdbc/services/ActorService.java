package servlets_jdbc.services;

import servlets_jdbc.models.Actor;

import java.util.List;

public interface ActorService {
    Actor getActorById(Long actorId);

    List<Actor> getActors();

    Actor addActor(Actor actor);
}
