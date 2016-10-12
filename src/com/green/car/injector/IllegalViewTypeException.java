package com.green.car.injector;

@SuppressWarnings("serial")
public class IllegalViewTypeException extends RuntimeException {

    IllegalViewTypeException(String msg) {
        super(msg);
    }

    IllegalViewTypeException() {
        super("使用Res标记字段应确保使用正确的类型，必须是View或其子类型，并且确保layout文件使用的控件是该字段的类型或该字段的子类型！");
    }
}
