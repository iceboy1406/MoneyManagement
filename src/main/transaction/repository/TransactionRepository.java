package transaction.repository;

import transaction.entity.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TransactionRepository<T extends Transaction> {
    public void add(T newTransaction) throws SQLException;

    public ArrayList<T> findAll() throws SQLException;

    public T findById(int id) throws SQLException;

    public void delete(int id) throws SQLException;

    public void deleteMany(int[] ids) throws SQLException;

    public void update(T newTransaction) throws SQLException;
}
