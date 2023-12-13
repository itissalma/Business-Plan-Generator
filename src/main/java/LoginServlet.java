import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.*;

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
        // get users from file
        List<User> userList = UserFileHandler.getUsers();
        logger.debug("User list: {}", userList);

        // check if user exists
        boolean userExists = false;
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                userExists = true;
                break;
            }
        }

        // send response
        resp.setStatus(userExists ? HttpServletResponse.SC_OK : HttpServletResponse.SC_UNAUTHORIZED);

        resp.setContentType("text/plain");
    }
}

