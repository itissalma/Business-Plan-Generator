import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import models.Plan;
import models.PlanFileHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/documents")
public class DocumentServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");

            // Create a list of documents (each document is the id and name of a plan)
            List<Plan> plans = new PlanFileHandler().getObjects().stream()
                    .filter(plan -> plan.getUsername().equals(username))
                    .toList();

            // Convert the list of documents to JSON
            StringBuilder jsonResponse = new StringBuilder("[");
            for (Plan plan : plans) {
                jsonResponse.append("{\"id\":").append(plan.getId()).
                        append(",\"name\":\"").append(plan.getName()).
                        append("\"},");
            }
            jsonResponse.deleteCharAt(jsonResponse.length() - 1);
            jsonResponse.append("]");
            logger.debug("JSON response is {}", jsonResponse);

            // Set response content type and write the response
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            logger.error("Error getting documents", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error getting documents: " + e.getMessage());
        }
    }
}
