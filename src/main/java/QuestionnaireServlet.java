import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import models.PlanFileHandler;
import models.Plan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/questions")
public class QuestionnaireServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Read the request parameters
            String companyName = req.getParameter("companyName");
            String businessOverview = req.getParameter("businessOverview");
            String country = req.getParameter("country");
            String productsServices = req.getParameter("productsServices");
            String targetAudience = req.getParameter("targetAudience");
            String employeesCount = req.getParameter("employeesCount");
            String businessGoals = req.getParameter("businessGoals");

            // Log the received data
            logger.debug("Received POST request with company data: {}, {}, {}, {}, {}, {}, {}",
                    companyName, businessOverview, country, productsServices, targetAudience, employeesCount, businessGoals);

            // Create a new plan object with the answers
            String username = (String) req.getSession().getAttribute("username");
            int id = new PlanFileHandler().getObjects().size();
            Plan plan = new Plan(username, companyName, id, new String[]{
                    companyName,
                    businessOverview,
                    country,
                    productsServices,
                    targetAudience,
                    employeesCount,
                    businessGoals
            });

            // Save the plan to file
            new PlanFileHandler().addObject(plan);

            // Send a response
            resp.setContentType("text/plain");
            resp.getWriter().write("Data received successfully!");
        } catch (Exception e) {
            logger.error("Error processing form submission", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing form submission");
        }
    }
}