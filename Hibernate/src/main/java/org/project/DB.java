package org.project;

import org.hibernate.Session;

public class DB {

    public static void saveObject(Object object) {
        Session session = MysqlConnector.open();

        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();

        MysqlConnector.close();
    }

    public static void readObject(int id, Class<?> type) {
        Session session = MysqlConnector.open();

        session.beginTransaction();
        session.get(type, id);
        session.getTransaction().commit();

        MysqlConnector.close();
    }
}
