package net.vanderkast.properties_annotation_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface Property {
    String value();

    boolean optional() default false;

    String defaults() default "";
}
