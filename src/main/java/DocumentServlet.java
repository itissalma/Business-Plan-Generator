import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/documents")
public class DocumentServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServlet.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Create a list of sample documents (replace with your actual logic to fetch documents)
        List<Document> documents = createSampleDocuments();

        // Convert the list of documents to JSON
        String jsonResponse = objectMapper.writeValueAsString(documents);

        // Set response content type and write the response
        resp.setContentType("application/json");
        resp.getWriter().write(jsonResponse);
    }

    // Replace this method with your actual logic to fetch documents from the database or another source
    private List<Document> createSampleDocuments() {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document(1, "Breadfast"));
        documents.add(new Document(2, "Cilantro"));
        documents.add(new Document(3, "Pepsi"));
        return documents;
    }

    private static class Document {
        private final int id;
        private final String name;

        public Document(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
