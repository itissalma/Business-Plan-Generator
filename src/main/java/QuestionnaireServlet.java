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
            new PlanFileHandler().addObject(plan); // TODO: fix id

            // build a prompt for chatGPT to generate the sections content of the plan based on the answers to the questions
            String prompt = "can you generate a business plan with the following sections in the order you were presented: " +
                    Plan.getSectionsString() + ". use these questions and answers to generate the sections: " +
                    plan.getQuestionsAndAnswersString();

            // generate the sections content using chatGPT
            String message = chatGPT(prompt);
            logger.debug("message generated: {}", message);

            // Send a response
            resp.setContentType("application/json");
            resp.getWriter().write("");
        } catch (Exception e) {
            logger.error("Error processing form submission", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing form submission");
        }
    }

    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return extractMessageFromJSONResponse(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }
}
