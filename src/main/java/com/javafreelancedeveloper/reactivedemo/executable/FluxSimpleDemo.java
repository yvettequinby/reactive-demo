package com.javafreelancedeveloper.reactivedemo.executable;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;

import com.javafreelancedeveloper.reactivedemo.subscriber.DemoSubscriber;

/**
 * Demo executable class for creating a simple Flux.
 * 
 * @author yvette.quinby
 *
 */
public class FluxSimpleDemo {

	public static void main(String[] args) {
		FluxSimpleDemo demo = new FluxSimpleDemo();
		demo.firstDemo();
		demo.forceAnErrorDemo();
		demo.customSubscriberDemo();
	}

	/**
	 * This is a really simple demo, showing how to create a simple flux
	 * and how to subscribe.
	 */
	private void firstDemo() {
		// Create a flux like this
		Flux<String> soSimpleFlux1 = Flux.just("halt", "and", "catch", "fire");
		// Simple subscribe
		soSimpleFlux1.subscribe(s -> System.out.println(s.toUpperCase()));
		// Or create a flux like this
		List<String> stringList = Arrays.asList("open", "the", "bay", "doors");
		Flux<String> soSimpleFlux2 = Flux.fromIterable(stringList);
		// Subscribe with error and complete functions
		soSimpleFlux2.subscribe(s -> { // on next
					System.out.println(s.toUpperCase());
				}, e -> { // on error
					System.out.println("Bay doors must be broken");
					e.printStackTrace();
				}, () -> { // on complete
					System.out.println("I can't allow you to do that.");
				});
	}

	/**
	 * Still a simple demo, but in the flux we force an error to see
	 * the error consumable function in use.
	 */
	private void forceAnErrorDemo() {
		Flux<Integer> integerFlux = Flux.range(1, 10).map(i -> {
			if (i <= 9) {
				return i;
			}
			throw new RuntimeException("What is your major malfunction?");
		});
		integerFlux.subscribe(i -> System.out.println(i), error -> System.err.println(error.getMessage()));
	}

	/**
	 * A demo with a custom subscriber
	 */
	private void customSubscriberDemo() {
		DemoSubscriber<Integer> demoSubscriber = new DemoSubscriber<Integer>();
		Flux<Integer> integerFlux = Flux.range(1, 4);
		integerFlux.subscribe(demoSubscriber); // subscribe
	}
}
