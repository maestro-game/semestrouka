package servlets_jdbc.models;

import java.util.List;

public class Film {

    private final String name;
    private final Description description;

    public Film(String name, Description description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public static class Description {
        private String img;
        private List<String> genres;

        public Description() {
        }

        public Description(String img, List<String> genres) {
            this.img = img;
            this.genres = genres;
        }

        public String getImg() {
            return img;
        }

        public List<String> getGenres() {
            return genres;
        }
    }
}
