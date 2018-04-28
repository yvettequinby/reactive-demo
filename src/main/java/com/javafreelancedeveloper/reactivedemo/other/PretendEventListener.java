package com.javafreelancedeveloper.reactivedemo.other;


public interface PretendEventListener {
	
	void onNext(Double d);

	void onComplete();
	
	void onError(Exception e);
}
