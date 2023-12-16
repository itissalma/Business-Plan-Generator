import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import models.PlanFileHandler;
import models.Plan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/questions")
public class QuestionnaireServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireServlet.class);
    private static final chatGPT chatty = new chatGPT();
    PlanFileHandler planFileHandler = new PlanFileHandler();

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
            String username = req.getParameter("username");

            // Log the received data
            logger.debug("received post data from user: {}. with request params: {}", username, req.getParameterMap());

            logger.debug("Received POST request with company data: {}, {}, {}, {}, {}, {}, {}",
                    companyName, businessOverview, country, productsServices, targetAudience, employeesCount, businessGoals);

            // Create a new plan object with the answers
            // String username = (String) req.getSession().getAttribute("username");    // TODO: try to use sessions
            Plan plan = new Plan(username, companyName, new String[]{
                    companyName,
                    businessOverview,
                    country,
                    productsServices,
                    targetAudience,
                    employeesCount,
                    businessGoals
            });

            // Save the plan to file
            planFileHandler.addObject(plan);

            // build a prompt for chatGPT to generate the sections content of the plan based on the answers to the questions
            String prompt = "can you generate a business plan with the following sections in the order you were presented: " +
                    Plan.getSectionsString() + ". use these questions and answers to generate the sections: " +
                    plan.getQuestionsAndAnswersString() + ". don't add any extra sections.";

            // generate the sections content using chatGPT
            String message = chatty.generateResponse(prompt);
            logger.debug("message generated: {}", message);
            logger.debug("prompt used: {}", prompt);

            // parse the sections content from the message with TextParser
            String[] sectionsContent = TextParser.parse(message);
            logger.debug("sections content parsed: {}", Arrays.stream(sectionsContent).toArray());

            // update the plan with the sections content
            plan.setSectionsContent(sectionsContent);
            planFileHandler.updateObject(plan);

            // build a response with plan id, and the sections titles and sections content JSON
            StringBuilder response = new StringBuilder();
            response.append("{\"id\":\"").append(plan.getId()).append("\",");
            for (int i = 0; i < Plan.getSections().length; i++) {
                response.append("\"").append(Plan.getSections()[i]).append("\":\"").append(sectionsContent[i]).append("\"");
                if (i < Plan.getSections().length - 1) {
                    response.append(",");
                }
            }
            response.append("}");

            // Send a response
            resp.setContentType("application/json");
            resp.getWriter().write(response.toString());
        } catch (Exception e) {
            logger.error("Error processing form submission", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing form submission");
        }
    }
}