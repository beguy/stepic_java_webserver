package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<UserProfile> userProfile = Optional.ofNullable(accountService.getUserByLogin(req.getParameter("login")));

        userProfile.filter(user -> user.getPass().equals(req.getParameter("password")))
                .ifPresent((user) -> {
                    try {
                        resp.getWriter().println("Authorized: " + user.getLogin());
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        userProfile.orElseGet(() -> {
            try {
                resp.getWriter().println("Unauthorized");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
