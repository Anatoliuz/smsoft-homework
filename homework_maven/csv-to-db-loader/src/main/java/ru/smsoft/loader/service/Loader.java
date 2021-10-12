package ru.smsoft.loader.service;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.smsoft.loader.data.hibernate.HibernateFactory;
import ru.smsoft.loader.util.Parser;

import java.io.IOException;

public class Loader {

    private static boolean isFinish = false;
    private static int loadedCount = 0;

    public static void main(String[] pathToCsv) throws IOException {
        Transaction transaction = null;
        var records = Parser.convertCsvToRecords(pathToCsv[0]);
        if (records == null) {
            return;
        }
        try (SessionFactory factory = HibernateFactory.getSessionFactory()) {
            var session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createQuery("delete from Record ").executeUpdate();
            for (int i = 0; i < records.size(); ++i) {
                session.save(records.get(i));
                ++loadedCount;
            }
            transaction.commit();
            isFinish = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static boolean isFinish() {
        return isFinish;
    }

    public static int getLoadedCount() {
        return loadedCount;
    }

}
