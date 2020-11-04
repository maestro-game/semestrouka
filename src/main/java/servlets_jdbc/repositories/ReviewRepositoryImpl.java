package servlets_jdbc.repositories;

import servlets_jdbc.models.reviews.Comment;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.reviews.Rating;
import servlets_jdbc.models.reviews.Review;
import servlets_jdbc.models.rowmappers.RowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReviewRepositoryImpl implements ReviewRepository {

    //  language=sql
    private static final String Q_SAVE_COMMENT = "INSERT INTO review (person_username, film_id, review__text) " +
            "VALUES (?, ?, ?) ;";
    //  language=sql
    private static final String Q_SAVE_MARK = "INSERT INTO review (person_username, film_id, mark) " +
            "VALUES (?, ?, ?) ;";
    //  language=sql
    private static final String Q_SAVE_REVIEW = "INSERT INTO review (person_username, film_id, review__text, mark) " +
            "VALUES (?, ?, ?, ?) ;";
    //  language=sql
    private static final String Q_FIND_ALL_COMMENTS = "SELECT * FROM review " +
            "WHERE film_id = ? AND mark IS NULL AND review__text IS NOT NULL ;";
    //  language=sql
    private static final String Q_FIND_ALL_MARKS = "SELECT mark FROM review " +
            "WHERE film_id = ? AND mark IS NOT NULL AND review__text IS NULL ;";
    //  language=sql
    private static final String Q_FIND_ALL_REVIEWS = "SELECT * from review " +
            "WHERE film_id = ? AND mark IS NOT NULL AND review__text IS NOT NULL ;";
    //  language=sql
    private static final String Q_FIND_RATING_BY_ID = "SELECT review__rating FROM review WHERE id = ? ;";
    //  language=sql
    private static final String Q_UPDATE_RATING_BY_ID = "UPDATE review SET review__rating = ? WHERE id = ? ;";
    //  language=sql
    private static final String Q_FIND_REVIEW = "SELECT * FROM review WHERE id = ? LIMIT 1 ;";
    //  language=sql
    private static final String Q_FIND_MARK_BY_USERNAME_AND_FILM_ID = "SELECT * FROM review " +
            "WHERE mark IS NOT NULL AND review__text IS NULL " +
            "AND person_username = ? AND film_id = ?;";
    //  language=sql
    private static final String Q_UPDATE_MARK = "UPDATE review SET mark = ? WHERE film_id = ? AND person_username = ? " +
            "AND review__text IS NULL AND mark IS NOT NULL ;";

    private final JdbcUtil jdbcUtil;

    private final RowMapper<Review> reviewRowMapper = resultSet ->
            Review.builder()
                    .id(resultSet.getLong("id"))
                    .filmId(resultSet.getLong("film_id"))
                    .personUsername(resultSet.getString("person_username"))
                    .mark(Mark.from(resultSet.getInt("mark")))
                    .rating(Rating.from(resultSet.getInt("review__rating")))
                    .reviewText(resultSet.getString("review__text"))
                    .build();

    private final RowMapper<Comment> commentRowMapper = resultSet ->
            new Comment(
                    resultSet.getLong("film_id"),
                    resultSet.getString("person_username"),
                    resultSet.getString("review__text")
            );

    private final RowMapper<Mark> markRowMapper = resultSet -> Mark.from(resultSet.getInt("mark"));

    public ReviewRepositoryImpl(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public Comment saveComment(Comment comment) {
        comment.setFilmId(jdbcUtil.save(Q_SAVE_COMMENT, comment,
                (stmt, form) -> {
                    try {
                        stmt.setString(1, form.getPersonUsername());
                        stmt.setLong(2, form.getFilmId());
                        stmt.setString(3, form.getReviewText());
                    } catch (SQLException e) {
                        throw new IllegalArgumentException(e);
                    }

                    return stmt;
                })
        );
        return comment;
    }

    @Override
    public int saveMark(String personUsername, Long filmId, Mark mark) {
        int res;
        //  language=sql
        if (jdbcUtil.findOne(Q_FIND_MARK_BY_USERNAME_AND_FILM_ID, reviewRowMapper, personUsername, filmId) != null) {
            res = jdbcUtil.update(Q_UPDATE_MARK, mark, filmId, personUsername);
        } else {
            res = jdbcUtil.update(Q_SAVE_MARK, personUsername, filmId, mark);
        }
        return res;
    }

    @Override
    public Review saveReview(Review review) {
        review.setId(jdbcUtil.save(Q_SAVE_REVIEW, review,
                (stmt, form) -> {
                    try {
                        stmt.setString(1, form.getPersonUsername());
                        stmt.setLong(2, form.getFilmId());
                        stmt.setString(3, form.getReviewText());
                        stmt.setInt(4, form.getMark().toInt());
                    } catch (SQLException e) {
                        throw new IllegalArgumentException(e);
                    }

                    return stmt;
                }));
        review.setRating(Rating.from(0));
        return review;
    }

    @Override
    public Optional<List<Comment>> findCommentsByFilmId(Long filmId) {
        return Optional.of(jdbcUtil.findAll(Q_FIND_ALL_COMMENTS, commentRowMapper, filmId));
    }

    @Override
    public Optional<List<Mark>> findMarksByFilmId(Long filmId) {
        return Optional.of(jdbcUtil.findAll(Q_FIND_ALL_MARKS, markRowMapper, filmId));
    }

    @Override
    public Optional<List<Review>> findReviewsByFilmId(Long filmId) {
        return Optional.of(jdbcUtil.findAll(Q_FIND_ALL_REVIEWS, reviewRowMapper, filmId));
    }

    @Override
    public double addRating(int reviewId, int rating, String personUsername) {
//      language=sql
        Integer reviewRatingCountVoices = jdbcUtil.findOne("SELECT review__rating__count FROM review WHERE id = ? ;",
                rs -> rs.getInt("review__rating__count"), reviewId);

        String query;
        double ratingNew;

        var ref = new Object() {
            public void setPersonUsername(String personUsername) {
                this.personUsername = personUsername;
            }

            public String getPersonUsername() {
                return personUsername;
            }

            public void setReviewId(Long reviewId) {
                this.reviewId = reviewId;
            }

            public Long getReviewId() {
                return reviewId;
            }

            private String personUsername = null;
            private Long reviewId = null;

            public boolean isNull() {
                return personUsername == null & reviewId == null;
            }
        };

//      Check if review was rated by this user
//        FIXME: fix :(
//      language=sql
        if (jdbcUtil.findOne("SELECT * FROM rating_review WHERE person_username = ? AND review_id = ? ;",
                resultSet -> {
                    String s;
                    long l;
                    if ((s = resultSet.getString("person_username")) != null
                            && (l = resultSet.getLong("review_id")) != 0) {
                        ref.setPersonUsername(s);
                        ref.setReviewId(l);
                    }
                    return ref;
                }, personUsername, reviewId).isNull()) {
            jdbcUtil.update("INSERT INTO rating_review (review_id, person_username) " +
                    "VALUES (?, ?)", reviewId, personUsername);
            ratingNew = rating;
            query = Q_UPDATE_RATING_BY_ID;
        } else {
            ratingNew = (jdbcUtil.findOne(Q_FIND_RATING_BY_ID,
                    rs -> rs.getDouble("review__rating"), reviewId) * reviewRatingCountVoices++ + rating) / reviewRatingCountVoices;
            query = Q_UPDATE_RATING_BY_ID;
        }

//      language=sql
        jdbcUtil.update("UPDATE review SET review__rating__count = ? + 1 " +
                        "WHERE id = ? ;",
                reviewRatingCountVoices,
                reviewId);
//      language=sql
        jdbcUtil.update(query, ratingNew, reviewId);
        return jdbcUtil.findOne(Q_FIND_RATING_BY_ID,
                rs -> rs.getDouble("review__rating"), reviewId);
    }

    @Override
    public Optional<Review> findReviewById(Long id) {
        return Optional.of(jdbcUtil.findOne(Q_FIND_REVIEW, reviewRowMapper, id));
    }
}
