//Shady Nessim 900191322 Salma Aly 900203182
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Plan;
import models.PlanFileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
            logger.debug("request parameter map is {}", request.getParameterMap());
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");

            // Parse the JSON payload into a Map using Jackson
            Map<String, String> checkedSections = parseJson(requestBody.toString());
            logger.debug("Checked sections are: ");
            for (Map.Entry<String, String> entry : checkedSections.entrySet()) {
                logger.debug(entry.getKey() + ": " + entry.getValue());
            }

            // Perform any processing with the checkedSections data
            String[] newSections = processBusinessPlanData(checkedSections);

            // get plan by id and username
            Plan plan = new PlanFileHandler().getObjects().stream().
                    filter(p -> p.getId() == id && p.getUsername().equals(username)).
                    findFirst().orElse(null);
            if (plan == null) {
                throw new Exception("Plan not found");
            }

            // set sections in plan
            plan.setSectionsContent(newSections);

            // save plan
            new PlanFileHandler().updateObject(plan);

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
    private String[] processBusinessPlanData(Map<String, String> checkedSections) {
        // get index of checked sections from Plan.getSections()
        // initialize array of 7 empty string
        // for each checked section, set the corresponding index in the array to the section name
        // set the sections array in the Plan object
        int numSections = checkedSections.size();
        String[] sections = {"", "", "", "", "", "", ""};
        for (Map.Entry<String, String> entry : checkedSections.entrySet()) {
            int index = Arrays.stream(Plan.getSections()).toList().indexOf(entry.getKey());
            sections[index] = entry.getValue();
        }

        return sections;
    }

    // Example function for parsing JSON into a Map using Jackson
    private Map<String, String> parseJson(String jsonString) throws IOException {
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(Map.class, String.class, String.class);
        return objectMapper.readValue(jsonString, mapType);
    }
}
