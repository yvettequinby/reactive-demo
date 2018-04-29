package com.javafreelancedeveloper.reactivedemo.executable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

/**
 * Demo of a "hot" flux
 * 
 * @author yvette.quinby
 *
 */
public class FluxHotDemo {

	public static void main(String[] args) {
		FluxHotDemo demo = new FluxHotDemo();
		demo.hotSourceDemo();
	}

	/**
	 * Demo of a hot flux, using a UnicastProcessor.
	 * A hot flux emits data whether there is a subscriber or not.
	 * Subscribers only get data emitted after they subscribe.
	 * 
	 */
	private void hotSourceDemo() {
		UnicastProcessor<String> hotSource = UnicastProcessor.create();
		Flux<String> hotFlux = hotSource.publish().autoConnect().map(String::toUpperCase);
		hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: " + d)); // will get all emissions
		hotSource.onNext("ground");
		hotSource.onNext("control");
		hotSource.onNext("to");
		hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: " + d)); // only gets emissions after this line
		hotSource.onNext("Major");
		hotSource.onNext("Tom");
		hotSource.onComplete();
	}
}
