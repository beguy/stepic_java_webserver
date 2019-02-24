package dbService.dao;

import com.sun.istack.internal.Nullable;
import dbService.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Objects;
import java.util.Optional;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UsersDAO {

    private Session session;

    public UsersDAO() {}

    public UsersDataSet getUserById(long id) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public UsersDataSet findUserByLogin(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        criteria.add(Restrictions.eq("login", name));
        @Nullable UsersDataSet findedUser = (UsersDataSet) criteria.uniqueResult();
        return Objects.isNull(findedUser) ? null : findedUser;
    }

    public long insertUser(String name) throws HibernateException {
        return (Long) session.save(new UsersDataSet(name));
    }

    public long insertUser(UsersDataSet user) throws HibernateException {
        return (Long) session.save(user);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
