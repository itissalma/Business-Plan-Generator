import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/questions")
public class QuestionnaireServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Read the request parameters
            String companyName = req.getParameter("companyName");
            String businessOverview = req.getParameter("businessOverview");
            String country = req.getParameter("country");
            String productsServices = req.getParameter("productsServices");
            String targetAudience = req.getParameter("targetAudience");
            String employeesCount = req.getParameter("employeesCount");
            String businessGoals = req.getParameter("businessGoals");
            String userName = req.getParameter("userName");

            // Log the received data
            logger.debug("Received POST request with company data: {}", companyName);

            // You can now handle the received data as needed, for example, store it in a database

            // Send a response
            resp.setContentType("text/plain");
            resp.getWriter().write("Data received successfully!");
        } catch (Exception e) {
            logger.error("Error processing form submission", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing form submission");
        }
    }
}
