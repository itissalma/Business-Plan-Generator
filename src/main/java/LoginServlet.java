import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Read the request parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Log the received data
        logger.debug("Received POST request with payload: {}", req.getReader().lines().collect(Collectors.joining()));
        logger.debug("Received POST request with username: {}, password: {}", username, password);

        // authentication logic
        if (username.equals("admin") && password.equals("admin")) {
            logger.debug("Login successful");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.debug("Login failed");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        // Set response content type and write the response
        resp.setContentType("text/plain");
//        resp.getWriter().write(response);
    }
}

