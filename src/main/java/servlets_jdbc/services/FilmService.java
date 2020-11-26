package servlets_jdbc.services;

import servlets_jdbc.models.Film;
import servlets_jdbc.models.forms.ReviewForm;
import servlets_jdbc.models.reviews.Comment;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.reviews.Review;

import java.util.List;

public interface FilmService {
    List<Film> getFilms();

    Film addFilm(Film film);

    Film getFilmById(Long filmId);

    Comment addComment(ReviewForm reviewForm);

    int addMark(ReviewForm reviewForm);

    Review addReview(ReviewForm reviewForm);

    List<Comment> getComments(Long filmId);

    List<Mark> getMarks(Long filmId);

    List<Review> getReviews(Long filmId);

    double addReviewRating(int reviewId, int rating, String personUsername);

    Review getReview(Long id);

    FilmService filter(Film.Description description);

    FilmService filter(String name);
}
