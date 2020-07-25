package com.web.app.repository.hibernate;

import com.web.app.entity.UsersEntity;
import com.web.app.model.AuthenticationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UsersRepositoryHibernateImpl implements UsersRepositoryHibernate {

    private final SessionFactory sessionFactory;

    @Autowired
    public UsersRepositoryHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //TODO: подумать над обработкой ошибки + вот я пытаюсь сохранить юзера с существующим логином/юзернеймом - мне
    // постгрес должен кинуть бан(юник кей и праймари кей) - подумать, что это за 'бан' и как его обработать
    @Override
    public void save(UsersEntity usersEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.save(usersEntity);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("IN save(UsersEntity usersEntity), UsersRepositoryHibernateImpl.class - user, having same " +
                            "login or name already exists (email: {}, name: {})",
                    usersEntity.getEmail(),
                    usersEntity.getName());
            transaction.rollback();
        }
    }

    //TODO: дописать логику - надо исползовать джоины
    @Override
    public UsersEntity loginByEmail(AuthenticationRequestDTO loginPassword) throws UsernameNotFoundException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String login = loginPassword.getLogin();
        String password = loginPassword.getPassword();
        UsersEntity user = null;
        try (session) {
            user = session
                    .createNativeQuery("SELECT FROM users u WHERE u.email = :email AND u.password = :password",
                            UsersEntity.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (user == null) {
            log.error("IN loginByEmail(AuthenticationRequestDTO loginPassword), UsersRepositoryHibernateImpl.class - " +
                    "user, having login: " + login + " and password: " + password + "doesn't exist");
            throw new UsernameNotFoundException("user, having login: " + login + " and password: " + password +
                    "doesn't exist");
        }

        return user;
    }

    @Override
    public void clear() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try(session) {
            session.createNativeQuery("DELETE FROM users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            log.error("IN clear(), UsersRepositoryHibernateImpl.class - couldn't clear DB");
            transaction.rollback();
        }
    }
}
