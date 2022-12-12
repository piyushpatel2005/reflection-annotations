package com.github.piyushpatel2005.reflection;

import java.lang.reflect.Field;

public class FieldInfo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Entity e = new Entity(10, "id");
        Class<? extends Entity> clss = e.getClass();
        System.out.println("====== Get only public fields =========");
        // get only public fields
        Field[] fields = clss.getFields();
        for (Field field: fields) {
            System.out.println(field.getName());
        }
        System.out.println("=========== Get all declared fields (private + public) ===========");
        // get all declared fields
        Field[] allFields = clss.getDeclaredFields();
        for (Field field: allFields) {
            System.out.println(field.getName());
        }
        // get all non-declared means all public elements in that class and its super class.
        // declared means all elements present in that class alone.
        System.out.println("====== Change field name =========");
        Field field = clss.getField("type");
        field.set(e, "identification_number");
        System.out.println(e.type);
        System.out.println("======= Change Private field name ==========");
        // To get private field, we have to use getDeclaredField, not getField
        Field field2 = clss.getDeclaredField("val");
        // set private field to become accessible
        field2.setAccessible(true);
        field2.set(e, 20);
        System.out.println(e.getVal());
    }
}
