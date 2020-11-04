package servlets_jdbc.models.forms;

import com.fasterxml.jackson.annotation.JsonValue;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.reviews.Rating;

public class ReviewForm {
    private Long id = null;
    private String personUsername;
    private Long filmId;
    private String reviewText;
    private Mark mark;
    private Rating rating;

    public ReviewForm() {
    }

    public ReviewForm(String personUsername, Long filmId, String reviewText, Mark mark) {
        this.personUsername = personUsername;
        this.filmId = filmId;
        this.reviewText = reviewText;
        this.mark = mark;
    }

    public ReviewForm(String personUsername, Long filmId, String reviewText, Mark mark, Rating rating) {
        this.personUsername = personUsername;
        this.filmId = filmId;
        this.reviewText = reviewText;
        this.mark = mark;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonUsername() {
        return personUsername;
    }

    public void setPersonUsername(String personUsername) {
        this.personUsername = personUsername;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public boolean isReview() {
        return reviewText != null && mark != null;
    }

    public boolean isComment() {
        return reviewText != null && mark == null;
    }

    public boolean isMark() {
        return reviewText == null && mark != null;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public String toString() {
        return this.id + " " + this.filmId + " " + this.personUsername + " "
                + this.reviewText + " " + this.mark;
    }

    @JsonValue
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public boolean isRating() {
        return mark == null && reviewText == null && rating != null;
    }


    public static class Builder {
        public String text;
        private String personUsername;
        private Long filmId;
        private String reviewText = null;
        private Mark mark = null;

        public Builder personUsername(String personUsername) {
            this.personUsername = personUsername;
            return this;
        }

        public Builder filmId(Long filmId) {
            this.filmId = filmId;
            return this;
        }

        public Builder filmId(String filmId) {
            return this.filmId(Long.valueOf(filmId));
        }

        public Builder reviewText(String reviewText) {
            this.reviewText = reviewText;
            return this;
        }

        public Builder mark(Mark mark) {
            this.mark = mark;
            return this;
        }

        public Builder mark(Integer mark) {
            return this.mark(Mark.from(mark));
        }

        public Builder mark(String mark) {
            return this.mark(mark != null ? Integer.valueOf(mark) : null);
        }

        public ReviewForm build() {
            return new ReviewForm(personUsername, filmId, reviewText, mark);
        }
    }
}
