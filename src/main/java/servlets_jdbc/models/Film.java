package servlets_jdbc.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import servlets_jdbc.models.json.FilmDescriptionDeserializer;

import java.util.List;
import java.util.Objects;

public class Film {

    private final String name;
    private final Description description;
    private final Long id;

    public Film(String name, Description description, Long id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Film(String name, Description description) {
        this.id = null;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return Objects.equals(getName(), film.getName()) &&
                Objects.equals(getDescription(), film.getDescription()) &&
                Objects.equals(getId(), film.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getId());
    }

    @JsonDeserialize(using = FilmDescriptionDeserializer.class)
    public static class Description {
        private String img;
        private Integer year = null;
        private List<String> genres;
        private List<String> awards;
        private List<String> actors;

        public Description() {
        }

        public Description(String img, Integer year,
                           List<String> genres, List<String> actors, List<String> awards) {
            this.img = img;
            this.year = year;
            this.genres = genres;
            this.actors = actors;
            this.awards = awards;
        }

        public Description(Integer year, List<String> genres, List<String> actors, List<String> awards) {
            this.year = year;
            this.genres = genres;
            this.actors = actors;
            this.awards = awards;
        }

        public String getImg() {
            return img;
        }

        public List<String> getGenres() {
            return genres;
        }

        public List<String> getActors() {
            return actors;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public void setActors(List<String> actors) {
            this.actors = actors;
        }


        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public List<String> getAwards() {
            return awards;
        }

        public void setAwards(List<String> awards) {
            this.awards = awards;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Description)) return false;
            Description that = (Description) o;
            return Objects.equals(getImg(), that.getImg()) &&
                    Objects.equals(getYear(), that.getYear()) &&
                    Objects.equals(getGenres(), that.getGenres()) &&
                    Objects.equals(getAwards(), that.getAwards()) &&
                    Objects.equals(getActors(), that.getActors());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getImg(), getYear(), getGenres(), getAwards(), getActors());
        }

        @Override
        public String toString() {
            return "Description{" +
                    "img='" + img + '\'' +
                    ", year=" + year +
                    ", genres=" + genres +
                    ", awards=" + awards +
                    ", actors=" + actors +
                    '}';
        }
    }
}
