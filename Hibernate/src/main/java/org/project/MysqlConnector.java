package org.project;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.project.beans.Student;

import java.util.Properties;

public class MysqlConnector {

    private static final MysqlDataSource dataSource;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_NAME = "myhibernate";
    private static SessionFactory sessionFactory;
    private static Session session;

    static {
        dataSource = new MysqlDataSource();
        dataSource.setUser(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDatabaseName(DB_NAME);
    }

    public static Session open() {
        if (session == null || !session.isOpen()) {
            var configuration = configure();
            sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .applySetting(Environment.DATASOURCE, dataSource)
                    .build());
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }

    private static Configuration configure() {
        var properties = new Properties();
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + DB_NAME);
        properties.setProperty("dialect", "org.hibernate.dialect.MysqlDialect");
        properties.setProperty("hibernate.connection.username", "root");
        properties.setProperty("hibernate.connection.password", "root");
        properties.setProperty("hibernate.current_session_context_class",
                "org.hibernate.context.internal.ThreadLocalSessionContext");

        var configuration = new Configuration().addProperties(properties);
        configuration.addClass(Student.class);

        return configuration;
    }


    public static void close() {
        if (session.isOpen()) {
            session.close();
            sessionFactory.close();
        }
    }
}
