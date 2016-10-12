package com.green.car.http;


public interface IRequestHandler{
	void onSuccess(Object result, String value);
	void onSuccess(Object result, String value, String tag);
	void onFailure(Object result);
	void onFailure(Object result, String tag);
	void onFailure(Object result, String tag,String error);
}
