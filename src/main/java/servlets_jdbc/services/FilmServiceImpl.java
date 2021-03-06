package servlets_jdbc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import servlets_jdbc.models.Film;
import servlets_jdbc.models.forms.ReviewForm;
import servlets_jdbc.models.reviews.Comment;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.reviews.Review;
import servlets_jdbc.repositories.FilmRepository;
import servlets_jdbc.repositories.ReviewRepository;

import java.util.Collections;
import java.util.List;

public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final ReviewRepository reviewRepository;

    private Film.Description description;
    private String name;

    public FilmServiceImpl(FilmRepository filmRepository, ReviewRepository reviewRepository) {
        this.filmRepository = filmRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Film> getFilms() {
        try {
            return (description == null
                    ? name == null ? filmRepository.findAllFilms() : filmRepository.findAllFilmsByNameStartsWith(name)
                    : filmRepository.findAllFilms(description))
                    .orElse(Collections.emptyList());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        } finally {
            description = null;
            name = null;
        }
    }

    @Override
    public Film addFilm(Film film) {
        return filmRepository.saveFilm(film).orElseThrow(
                () -> new IllegalArgumentException("Film can't be saved")
        );
    }

    @Override
    public Film getFilmById(Long filmId) {
        return filmRepository.findFilmById(filmId).orElseThrow(
                () -> new IllegalArgumentException("No such film")
        );
    }

    @Override
    public Comment addComment(ReviewForm reviewForm) {
        Comment comment = new Comment(reviewForm.getFilmId(), reviewForm.getPersonUsername(), reviewForm.getReviewText());
        return reviewRepository.saveComment(comment);
    }

    @Override
    public int addMark(ReviewForm reviewForm) {
        return reviewRepository.saveMark(reviewForm.getPersonUsername(), reviewForm.getFilmId(), reviewForm.getMark());
    }

    @Override
    public Review addReview(ReviewForm reviewForm) {
        Review review = Review.from(reviewForm);
        return reviewRepository.saveReview(review);
    }

    @Override
    public List<Comment> getComments(Long filmId) {
        return reviewRepository.findCommentsByFilmId(filmId).orElse(Collections.emptyList());
    }

    @Override
    public List<Mark> getMarks(Long filmId) {
        return reviewRepository.findMarksByFilmId(filmId).orElse(Collections.emptyList());
    }

    @Override
    public List<Review> getReviews(Long filmId) {
        return reviewRepository.findReviewsByFilmId(filmId).orElse(Collections.emptyList());
    }

    @Override
    public double addReviewRating(int reviewId, int rating, String personUsername) {
        return reviewRepository.addRating(reviewId, rating, personUsername);
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findReviewById(id).orElseThrow(
                () -> new IllegalArgumentException("No review with id: " + id)
        );
    }

    @Override
    public FilmService filter(Film.Description description) {
        this.description = description;
        return this;
    }

    @Override
    public FilmService filter(String name) {
        this.name = name;
        return this;
    }
}
