package org.project.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DB {

    private static final Logger logger = LogManager.getLogger(DB.class);
    private static MysqlConnector connector;

    public static MysqlConnector open() {
        if (connector == null) {
            connector = MysqlConnector.open();
        }
        return connector;
    }

    public static <T> T executeQuery(String query, Class<T> resultType) {
        Session session = open().currentSession();

        session.beginTransaction();
        Query<T> queryToExecute = session.createQuery(query, resultType);
        T result = queryToExecute.getSingleResult();
        session.getTransaction().commit();
        return result;
    }

    public static void saveObject(Object object) {
        Session session = open().currentSession();

        logger.info("Saving object: " + object);

        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();
    }

    public static <T> T readObject(int id, Class<T> type) {
        Session session = open().currentSession();

        logger.info("Reading object");

        session.beginTransaction();
        T object = session.get(type, id);
        session.getTransaction().commit();

        return object;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> readObjectsWhere(String tableName, String conditionColumnName, String condition,
                                               String conditionValue) {
        Session session = open().currentSession();

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

    public static void removeObject(Object object) {
        Session session = open().currentSession();

        logger.info("Removing object: " + object);

        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }

    public static void shutdown() {
        if (connector != null) {
            connector.shutdown();
        }
    }
}
