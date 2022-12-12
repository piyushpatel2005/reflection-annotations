package com.github.piyushpatel2005.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        TransactionHistory nehal = new TransactionHistory(123, "Nehal", "Savings", 10000);
        TransactionHistory ankit = new TransactionHistory(1234, "Ankit", "Current", 7000);
        TransactionHistory bob = new TransactionHistory(234, "Bob", "Credit", 34000);
        TransactionHistory jessi = new TransactionHistory(456, "Jessi", "Savings", 21000);

        Hibernate<TransactionHistory> hibernate = Hibernate.getConnection();

        hibernate.write(nehal);
        hibernate.write(ankit);
        hibernate.write(bob);
        hibernate.write(jessi);

        TransactionHistory obj1 = hibernate.read(TransactionHistory.class, 1L);
        System.out.println(obj1);
    }
}
