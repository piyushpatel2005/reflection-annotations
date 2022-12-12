package com.github.piyushpatel2005.annotations;

import java.util.ArrayList;

class Parent {
    public void method1() {
        System.out.println("Method 1 from Parent");
    }

    public void method2() {
        System.out.println("Method 2 from Parent");
    }
}

@FunctionalInterface // cannot add more than one abstract method to this interface
interface MyFunctionalInterface {
    public void method();
}

public class GeneralPurposeAnnotations extends Parent {
    @Override
    public void method1() {
        System.out.println("Method 1 from Child");
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        int a = 10;

        @SuppressWarnings({"unused", "rawtypes"})
//        @SuppressWarnings("all")
        ArrayList intArr = new ArrayList();

        @Deprecated
        Integer i = new Integer(1);

        MyFunctionalInterface impl = () -> System.out.println("Functional interface impl");
        impl.method();
    }
}
