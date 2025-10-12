package lk.ijse.grim_fieldfood_stall.dao.custom.impl;

import lk.ijse.grim_fieldfood_stall.config.FactoryConfiguration;
import lk.ijse.grim_fieldfood_stall.dao.custom.OrderFoodDao;
import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;
import lk.ijse.grim_fieldfood_stall.entity.OrderFood;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderFoodDaoImpl implements OrderFoodDao {
    @Override
    public boolean save(OrderFood entity) throws Exception {
         Session session = FactoryConfiguration.getInstance().getSession();
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
    public List<OrderFood> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<OrderFood> list = session.createQuery("from OrderFood", OrderFood.class).list();
        session.close();
        return list;
    }

}
