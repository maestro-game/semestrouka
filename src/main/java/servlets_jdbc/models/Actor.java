package servlets_jdbc.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import servlets_jdbc.models.json.ActorDescriptionDeserializer;

import java.util.List;
import java.util.Objects;

public class Actor {
    private Long id;
    private String fullname;
    private Description description;

    public Actor(String fullname, Description description) {
        this.fullname = fullname;
        this.description = description;
    }

    public Actor(Long id, String fullname, Description description) {
        this.id = id;
        this.fullname = fullname;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(getId(), actor.getId()) &&
                Objects.equals(getFullname(), actor.getFullname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullname());
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @JsonDeserialize(using = ActorDescriptionDeserializer.class)
    public static class Description {
        private String img;
        private Integer yearB;
        private Integer yearD;
        private String town;
        @JsonProperty("isDirector")
        private Boolean isDirector;
        private List<String> genres;
        private List<String> awards;

        public Description(String img, Integer yearB, Integer yearD, String town, Boolean isDirector, List<String> genres, List<String> awards) {
            this.img = img;
            this.yearB = yearB;
            this.yearD = yearD;
            this.town = town;
            this.isDirector = isDirector;
            this.genres = genres;
            this.awards = awards;
        }

        public Description(String img, List<String> genres) {
            this.img = img;
            this.genres = genres;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Integer getYearB() {
            return yearB;
        }

        public void setYearB(Integer yearB) {
            this.yearB = yearB;
        }

        public Integer getYearD() {
            return yearD;
        }

        public void setYearD(Integer yearD) {
            this.yearD = yearD;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public Boolean getDirector() {
            return isDirector;
        }

        public void setDirector(Boolean director) {
            isDirector = director;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
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
                    Objects.equals(getYearB(), that.getYearB()) &&
                    Objects.equals(getYearD(), that.getYearD()) &&
                    Objects.equals(getTown(), that.getTown()) &&
                    Objects.equals(isDirector, that.isDirector) &&
                    Objects.equals(getGenres(), that.getGenres()) &&
                    Objects.equals(getAwards(), that.getAwards());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getImg(), getYearB(), getYearD(), getTown(), isDirector, getGenres(), getAwards());
        }

        @Override
        public String toString() {
            return "Description{" +
                    "img='" + img + '\'' +
                    ", yearB=" + yearB +
                    ", yearD=" + yearD +
                    ", town='" + town + '\'' +
                    ", isDirector=" + isDirector +
                    ", genresList=" + genres +
                    ", awardsList=" + awards +
                    '}';
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String fullname;
        private Description description;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public Builder description(Description description) {
            this.description = description;
            return this;
        }

        public Actor build() {
            return new Actor(id, fullname, description);
        }
    }
}
