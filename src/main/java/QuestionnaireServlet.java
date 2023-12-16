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
                    plan.getQuestionsAndAnswersString() + ". don't add any extra sections.";

            // generate the sections content using chatGPT
            String message = chatGPT(prompt);
            logger.debug("message generated: {}", message);

            // parse the sections content from the message
            // response example: Executive Summary:\n\npaymob is a fintech company based in Egypt, specializing in providing payment processing and integration solutions. Our digital payment solution is designed to cater specifically to the needs of small businesses. With a current team of 25 employees, our primary goal is to expand our presence in the MENA region.\n\nCompany Description:\n\npaymob is a leading fintech company in Egypt, offering innovative payment processing and integration solutions. Our mission is to empower small businesses by enabling secure, convenient, and seamless digital payment transactions. With a strong focus on customer satisfaction, we strive to provide cutting-edge technology and exceptional service.\n\nProducts and Services:\n\nWe offer a comprehensive digital payment solution that includes a range of features designed to meet the specific requirements of small businesses. Our services include online payment gateway integration, mobile payment solutions, QR code payments, and secure card processing capabilities. By leveraging the latest technology, we enable businesses to enhance their customer experience while streamlining payment processes.\n\nMarket Analysis:\n\nThe market for digital payment solutions in the MENA region is growing rapidly due to the increasing adoption of e-commerce and the transformation of traditional businesses to digital platforms. Small businesses, in particular, face numerous challenges in embracing digital payment methods, such as costly integration processes and lack of accessible solutions. paymob aims to bridge this gap by providing affordable and user-friendly payment solutions tailored to the needs of small businesses.\n\nStrategy and Implementation:\n\nOur strategy revolves around expansion in the MENA region, capitalizing on the untapped market potential. By focusing on targeted marketing campaigns and strategic partnerships, we aim to increase our brand presence and attract a larger customer base. Additionally, we will invest in research and development to continually enhance our product offerings and stay ahead of competitors.\n\nOrganization and Management Team:\n\npaymob is led by a team of experienced professionals with expertise in fintech, payment solutions, and business development. With a diverse skill set and a shared vision, our management team is committed to driving the growth and success of the company. Each member brings valuable industry knowledge and strategic acumen, ensuring that paymob is well-positioned for continuous expansion.\n\nFinancial Plan:\n\nOur financial plan is centered around sustainable growth and profitability. We anticipate steady revenue streams from transaction fees and service charges. As we expand our reach and build a larger customer base, we expect to achieve economies of scale, which will further enhance our financial performance. To support our growth goals, we will actively seek external funding and strategic partnerships that align with our vision.
            String[] sectionsContent = message.split("~");
//            for (int i = 0; i < sectionsContent.length; i++) {
//                sectionsContent[i] = sectionsContent[i].substring(sectionsContent[i].indexOf(":") + 1);
//            }

            // print
            for (String s : sectionsContent) {
                logger.debug("section content: {}", s);
            }

            // Send a response
            resp.setContentType("application/json");
            resp.getWriter().write(message);
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
            String body = "{" +
                    "\"model\": \"" + model + "\"," +
                    "\"messages\": [" +
                    "{\"role\": \"assistant\", \"content\": \"You are a helpful expert business consultant.\"}," +
                    "{\"role\": \"user\", \"content\": \"" + prompt + "\"}" +
                    "]" +
                    "}";

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