package lk.ijse.grim_fieldfood_stall.dao.custom.impl;

import lk.ijse.grim_fieldfood_stall.config.FactoryConfiguration;
import lk.ijse.grim_fieldfood_stall.dao.custom.FoodDao;
import lk.ijse.grim_fieldfood_stall.entity.Food;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FoodDaoImpl implements FoodDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Food entity) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Food entity) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        Food food = session.get(Food.class, id);
        if (food != null) session.remove(food);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public Food get(String id) {
        Session session = factoryConfiguration.getSession();
        Food food = session.get(Food.class, id);
        session.close();
        return food;
    }

    @Override
    public List<Food> getAll() {
        Session session = factoryConfiguration.getSession();
        List<Food> list = session.createQuery("from Food", Food.class).list();
        session.close();
        return list;
    }

    @Override
    public Food findByName(String itemName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Food> query = session.createQuery("FROM Food WHERE name = :name", Food.class);
        query.setParameter("name", itemName);
        Food item = query.uniqueResult();
        session.close();
        return item;
    }

    @Override
    public Food findById(long id) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.get(Food.class, id);
        }
    }
}
