package servlets_jdbc.models.reviews;

import servlets_jdbc.models.forms.ReviewForm;

public class Review {
    private Long id;
    private final String personUsername;
    private final Long filmId;
    private final String reviewText;
    private final Mark mark;
    private Rating rating;
    private final int voices;

    public Review(String personUsername, Long filmId, String reviewText, Mark mark, Rating rating) {
        this.id = null;
        this.personUsername = personUsername;
        this.filmId = filmId;
        this.reviewText = reviewText;
        this.mark = mark;
        this.rating = rating;
        voices = 0;
    }

    public Review(Long id, String personUsername, Long filmId, String reviewText, Mark mark, Rating rating, int voices) {
        this.id = id;
        this.personUsername = personUsername;
        this.filmId = filmId;
        this.reviewText = reviewText;
        this.mark = mark;
        this.rating = rating;
        this.voices = voices;
    }

    public String getPersonUsername() {
        return personUsername;
    }

    public Long getFilmId() {
        return filmId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Mark getMark() {
        return mark;
    }

    public Rating getRating() {
        return rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
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

    public static Review from(ReviewForm reviewForm) {
        return builder()
                .filmId(reviewForm.getFilmId())
                .personUsername(reviewForm.getPersonUsername())
                .reviewText(reviewForm.getReviewText())
                .mark(reviewForm.getMark())
                .build();
    }

    public int getVoices() {
        return voices;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", personUsername='" + personUsername + '\'' +
                ", filmId=" + filmId +
                ", reviewText='" + reviewText + '\'' +
                ", mark=" + mark +
                ", rating=" + rating +
                ", voices=" + voices +
                '}';
    }

    public static Double avgRating(Review review) {
        return review.getRating().toInt() * 1.0 / review.getVoices();
    }

    public static class Builder {
        private Long id = null;
        private String personUsername;
        private Long filmId;
        private String reviewText = null;
        private Mark mark = null;
        private Rating rating = null;
        private int voices = 0;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

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
            return this.mark(Integer.valueOf(mark));
        }

        public Builder rating(Rating rating) {
            this.rating = rating;
            return this;
        }

        public Builder rating(Integer rating) {
            return this.rating(Rating.from(rating));
        }

        public Builder rating(String rating) {
            return this.rating(Integer.valueOf(rating));
        }

        public Builder voices(int voices) {
            this.voices = voices;
            return this;
        }

        public Review build() {
            if (id == null) {
                return new Review(personUsername, filmId, reviewText, mark, rating);
            } else {
                return new Review(id, personUsername, filmId, reviewText, mark, rating, voices);
            }
        }
    }
}
