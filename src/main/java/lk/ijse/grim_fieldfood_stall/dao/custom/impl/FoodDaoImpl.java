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
        try {
            session.persist(entity);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Food entity) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(entity);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        try {
            Food food = session.get(Food.class, Long.parseLong(id));
            if (food != null) session.remove(food);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Food get(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            Food food = session.get(Food.class, Long.parseLong(id));
            return food;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Food> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            List<Food> list = session.createQuery("from Food", Food.class).list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Food findByName(String itemName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Query<Food> query = session.createQuery("FROM Food WHERE name = :name", Food.class);
            query.setParameter("name", itemName);
            Food item = query.uniqueResult();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Food findById(long id) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.get(Food.class, id);
        }
    }
}
