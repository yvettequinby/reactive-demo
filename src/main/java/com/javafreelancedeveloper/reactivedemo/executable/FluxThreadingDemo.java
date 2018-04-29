package com.javafreelancedeveloper.reactivedemo.executable;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Demo of various threading options
 * 
 * @author yvette.quinby
 *
 */
public class FluxThreadingDemo {

	public static void main(String[] args) {
		FluxThreadingDemo demo = new FluxThreadingDemo();
		demo.parallelDemo();
		demo.intervalDemo();
	}

	/**
	 * Demo of Schedulers.parallel()
	 */
	private void parallelDemo() {
		System.out.println("parallelDemo start.");
		CountDownLatch latch = new CountDownLatch(1); // we will use this to make the main thread wait for the subscription thread to finish
		Flux.range(1, 50) // a range flux
				.publishOn(Schedulers.parallel()) // Create as many threads as there are CPUs (min 4) and force the next operator to run on a different thread
				.subscribe( // do the subscription
						i -> { // on next
							System.out.println("I am happening in another thread! " + i);
						}, e -> { // on error
							System.err.println("Error: " + e);
							e.printStackTrace();
							latch.countDown();
						}, () -> { // on complete
							System.out.println("Subscribe is complete!");
							latch.countDown();
						});
		try {
			latch.await(); // wait for the subscription to finish before terminating this thread
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("parallelDemo finish.");
	}

	/**
	 * Demo of Flux.interval and Flux.retry
	 */
	private void intervalDemo() {
		System.out.println("intervalDemo start.");
		CountDownLatch latch = new CountDownLatch(1); // we will use this to make the main thread wait for the subscription thread to finish
		Flux.interval(Duration.ofMillis(500)) // emits incrementing values at specified time intervals. Runs on the Schedulers.parallel() Scheduler.
				.map(input -> {
					if (input < 3) {
						return "Tick: " + input;
					} else {
						throw new RuntimeException("KABOOM!"); // force an error after 3 ticks
					}
				}).retry(1) // if an error occurs, try to reconnect once
				.subscribe(s -> { // on next
							System.out.println("I am happening in another thread! " + s);
						}, e -> { // on error
							System.err.println("Error: " + e);
							e.printStackTrace();
							latch.countDown();
						}, () -> { // on complete
							System.out.println("Subscribe is complete!");
							latch.countDown();
						});
		try {
			latch.await(); // wait for the subscription to finish before terminating this thread
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("intervalDemo finish.");
	}
}
