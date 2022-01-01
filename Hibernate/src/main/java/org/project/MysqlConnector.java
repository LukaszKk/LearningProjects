package org.project;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.project.beans.Course;
import org.project.beans.Instructor;
import org.project.beans.InstructorDetails;
import org.project.beans.Review;
import org.project.beans.Student;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class MysqlConnector implements AutoCloseable {

    private static final Logger logger = LogManager.getLogger(MysqlConnector.class);
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "lIS0ib7b320=";
    private static final String DB_NAME = "myhibernate";
    private static SessionFactory sessionFactory;
    private static Session session;
    private final MysqlDataSource dataSource;

    private MysqlConnector() {
        dataSource = new MysqlDataSource();
        dataSource.setUser(DB_USER);
        dataSource.setPassword(decrypt(DB_PASSWORD));
        dataSource.setDatabaseName(DB_NAME);
    }

    public static MysqlConnector open() {
        return new MysqlConnector();
    }

    public Session currentSession() {
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
        properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.connection.username", "root");
        properties.setProperty("hibernate.connection.password", "root");
        properties.setProperty("hibernate.current_session_context_class",
                "org.hibernate.context.internal.ThreadLocalSessionContext");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        // drop db on startup
        // properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        var configuration = new Configuration().addProperties(properties);
        mapClasses(configuration);

        return configuration;
    }

    private static void mapClasses(Configuration configuration) {
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Instructor.class);
        configuration.addAnnotatedClass(InstructorDetails.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Review.class);
    }

    @Override
    public void close() {
        if (session.isOpen()) {
            session.close();
            sessionFactory.close();
        }
    }

    public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            var DESEDE_ENCRYPTION_SCHEME = "DESede";
            var arrayBytes = "ThisIsSpartaThisIsSparta".getBytes(StandardCharsets.UTF_8);
            var keySpec = new DESedeKeySpec(arrayBytes);
            var cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
            var secretKeyFactory = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
            var key = secretKeyFactory.generateSecret(keySpec);

            cipher.init(Cipher.DECRYPT_MODE, key);
            var encryptedText = Base64.decodeBase64(encryptedString);
            var plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception e) {
            logger.error("Decryption error");
        }
        return decryptedText;
    }
}
