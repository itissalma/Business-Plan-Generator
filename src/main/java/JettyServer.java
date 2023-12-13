import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Enable CORS for all origins on Jetty server
        FilterHolder cors = context.addFilter(CrossOriginFilter.class, "/*", null);
        cors.setInitParameter("allowedOrigins", "http://localhost:3000"); // Add your frontend origin
        cors.setInitParameter("allowedMethods", "GET,POST,HEAD,OPTIONS"); // Adjust as needed

        // Add servlets
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");

        // Start server
        server.start();
        server.join();

    }
}
