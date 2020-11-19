package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Film;
import servlets_jdbc.models.dto.CommentDto;
import servlets_jdbc.models.dto.FilmDto;
import servlets_jdbc.models.forms.ReviewForm;
import servlets_jdbc.models.reviews.Comment;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.reviews.Review;
import servlets_jdbc.services.FilmService;
import servlets_jdbc.services.Json;
import servlets_jdbc.services.security.SecurityChecker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilmServlet extends HttpServlet {

    private FilmService filmService;
    private SecurityChecker securityChecker;
    private Json json;

    @Override
    public void init(ServletConfig config) throws ServletException {
        filmService = ComponentScanner.get(config, "filmService", FilmService.class);
        securityChecker = ComponentScanner.get(config, "securityChecker", SecurityChecker.class);
        json = ComponentScanner.get(config, "json", Json.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long filmId = Long.valueOf(req.getParameter("filmId"));

        Film film = filmService.getFilmById(filmId);
        req.setAttribute("film", FilmDto.from(film));

        List<Comment> comments = filmService.getComments(filmId);
        req.setAttribute("comments", CommentDto.from(comments));

        List<Mark> marks = filmService.getMarks(filmId);
        req.setAttribute("avgMark", Mark.avgMark(marks));

        List<Review> reviews = filmService.getReviews(filmId);
        Map<Review, Double> reviewsWithAvgRating =
                reviews.stream().collect(Collectors.toMap(Function.identity(), Review::avgRating));
        req.setAttribute("reviews", reviewsWithAvgRating);

        req.getRequestDispatcher("film.ftl").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ReviewForm reviewForm = json.read(req, ReviewForm.class);

        reviewForm.setPersonUsername(securityChecker.getUser(req).getUsername());
        reviewForm.setFilmId(Long.parseLong(req.getParameter("filmId")));
        if (req.getParameter("reviewId") != null) {
            reviewForm.setId(Long.parseLong(req.getParameter("reviewId")));
        }

        Object res;

        if (reviewForm.isComment()) {
            res = filmService.addComment(reviewForm);
        } else if (reviewForm.isMark()) {
            filmService.addMark(reviewForm);
            res = Mark.avgMark(filmService.getMarks(reviewForm.getFilmId()));
        } else if (reviewForm.isReview()) {
            res = filmService.addReview(reviewForm);
        } else if (reviewForm.isRating()) {
            filmService.addReviewRating(
                    reviewForm.getId().intValue(),
                    reviewForm.getRating().toInt(),
                    securityChecker.getUser(req).getUsername()
            );
            res = Review.avgRating(filmService.getReview(reviewForm.getId()));
        } else {
            throw new IllegalArgumentException("Review form is incorrect");
        }

        json.write(resp, res);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReviewForm reviewForm = json.read(req, ReviewForm.class);

        String reviewId;
        double res = 0;
        if ((reviewId = req.getParameter("reviewId")) != null) {
            res = filmService.addReviewRating(
                    Integer.parseInt(reviewId),
                    reviewForm.getRating().toInt(),
                    securityChecker.getUser(req).getUsername()
            );
        }

        json.write(resp, res);
    }
}
