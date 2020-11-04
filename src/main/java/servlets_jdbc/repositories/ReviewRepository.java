package servlets_jdbc.repositories;

import servlets_jdbc.models.reviews.Comment;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.reviews.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Comment saveComment(Comment comment);

    int saveMark(String personUsername, Long filmId, Mark mark);

    Review saveReview(Review review);

    Optional<List<Comment>> findCommentsByFilmId(Long filmId);

    Optional<List<Mark>> findMarksByFilmId(Long filmId);

    Optional<List<Review>> findReviewsByFilmId(Long filmId);

    double addRating(int reviewId, int rating, String personUsername);

    Optional<Review> findReviewById(Long id);
}
