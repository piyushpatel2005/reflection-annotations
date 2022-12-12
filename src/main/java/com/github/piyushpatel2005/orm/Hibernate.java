package com.github.piyushpatel2005.orm;

import com.github.piyushpatel2005.orm.annotation.Column;
import com.github.piyushpatel2005.orm.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hibernate<T> {

    private Connection conn;
    private AtomicLong id = new AtomicLong(0l);

    private Hibernate() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:h2://path/to/reflection-annotations/database/practice", "sa", "");
    }

    public static <T> Hibernate<T> getConnection() throws SQLException {
        return new Hibernate<T>();
    }

    public void write(T t) throws IllegalAccessException, SQLException {
        Class<? extends Object> clss = t.getClass();
        Field[] declaredFields = clss.getDeclaredFields();

        Field primaryKey = null;
        List<Field> columns = new ArrayList<>();
        StringJoiner joiner = new StringJoiner(",");

        for (Field field: declaredFields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                primaryKey = field;
//                System.out.println("The primary key is " + field.getName());
            } else if (field.isAnnotationPresent(Column.class)) {
                joiner.add(field.getName());
                columns.add(field);
//                System.out.println("Column : " + field.getName() + ": " + field.get(t));
            }
        }

        int numFields = joiner.length() + 1; // 1 extra due to PK
        String questionMarks = IntStream.range(0, numFields)
                .mapToObj(elem -> "?")
                .collect(Collectors.joining(","));

        String sql = "insert into " + clss.getSimpleName() + "( " + primaryKey.getName() + joiner.toString() + ") values (" + questionMarks + ")";
        PreparedStatement stmt = conn.prepareStatement(sql);

        if (primaryKey.getType() == long.class) {
            stmt.setLong(1, id.incrementAndGet());
        }

        int index = 2;
        for (Field field: columns) {
            field.setAccessible(true);
            if (field.getType() == int.class) {
                stmt.setInt(index++, (int) field.get(t));
            } else if (field.getType() == String.class) {
                stmt.setString(index++, (String) field.get(t));
            }
        }
        stmt.executeUpdate();
    }

    public T read(Class<T> clss, long id) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Field[] declaredFields = clss.getDeclaredFields();
        Field primaryKey = null;
        for(Field field: declaredFields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                primaryKey = field;
                break;
            }
        }
        String sql = "select * from " + clss.getSimpleName() + " where " + primaryKey.getName() + " = " + id;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        rs.next();

        T t = clss.getConstructor().newInstance();

        long transactionId = rs.getInt(primaryKey.getName());
        primaryKey.setAccessible(true);
        primaryKey.set(t, transactionId);

        for (Field field: declaredFields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                if (field.getType() == int.class) {
                    field.set(t, rs.getInt(field.getName()));
                } else if (field.getType() == String.class) {
                    field.set(t, rs.getString(field.getName()));
                } else {
                    throw new IllegalArgumentException("Incorrect type of the field");
                }
            }
        }

        return t;
    }
}
