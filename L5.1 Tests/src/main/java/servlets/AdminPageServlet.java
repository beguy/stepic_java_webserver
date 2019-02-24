package servlets;

import accountServer.AccountServerI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {
    public static final String PAGE_URL = "/admin";
    private AccountServerI accountServer;

    public AdminPageServlet(AccountServerI accountServer) {

        this.accountServer = accountServer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(accountServer.getUsersLimit());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
