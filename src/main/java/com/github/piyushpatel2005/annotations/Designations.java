package com.github.piyushpatel2005.annotations;

import javax.print.attribute.standard.Destination;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Designations {

    public Designation[] value();
}
