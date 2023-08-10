package org.example.db.transaction;

public interface Transaction {
    Transaction begin();
    Transaction commit();
    Transaction rollback();
}
