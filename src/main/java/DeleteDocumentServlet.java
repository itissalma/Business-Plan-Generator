//Shady Nessim 900191322 Salma Aly 900203182
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Plan;
import models.PlanFileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/delete-document/*")
public class DeleteDocumentServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DeleteDocumentServlet.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Extract the document ID from the URL path
            int documentId = extractDocumentId(req.getRequestURI());

            // Replace this with your actual logic to delete the document by ID
            boolean deleted = deleteDocument(documentId);

            logger.debug("Received POST request to delete document with ID: {}", documentId);

            // Send a success response
            resp.setContentType("text/plain");
            if (deleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Document deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Document not found");
            }
        } catch (NumberFormatException e) {
            // Handle the case where the document ID is not a valid integer
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid document ID");
        } catch (Exception e) {
            // Send an error response in case of any other exception
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting document: " + e.getMessage());
            logger.error("Error deleting document", e);
        }
    }

    // Extract the document ID from the URL path
    private int extractDocumentId(String path) {
        // Parse the document ID from the path
        String[] pathSegments = path.split("/");
        String documentIdSegment = pathSegments[pathSegments.length - 1];
        return Integer.parseInt(documentIdSegment);
    }

    // Replace this method with your actual logic to delete the document from the database or another source
    private boolean deleteDocument(int documentId) {
        // Replace this with your actual logic to delete the document by ID
        // Return true if the document is deleted, false otherwise
        Plan plan = new PlanFileHandler().getObjects().stream()
                .filter(p -> p.getId() == documentId)
                .findFirst()
                .orElse(null);

        if (plan == null) {
            return false;
        } else {
            return new PlanFileHandler().deleteObject(plan);
        }
    }
}

