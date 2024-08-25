package org.gaslang.script.lib.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface GasFunction
{
	String name() default "";
	String[] aliases() default {};
	boolean strict() default false;
}
