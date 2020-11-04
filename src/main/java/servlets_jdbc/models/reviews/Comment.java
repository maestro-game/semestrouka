package servlets_jdbc.models.reviews;

public class Comment {
    private Long filmId;
    private final String personUsername;
    private final String reviewText;

    public Comment(Long filmId, String personUsername, String getReviewText) {
        this.filmId = filmId;
        this.personUsername = personUsername;
        this.reviewText = getReviewText;
    }


    public String getReviewText() {
        return reviewText;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getPersonUsername() {
        return personUsername;
    }
}
