package org.stock.api.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
@Repeatable(ValidateParams.class)
public @interface ValidateParam {	
	String name();
	WhiteList sanitize() default WhiteList.DEFAULT;
	boolean required() default false;
}
