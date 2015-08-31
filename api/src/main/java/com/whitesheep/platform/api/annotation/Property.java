package com.whitesheep.platform.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Property {
    
    public String name();
    
    public DataType inputType() default DataType.TEXT_FIELD;
    
    public boolean hasInputCrud() default true;
    
    public boolean hasInputFilter() default true;

    public boolean hasView() default true;
    
}
