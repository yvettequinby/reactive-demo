package com.javafreelancedeveloper.reactivedemo.executable;

import java.util.Arrays;
import java.util.function.Function;

import reactor.core.publisher.Flux;

public class FluxReuseDemo {

	public static void main(String[] args) {
		FluxReuseDemo demo = new FluxReuseDemo();
		demo.transformDemo();
	}

	private void transformDemo() {
		Function<Flux<String>, Flux<String>> transformer = moreThanMeetsTheEye();
		Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple")) // make a flux from color strings
				.doOnNext(System.out::println) // this will print out EVERY item in the flux
				.transform(transformer) // now we do the transform, which will filter and modify the values into a new flux
				.subscribe(d -> {
					System.out.println("Transformed and filtered value: " + d); // print out the subscribed values
					});
		// Now reuse the transformer
		Flux.fromIterable(Arrays.asList("grey", "black", "red", "orange")) // make a flux from color strings
			.doOnNext(System.out::println) // this will print out EVERY item in the flux
			.transform(transformer) // now we do the transform, which will filter and modify the values into a new flux
			.subscribe(d -> {
				System.out.println("Transformed and filtered value: " + d); // print out the subscribed values
				});
	}

	/**
	 * Makes a re-usable transformer function.
	 * 
	 * @return
	 */
	private Function<Flux<String>, Flux<String>> moreThanMeetsTheEye() {
		Function<Flux<String>, Flux<String>> filterAndMap = f -> {
			Flux<String> fs = f.filter(color -> {
				return !color.equals("orange");
			});
			fs = fs.map(String::toUpperCase);
			return fs;
		};
		return filterAndMap;
	}
}
