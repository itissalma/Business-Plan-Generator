import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Plan;
import models.PlanFileHandler;

import java.io.IOException;

@WebServlet("/document/*")
public class EditorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int planId = extractPlanId(req);

            Plan plan = new PlanFileHandler().getObjects().stream()
                    .filter(p -> p.getId() == planId)
                    .findFirst()
                    .orElse(null);

            if (plan == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Document not found");
                return;
            }

            resp.setContentType("application/json");
            resp.getWriter().write(plan.getSectionsJSON());
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid document ID");
        }
    }

    private int extractPlanId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        return Integer.parseInt(pathParts[1]);
    }
}
