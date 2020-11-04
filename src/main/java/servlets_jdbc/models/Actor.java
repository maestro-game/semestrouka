package servlets_jdbc.models;

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
        private Boolean isDirector;
        private List<String> genresArray;
        private List<String> awardsArray;

        public Description(String img, Integer yearB, Integer yearD, String town, Boolean isDirector, List<String> genresArray, List<String> awardsArray) {
            this.img = img;
            this.yearB = yearB;
            this.yearD = yearD;
            this.town = town;
            this.isDirector = isDirector;
            this.genresArray = genresArray;
            this.awardsArray = awardsArray;
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

        public List<String> getGenresArray() {
            return genresArray;
        }

        public void setGenresArray(List<String> genresArray) {
            this.genresArray = genresArray;
        }

        public List<String> getAwardsArray() {
            return awardsArray;
        }

        public void setAwardsArray(List<String> awardsArray) {
            this.awardsArray = awardsArray;
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
                    Objects.equals(getGenresArray(), that.getGenresArray()) &&
                    Objects.equals(getAwardsArray(), that.getAwardsArray());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getImg(), getYearB(), getYearD(), getTown(), isDirector, getGenresArray(), getAwardsArray());
        }
    }
}
