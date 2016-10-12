package com.green.car.injector;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用Res标记字段应确保使用正确的类型，必须是View或其子类型，并且确保layout文件使用的控件是该字段的类型或该字段的子类型！
 *
 * @author yangcheng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Res {
    public int vid() default -1;
	public String click() default "";
	public String itemClick() default "";
	public String itemLongClick() default "";
}