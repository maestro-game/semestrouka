package servlets_jdbc.services;

import servlets_jdbc.models.Actor;
import servlets_jdbc.repositories.ActorRepository;

import java.util.Collections;
import java.util.List;

public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Actor getActorById(Long actorId) {
        return actorRepository.findActorById(actorId).orElseThrow(
                () -> new IllegalArgumentException("Wrong actor id")
        );
    }

    @Override
    public List<Actor> getActors() {
        return actorRepository.findAllActors().orElse(Collections.emptyList());
    }

    @Override
    public Actor addActor(Actor actor) {
        return actorRepository.saveActor(actor).orElseThrow(
                () -> new IllegalArgumentException("Can't save actor")
        );
    }
}
