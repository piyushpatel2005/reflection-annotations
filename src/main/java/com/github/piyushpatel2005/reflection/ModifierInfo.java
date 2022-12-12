package com.github.piyushpatel2005.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ModifierInfo {
    public static void main(String[] args) throws NoSuchMethodException {
        Entity e = new Entity(10, "id");
        Class<? extends Entity> clss = e.getClass();
        System.out.println("====== Check modifier of class =====");
        int modifiersInt = clss.getModifiers();
        int i = modifiersInt & Modifier.PUBLIC;
        System.out.println(i);

        System.out.println("====== Check modifier of getVal method =======");
        Method getVal = clss.getMethod("getVal");
        System.out.println(getVal.getModifiers() & Modifier.PRIVATE); // check if it's private
        System.out.println(Modifier.isPublic(getVal.getModifiers())); // another way to check
    }
}
