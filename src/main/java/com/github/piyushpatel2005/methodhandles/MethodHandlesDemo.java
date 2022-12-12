package com.github.piyushpatel2005.methodhandles;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

// Method Handles is performant way to do reflection. It improves performance on same type of object creation or method calls
// because it doesn't have to do authorization in every call.
public class MethodHandlesDemo {
    public static void main(String[] args) throws Throwable {
        Lookup lookup = MethodHandles.lookup();
        Class<?> clss = lookup.findClass(Student.class.getName());

        // Method invocation
        MethodType methodType = MethodType.methodType(String.class); // return type of getCourse method
        MethodHandle getCourseHandle = lookup.findVirtual(clss, "getCourse", methodType);

        Student student = new Student();
        student.setName("Amy");
        student.setCourse("Java Programming");
        String courseName = (String) getCourseHandle.invoke(student);
        System.out.println(courseName);

        // Method with argument setName method
        MethodType argMethodType = MethodType.methodType(void.class, String.class);
        MethodHandle setNameHandle = lookup.findVirtual(clss, "setName", argMethodType);
        setNameHandle.invoke(student, "Amanda");
        System.out.println("Student after setName: " + student);

        // No Arg Constructor creation
        MethodType noArgConstructorType = MethodType.methodType(void.class);
        MethodHandle noArgHandle = lookup.findConstructor(clss, noArgConstructorType);
        Student s = (Student) noArgHandle.invoke();
        System.out.println(s.toString());

        // Constructor with arguments
        MethodType argConstructorType = MethodType.methodType(void.class, String.class, String.class); // constructor taking args of String Types
        MethodHandle argConstructor = lookup.findConstructor(clss, argConstructorType);
        Student john = (Student) argConstructor.invoke("John", "Python Programming");
        System.out.println(john);

        // invoke static methods
        MethodType setNumMethodType = MethodType.methodType(void.class, int.class);
        MethodHandle setNumMethodHandle = lookup.findStatic(clss, "setNumOfStudents", setNumMethodType);
        setNumMethodHandle.invoke(500);
        System.out.println("Student number of Students : " + Student.getNumOfStudents());

        // For working with private fields, we need private lookup object
        Lookup privateLookupIn = MethodHandles.privateLookupIn(clss, lookup);
        // Get getter for a field
        MethodHandle findGetter = privateLookupIn.findGetter(clss, "name", String.class);
        System.out.println(findGetter.invoke(student)); // field must be accessible, public

        // set private field name with private getter.
        MethodHandle findSetter = privateLookupIn.findSetter(clss, "name", String.class);
        findSetter.invoke(student, "Aisha");
        System.out.println(student);

        // There is also VarHandles to work with variables (private or public)
        // This is good for concurrent operations. For single-threaded operations, MethodHandles might be enough.
        VarHandle nameHandle = privateLookupIn.findVarHandle(clss, "name", String.class);
        System.out.println(nameHandle.get(student));
        nameHandle.set(student, "Shamila");
        System.out.println(student);
    }
}
