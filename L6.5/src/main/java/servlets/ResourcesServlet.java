package servlets;

import resources.TestResource;
import server.ResourceServer;
import servlets.xml.sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourcesServlet extends HttpServlet {

    private ResourceServer resourceServer;

    public ResourcesServlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathToResource = req.getParameter("path");
        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(pathToResource);
        resourceServer.setResource(resource);
    }
}
