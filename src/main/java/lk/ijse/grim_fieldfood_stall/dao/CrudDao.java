package lk.ijse.grim_fieldfood_stall.dao;

import java.util.List;

public interface CrudDao<T> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(String id);
    T get(String id);
    List<T> getAll();
}
