import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import resources.TestResource;
import server.ResourceServer;
import server.ResourceServerMBean;
import servlets.ResourcesServlet;

import javax.management.*;
import javax.servlet.Servlet;
import java.lang.management.ManagementFactory;

public class Main {
    private static final Logger log = Log.getLogger(Main.class);

    public static void main(String[] args) {
        ResourceServer resourceServer = new ResourceServer();

        log.debug("Register MBean");
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("Admin:type=ResourceServerController");
            ResourceServerMBean bean = resourceServer;
            mBeanServer.registerMBean(bean, objectName);
        } catch (JMException e) {
            e.printStackTrace();
        }
        log.debug("MBean registered");

        // Jetty
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        Servlet resourcesServlet = new ResourcesServlet(resourceServer);
        context.addServlet(new ServletHolder(resourcesServlet), "/resources");
        Server httpServer = new Server(8080);
        httpServer.setHandler(context);
        try {
            httpServer.start();
            log.info("Server started");
            httpServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
