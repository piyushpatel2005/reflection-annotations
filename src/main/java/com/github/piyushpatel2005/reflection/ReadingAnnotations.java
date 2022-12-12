package com.github.piyushpatel2005.reflection;

import com.github.piyushpatel2005.annotations.MostUsed;
import com.github.piyushpatel2005.annotations.Utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReadingAnnotations {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clss = Class.forName("com.github.piyushpatel2005.annotations.Utility");
        Constructor<?> constructor = clss.getConstructor();
        Utility u = (Utility) constructor.newInstance();

        Method[] methods = clss.getDeclaredMethods();
        for (Method method: methods) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(MostUsed.class)) {
                MostUsed annotation = method.getAnnotation(MostUsed.class);
                String value = annotation.value(); // get default values
                method.invoke(u, value);
                method.invoke(u, "Scala"); // use specific value
            }
        }
    }
}
