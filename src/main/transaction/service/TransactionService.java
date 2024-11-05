package transaction.service;

import exception.ValidationException;
import transaction.entity.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TransactionService<T extends Transaction> {
    public void add(T newTransaction) throws SQLException, ValidationException;

    public ArrayList<T> findAll() throws SQLException, ValidationException;

    public T findById(int id) throws SQLException, ValidationException;

    public void delete(int id) throws SQLException, ValidationException;

    public void deleteMany(int[] ids) throws SQLException, ValidationException;

    public void update(T newTransaction) throws SQLException, ValidationException;
}