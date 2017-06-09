package cn.com.watchman.database.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Column {
	/**
	 * 列名
	 *
	 * @return
	 */
	public abstract String name();

	public abstract String type() default "";

	public abstract int length() default 0;
}