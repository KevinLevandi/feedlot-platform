package com.whitesheep.platform.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Property {
    
    public DataType type() default DataType.TEXT;
    
    public boolean showInput() default true;
    
    public boolean showFilter() default true;

    public boolean showColumn() default true;
    
}
