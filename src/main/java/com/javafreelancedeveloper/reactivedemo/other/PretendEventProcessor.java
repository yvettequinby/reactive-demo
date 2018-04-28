package com.javafreelancedeveloper.reactivedemo.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a "pretend" event processor.
 * 
 * Imagine it is something like the kind of class
 * that might be written/provided to connect to a market data API.
 * It would process market data events by notifying a listener as
 * the events occur.
 * 
 * In this example, the processor just generates a number every half second
 * and notifies the listener.
 * 
 * This class will be used in one (or more) of the demos to show
 * how reactive core can be used to bridge this kind of
 * programming style/design to a reactive style.
 * 
 * @author yvette.quinby
 *
 */
public class PretendEventProcessor {

	private PretendEventListener pretendEventListener;
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public void register(PretendEventListener pretendEventListener) {
		this.pretendEventListener = pretendEventListener;
	}
	
	public void start() {
		if(pretendEventListener==null) {
			throw new RuntimeException("No registered listener!");
		}
		executorService.execute(() -> {
			try {
				for (int i = 0; i < 20; i++) {
					Thread.sleep(500);
					Double d = new Double(i*i);
					pretendEventListener.onNext(d);
				}
				pretendEventListener.onComplete();
				executorService.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
				pretendEventListener.onError(e);
				executorService.shutdown();
			}
		});
	}
}
