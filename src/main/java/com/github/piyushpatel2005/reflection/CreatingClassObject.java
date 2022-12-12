package com.github.piyushpatel2005.reflection;

class AnotherClass {
    public AnotherClass() {
        System.out.println("Another Class constructor");
    }
}

public class CreatingClassObject {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1. Class.forName
        Class<?> clss1 = Class.forName("java.lang.String");
        Class<?> clss2 = Class.forName("java.lang.String");

        // All Class referring to same class created using Class.forName point to same reference
        System.out.println(clss1 == clss2);

        // 2. ClassName.class to create Class object
        Class<?> clss3 = int.class;
        Class<?> clss4 = String.class;

        // 3. obj.getClass()
        AnotherClass m = new AnotherClass();
        Class<? extends AnotherClass> clss5 = m.getClass();

        // Methods to get metadata from Class object.
        Class<?> superClass = clss1.getSuperclass();
        // Methods to get all interface of a class
        Class<?>[] interfaces = clss2.getInterfaces();
        // getName()
        System.out.println(clss4.getName());
    }
}
