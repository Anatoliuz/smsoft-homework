package ru.smsoft.loader.data.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {

    public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration();
        config.configure();
        return config.buildSessionFactory();
    }

}
