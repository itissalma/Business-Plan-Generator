import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet("/document/*")
public class EditorServlet extends HttpServlet {

    private static final String FILE_PATH = "/path/to/document/files/"; // Replace with the actual path

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int documentId = extractDocumentId(req);

            if (documentExistsInFile(documentId)) {
                // Retrieve data from the file
                String documentData = retrieveDataFromFile(documentId);
                resp.getWriter().write(documentData);
            } else {
                // Fetch data from GPT API
                String gptApiResponse = fetchDataFromGPT(documentId);
                resp.getWriter().write(gptApiResponse);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid document ID");
        }
    }

    private int extractDocumentId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        return Integer.parseInt(pathParts[1]);
    }

    private boolean documentExistsInFile(int documentId) {
        // Logic to check if the document ID exists in the file
        // For simplicity, let's assume each document has its file named with the document ID
        String filePath = FILE_PATH + "document_" + documentId + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String retrieveDataFromFile(int documentId) throws IOException {
        // Retrieve data from the file
        String filePath = FILE_PATH + "document_" + documentId + ".txt";
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        }
        return data.toString();
    }

    private String fetchDataFromGPT(int documentId) {
        // Logic to fetch data from GPT API
        // This is a placeholder, replace it with your actual API call
        // You may use a library like HttpClient for making HTTP requests
        return "Data fetched from GPT API for document ID: " + documentId;
    }
}
