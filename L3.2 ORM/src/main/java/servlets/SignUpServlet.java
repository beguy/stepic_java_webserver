package servlets;

import services.AccountService;
import dbService.dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<UsersDataSet> userProfile = Optional.ofNullable(accountService.getUserByLogin(req.getParameter("login")));


        userProfile.ifPresent((existUser) -> {
            try {
                resp.getWriter().println("User " + existUser.getLogin() + " already exist");
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        userProfile.orElseGet(() -> {
            UsersDataSet newUserProfile = new UsersDataSet(-1, req.getParameter("login"), req.getParameter("password"));
            accountService.addNewUser(newUserProfile);
            try {
                resp.getWriter().println("Registered: " + newUserProfile.getLogin());
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newUserProfile;
        });

    }
}
