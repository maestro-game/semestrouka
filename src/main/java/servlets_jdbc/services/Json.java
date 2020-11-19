package servlets_jdbc.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Json {
    private final ObjectMapper objectMapper;

    public Json(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T read(HttpServletRequest reader, Class<T> _class) {
        try {
            return new ObjectMapper().readValue(reader.getReader(), _class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void write(HttpServletResponse resp, Object res) {
        try (PrintWriter pw = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            pw.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(res));
            pw.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
