package components.repository;

import com.web.app.config.HibernateConfig;
import com.web.app.repository.hibernate.UsersRepositoryHibernate;
import com.web.app.repository.hibernate.UsersRepositoryHibernateImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


//TODO: НЕ ПОНЯЛ, clearTest1() РАБОТАЕТ, А clearTest() - НЕТ
public class RepositoryTests {

    private final static UsersRepositoryHibernate usersRepositoryHibernate;

    static  {
        //usersRepositoryHibernate = new UsersRepositoryHibernateImpl(new HibernateConfig().createSessionFactory());

        HibernateConfig hibernateConfig = new HibernateConfig();
        usersRepositoryHibernate = new UsersRepositoryHibernateImpl(hibernateConfig.createSessionFactory());
    }

    @Test
    public void clearTest() {
        HibernateConfig hibernateConfig = new HibernateConfig();
        UsersRepositoryHibernate usersRepositoryHibernate = new UsersRepositoryHibernateImpl(hibernateConfig.createSessionFactory());
        usersRepositoryHibernate.clear();
    }

    @Test
    public void clearTest1() {
        usersRepositoryHibernate.clear();
    }
}
