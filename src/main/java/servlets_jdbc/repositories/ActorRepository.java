package servlets_jdbc.repositories;

import servlets_jdbc.models.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorRepository {
    Optional<Actor> findActorById(Long actorId);

    Optional<List<Actor>> findAllActors();

    Optional<Actor> saveActor(Actor actor);
}
