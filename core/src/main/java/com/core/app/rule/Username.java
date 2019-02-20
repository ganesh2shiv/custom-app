package com.core.app.rule;

import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidateUsing(UsernameRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Username {

    int sequence() default -1;

    int messageResId() default -1;

    String message() default "Invalid username!";

}