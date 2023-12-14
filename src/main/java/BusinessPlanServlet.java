import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/business-plan")
public class BusinessPlanServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Read the JSON payload from the request body
            StringBuilder requestBody = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            }

            // Parse the JSON payload into a Map using Jackson
            Map<String, String> checkedSections = parseJson(requestBody.toString());
            logger.debug("Checked sections are: ");
            for (Map.Entry<String, String> entry : checkedSections.entrySet()) {
                logger.debug(entry.getKey() + ": " + entry.getValue());
            }

            // Perform any processing with the checkedSections data
            processBusinessPlanData(checkedSections);

            // Send a success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println("Business plan data submitted successfully!");

        } catch (Exception e) {
            // Send an error response in case of any exception
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println("Error submitting business plan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Example function for processing business plan data
    private void processBusinessPlanData(Map<String, String> checkedSections) {
        // Replace this with your actual processing logic
        logger.debug("Processing business plan data: " + checkedSections);
    }

    // Example function for parsing JSON into a Map using Jackson
    private Map<String, String> parseJson(String jsonString) throws IOException {
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(Map.class, String.class, String.class);
        return objectMapper.readValue(jsonString, mapType);
    }
}
