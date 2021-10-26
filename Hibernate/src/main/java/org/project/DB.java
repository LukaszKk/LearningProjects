package org.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class DB {

    private static final Logger logger = LogManager.getLogger(DB.class);

    public static void saveObject(Object object) {
        try (MysqlConnector connector = MysqlConnector.open()) {
            Session session = connector.session();

            logger.info("Saving object: " + object);

            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        }

    }

    public static <T> T readObject(int id, Class<T> type) {
        try (MysqlConnector connector = MysqlConnector.open()) {
            Session session = connector.session();

            logger.info("Reading object");

            session.beginTransaction();
            T object = session.get(type, id);
            session.getTransaction().commit();

            return object;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> readObjectsWhere(String tableName, String conditionColumnName, String condition,
                                               String conditionValue) {
        try (MysqlConnector connector = MysqlConnector.open()) {
            Session session = connector.session();

            logger.info("Reading objects");

            session.beginTransaction();
            List<T> objects = session.createQuery("from " + tableName
                    + " t where "
                    + "t." + conditionColumnName
                    + condition +
                    "'" + conditionValue + "'").getResultList();
            session.getTransaction().commit();

            return objects;
        }
    }
}
