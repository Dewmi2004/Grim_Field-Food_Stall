package lk.ijse.grim_fieldfood_stall.config;

import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;
import lk.ijse.grim_fieldfood_stall.entity.Food;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {

    private static FactoryConfiguration instance;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {

        Configuration configuration = new Configuration();
//        configuration.configure();
        configuration.addAnnotatedClass(Food.class);
        configuration.addAnnotatedClass(OrderEntity.class);


        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (instance == null) ? instance = new FactoryConfiguration()
                : instance;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}

