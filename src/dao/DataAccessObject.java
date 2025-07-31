package src.dao;

import src.Exception.RetailInventoryException;

public interface DataAccessObject<T> {
    void create(T entity) throws RetailInventoryException;
    T findById(int id) throws RetailInventoryException;
    void update(T entity) throws RetailInventoryException;
    void delete(int id) throws RetailInventoryException;
}
