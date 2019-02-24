package services;

import dbService.DBService;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * @author v.chibrikov
 * <p>
 * Пример кода для курса на https://stepic.org/
 * <p>
 * Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {
    private final DBService dbService;
    private UsersDAO cachedUsersDAO;

    public AccountService() {
        this.dbService = new DBService();
        cachedUsersDAO = new UsersDAO();
    }

    public void addNewUser(UsersDataSet userProfile) {
        execInSession(usersDAO -> {
            usersDAO.insertUser(userProfile);
            return null;
        });
    }

    public UsersDataSet getUserByLogin(String login) {
        return execInSession(usersDAO -> {
            return usersDAO.findUserByLogin(login);
        });
    }

    private UsersDataSet execInSession(Function<UsersDAO, UsersDataSet> usersDAOConsumer) {
        Session session = dbService.getSessionFactory().openSession();
        cachedUsersDAO.setSession(session);
        UsersDataSet usersDataSet = usersDAOConsumer.apply(cachedUsersDAO);
        session.close();
        return usersDataSet;
    }
}
