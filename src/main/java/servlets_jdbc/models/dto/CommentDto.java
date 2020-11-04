package servlets_jdbc.models.dto;

import servlets_jdbc.models.reviews.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentDto {
    private final String personUsername;
    private final String text;

    public CommentDto(String personUsername, String text) {
        this.personUsername = personUsername;
        this.text = text;
    }

    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getPersonUsername(), comment.getReviewText());
    }

    public static List<CommentDto> from(List<Comment> comments) {
        return comments.stream().map(CommentDto::from).collect(Collectors.toList());
    }

    public String getText() {
        return text;
    }

    public String getPersonUsername() {
        return personUsername;
    }
}
