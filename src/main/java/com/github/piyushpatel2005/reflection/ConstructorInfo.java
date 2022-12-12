package com.github.piyushpatel2005.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ConstructorInfo {
    private static void printConstructorInfo(Constructor[] constructors) {
        for (Constructor<?> c: constructors) {
            System.out.println(c.getName());
            Arrays.stream(c.getParameters()).forEach(System.out::println);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clss = Class.forName("com.github.piyushpatel2005.reflection.Entity");

        System.out.println("======= Get all constructors (public) =======");
        Constructor<?>[] constructors = clss.getConstructors();
        printConstructorInfo(constructors);

        System.out.println("===== Get all declared constructors (public + Private) =======");
        Constructor<?>[] allConstructors = clss.getDeclaredConstructors();
        printConstructorInfo(allConstructors);

        System.out.println("====== Create object using constructor =====");
        Constructor<?> publicConstructor = clss.getConstructor(int.class, String.class);
        Entity e = (Entity) publicConstructor.newInstance(2, "identification");
        System.out.println(e.getType() + ":" + e.getVal());

        System.out.println("====== Create object using private constructor =======");
        Constructor privateConstructor = clss.getDeclaredConstructor();
        privateConstructor.setAccessible(true);
        Entity e2 = (Entity) privateConstructor.newInstance();
        System.out.println(e2.getType() + ":" + e2.getVal());
    }
}
