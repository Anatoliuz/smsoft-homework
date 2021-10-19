package ru.smsoft.loader.loader.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.smsoft.loader.loader.data.hibernate.HibernateFactory;
import ru.smsoft.loader.loader.model.Record;
import ru.smsoft.loader.loader.util.Parser;

import java.io.IOException;
import java.util.List;

public class Loader {

    int loadedCount = 0;
    public int load(String csvFilePath) throws IOException {
        List<Record> records = Parser.convertCsvToRecords(csvFilePath);
        loadedCount = 0;

        if (records == null) {
            return loadedCount;
        }
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateFactory.getSessionFactory().getCurrentSession();

            transaction = session.beginTransaction();
            session.createQuery("delete from Record ").executeUpdate();

            for (Record entity : records) {
                session.save(entity);
                ++loadedCount;
//                if (loadedCount % 1000 == 0)
//                    System.out.println("loaded count: " + loadedCount);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return loadedCount;
    }

    public int getLoadedCount(){
        return loadedCount;
    }

}
