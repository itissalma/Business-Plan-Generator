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

@WebServlet("/expand-text")
public class ExpandTextServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private static final chatGPT chatty = new chatGPT();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read the text from the request body
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        String textToExpand = requestBody.toString();
        logger.debug("Text is {}", textToExpand);
        textToExpand = textToExpand.substring(9, textToExpand.length() - 2);

        try {
            // Expand the text (replace with your actual GPT API integration)

            String output = chatty.generateResponse(textToExpand);

            logger.debug("Expanded text is {}", output);

            // Send the expanded text as the response
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println(output);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

}