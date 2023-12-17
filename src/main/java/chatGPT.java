import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class chatGPT {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "gpt-3.5-turbo";

    public static String generateResponse(String prompt) {
        try {
            URL obj = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = String.format("{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                    MODEL, prompt);
            connection.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                writer.write(body);
            }

            // Response from ChatGPT
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                return extractMessageFromJSONResponse(response.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        String content = response.substring(start, end);
        return content.replace("\\n", "~");
    }
}