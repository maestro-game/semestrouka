package servlets_jdbc.services.security.models;

import java.util.Map;

public class ErrorMapPair<T extends Boolean, V extends Map<?, ?>> {
    private T errors;
    private V map;

    public ErrorMapPair() {
    }

    public T hasErrors() {
        return errors;
    }

    public void errors(T state) {
        this.errors = state;
    }

    public V getMap() {
        return map;
    }

    public void setMap(V map) {
        this.map = map;
    }
}
