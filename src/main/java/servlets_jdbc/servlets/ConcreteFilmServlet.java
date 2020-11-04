package servlets_jdbc.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Film;
import servlets_jdbc.models.dto.CommentDto;
import servlets_jdbc.models.dto.FilmDto;
import servlets_jdbc.models.dto.PersonDto;
import servlets_jdbc.models.forms.ReviewForm;
import servlets_jdbc.models.reviews.Comment;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.reviews.Review;
import servlets_jdbc.services.FilmService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConcreteFilmServlet extends HttpServlet {

    private FilmService filmService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        filmService = ComponentScanner.get(config, "filmService", FilmService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long filmId = Long.valueOf(req.getParameter("filmId"));

        Film film = filmService.getFilmById(filmId);
        req.setAttribute("film", FilmDto.from(film));

        List<Comment> comments = filmService.getComments(filmId);
        req.setAttribute("comments", CommentDto.from(comments));

        List<Mark> marks = filmService.getMarks(filmId);
        req.setAttribute("avgMark", avgMark(marks));

        List<Review> reviews = filmService.getReviews(filmId);
        Map<Review, Double> reviewsWithAvgRating =
                reviews.stream().collect(Collectors.toMap(Function.identity(), this::avgRating));
        req.setAttribute("reviews", reviewsWithAvgRating);

        req.getRequestDispatcher("film.ftl").forward(req, resp);
    }

    private Double avgRating(Review review) {
        return review.getRating().toInt() * 1.0 / review.getVoices();
    }

    private Double avgMark(List<Mark> marks) {
        return marks.isEmpty() ? null : marks.stream().map(Mark::toInt).reduce(Integer::sum).get() * 1.0 / marks.size();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ReviewForm reviewForm = new ObjectMapper().readValue(req.getReader(), ReviewForm.class);

        reviewForm.setPersonUsername(((PersonDto) req.getSession().getAttribute("user")).getUsername());
        reviewForm.setFilmId(Long.parseLong(req.getParameter("filmId")));
        if (req.getParameter("reviewId") != null) {
            reviewForm.setId(Long.parseLong(req.getParameter("reviewId")));
        }

        Object res;

        if (reviewForm.isComment()) {
            res = filmService.addComment(reviewForm);
        } else if (reviewForm.isMark()) {
            filmService.addMark(reviewForm);
            res = avgMark(filmService.getMarks(reviewForm.getFilmId()));
        } else if (reviewForm.isReview()) {
            res = filmService.addReview(reviewForm);
        } else if (reviewForm.isRating()) {
            filmService.addReviewRating(
                    reviewForm.getId().intValue(),
                    reviewForm.getRating().toInt(),
                    ((PersonDto) req.getAttribute("user")).getUsername()
            );
            res = avgRating(filmService.getReview(reviewForm.getId()));
        } else {
            throw new IllegalArgumentException("Review form is incorrect");
        }

        try (PrintWriter pw = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            pw.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(res));
            pw.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReviewForm reviewForm = new ObjectMapper().readValue(req.getReader(), ReviewForm.class);

        String reviewId;
        double res = 0;
        if ((reviewId = req.getParameter("reviewId")) != null) {
            res = filmService.addReviewRating(
                    Integer.parseInt(reviewId),
                    reviewForm.getRating().toInt(),
                    ((PersonDto) req.getSession().getAttribute("user")).getUsername()
            );
        }


        resp.getWriter().write(String.valueOf(res));
    }
}
