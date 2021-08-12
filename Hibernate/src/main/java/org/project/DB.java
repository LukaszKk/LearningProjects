package org.project;

import org.hibernate.Session;

import java.util.List;

public class DB {

    public static void saveObject(Object object) {
        Session session = MysqlConnector.open();

        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();

        MysqlConnector.close();
    }

    public static <T> T readObject(int id, Class<T> type) {
        Session session = MysqlConnector.open();

        session.beginTransaction();
        T object = session.get(type, id);
        session.getTransaction().commit();

        MysqlConnector.close();

        return object;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> readObjectsWhere(String tableName, String conditionColumnName, String condition,
                                               String conditionValue) {
        Session session = MysqlConnector.open();

        session.beginTransaction();
        List<T> objects = session.createQuery("from " + tableName
                        + " t where "
                        + "t." + conditionColumnName
                        + condition +
                        "'" + conditionValue + "'").getResultList();
        session.getTransaction().commit();

        MysqlConnector.close();

        return objects;
    }
}
