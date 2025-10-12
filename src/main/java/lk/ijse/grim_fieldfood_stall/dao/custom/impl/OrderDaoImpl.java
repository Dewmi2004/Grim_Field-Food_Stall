package lk.ijse.grim_fieldfood_stall.dao.custom.impl;

import lk.ijse.grim_fieldfood_stall.config.FactoryConfiguration;
import lk.ijse.grim_fieldfood_stall.dao.custom.OrderDao;
import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(OrderEntity entity) throws Exception {
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
    public List<OrderEntity> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<OrderEntity> list = session.createQuery("from OrderEntity", OrderEntity.class).list();
        session.close();
        return list;
    }
}
