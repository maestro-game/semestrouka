package servlets_jdbc.services;

import servlets_jdbc.models.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FilterValidator {

    private boolean valid = false;

    public FilterValidator validate(Map<String, String[]> filters) {
        if (filters.containsKey("genres")
                || filters.containsKey("directors")
                || filters.containsKey("actors")) {
            valid = true;
        }
        return this;
    }

    public Optional<List<Film>> then(List<Film> films) {
        return valid ? Optional.of(films) : Optional.empty();
    }
}
