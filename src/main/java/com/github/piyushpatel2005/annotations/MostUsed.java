package com.github.piyushpatel2005.annotations;

import java.lang.annotation.*;

@Documented // creates documentation
@Inherited // Annotation using Child classes will inherit all annotations
@Target({ElementType.TYPE, ElementType.METHOD}) // Target entity to which this meta annotation is used.
@Retention(RetentionPolicy.RUNTIME) // used to define how long a particular annotation should be retained
public @interface MostUsed {
    String value() default "Java";
}
