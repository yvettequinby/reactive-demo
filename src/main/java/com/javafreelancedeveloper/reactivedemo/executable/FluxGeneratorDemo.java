package com.javafreelancedeveloper.reactivedemo.executable;

import javafx.util.Pair;
import reactor.core.publisher.Flux;

/**
 * Demo executable class for Flux.generate
 * 
 * @author yvette.quinby
 *
 */
public class FluxGeneratorDemo {

	public static void main(String[] args) {
		FluxGeneratorDemo demo = new FluxGeneratorDemo();
		demo.fibonacciDemo();
	}

	/**
	 * Demo of how to use the Flux.generate to create a flux.
	 * This is for synchronous and one-by-one emissions.
	 */
	private void fibonacciDemo() {
		Flux<Integer> fibonacciFlux = Flux.generate(() -> new Pair<Integer, Integer>(null, null), // state suplier
				(state, sink) -> {
					// we use the state to compute a fibonacci sequence
					// then we "sink" the fibonacci value and return the new state
					// We will max out the fibonacci at 6765 and flag the flux as complete
				Integer nextFib = 0;
				if (state.getKey() == null && state.getValue() == null) {
					nextFib = 0;
					sink.next(nextFib);
					return new Pair<Integer, Integer>(null, nextFib);
				} else if (state.getKey() == null && state.getValue() == 0) {
					nextFib = 1;
					sink.next(nextFib);
					return new Pair<Integer, Integer>(state.getValue(), nextFib);
				} else {
					nextFib = state.getKey() + state.getValue();
					sink.next(nextFib);
					if (nextFib >= 6765) {
						sink.complete();
					}
					return new Pair<Integer, Integer>(state.getValue(), nextFib);
				}
			});
		fibonacciFlux.subscribe(i -> System.out.println(i), // on next
				e -> System.err.print("Error: " + e), // on error
				() -> System.out.println("Fibonacci Flux Complete!")); // on complete
	}
}
