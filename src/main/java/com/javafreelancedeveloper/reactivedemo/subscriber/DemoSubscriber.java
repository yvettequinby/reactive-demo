package com.javafreelancedeveloper.reactivedemo.subscriber;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class DemoSubscriber<T> extends BaseSubscriber<T> {

	public void hookOnSubscribe(Subscription subscription) {
		System.out.println("hookOnSubscribe");
		request(1);
	}

	public void hookOnNext(T value) {
		System.out.println("hookOnNext: " + value);
		request(1);
	}
}
