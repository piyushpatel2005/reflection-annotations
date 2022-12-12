package com.github.piyushpatel2005.spring;

import com.github.piyushpatel2005.spring.annotation.Autowired;
import com.github.piyushpatel2005.spring.annotation.Component;
import com.github.piyushpatel2005.spring.annotation.ComponentScan;
import com.github.piyushpatel2005.spring.annotation.Configuration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ApplicationContext {
    private static HashMap<Class<?>, Object> map = new HashMap<>();

    public ApplicationContext(Class<AppConfig> clss) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Spring.initializeSpringContext(clss);
    }

    public <T> T getBean(Class<T> clss) throws IllegalAccessException {
        T object = (T) map.get(clss); // This gives us instance of ProductService but we still need to inject ProductRepository into it.
        Field[] declaredFields = clss.getDeclaredFields();

        injectBean(object, declaredFields);
        return object;
    }

    private <T> void injectBean(T object, Field[] declaredFields) throws IllegalAccessException {
        for(Field field: declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object innerObject = map.get(type);
                field.set(object, innerObject);
                // what if repository also has dependencies. So, we will do recursive way
                Field[] declaredFields2 = type.getDeclaredFields();
                injectBean(innerObject, declaredFields2);
            }
        }
    }


    private static class Spring {
        private static void initializeSpringContext(Class<?> clss) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            if (!clss.isAnnotationPresent(Configuration.class)) {
                throw new RuntimeException("The file is not a Configuration file");
            } else {
                ComponentScan annotation = clss.getAnnotation(ComponentScan.class);
                String value = annotation.value();

//                String packageStructure = "bin/" + value.replace(".", "/"); // Eclipse
                String packageStructure = "target/classes/" + value.replace(".", "/"); // IntelliJ
                File[] files = findClasses(new File(packageStructure));

                for(File file: files) {
                    String name = value + "." + file.getName().replace(".class", "");
                    Class<?> loadingClass = Class.forName(name);
                    if (loadingClass.isAnnotationPresent(Component.class)) {
                        Constructor<?> constructor = loadingClass.getConstructor();
                        Object newInstance = constructor.newInstance();
                        map.put(loadingClass, newInstance);
                    }
                }
            }
        }

        private static File[] findClasses(File file) {
            if (!file.exists()) {
                throw new RuntimeException("Package " + file + " does not exist.");
            } else {
                File[] listOfFiles = file.listFiles(e -> e.getName().endsWith(".class"));
                return listOfFiles;
            }
        }
    }

}
