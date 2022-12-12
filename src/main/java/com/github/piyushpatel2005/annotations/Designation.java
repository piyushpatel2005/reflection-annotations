package com.github.piyushpatel2005.annotations;

import java.lang.annotation.*;

@Repeatable(value = Designations.class) // This makes this annotation repeatable more than once on the same type
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Designation {

    String value() default "Employee";
}
