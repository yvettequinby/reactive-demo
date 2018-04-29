package com.javafreelancedeveloper.reactivedemo.executable;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

/**
 * Demo of broadcasting to multiple subscribers with ConnectableFlux
 * 
 * @author yvette.quinby
 *
 */
public class FluxBroadcastDemo {

	public static void main(String[] args) {
		FluxBroadcastDemo demo = new FluxBroadcastDemo();
		demo.connectableFluxConnectDemo();
		demo.connectableFluxAutoDemo();
	}

	/**
	 * Demo of ConnectableFlux, with multiple subscribers and a manual connection.
	 */
	private void connectableFluxConnectDemo() {
		List<String> ashesToAshes = Arrays.asList("You'd", "better", "not", "mess", "with", "Major", "Tom"); // data for the flux
		Flux<String> sourceFlux = Flux.fromIterable(ashesToAshes).doOnSubscribe(s -> System.out.println("subscribed to source")); // prepare the flux
		ConnectableFlux<String> connectableFlux = sourceFlux.publish(); // make a connectable flux
		connectableFlux.subscribe( // this will not trigger anything. A connection to connectableFlux is required to trigger action.
				s -> { // on next
					System.out.println("First subscriber has data: " + s);
				}, e -> { // on error
					System.err.println("First subscriber has error: " + e);
				}, () -> { // on complete
					System.out.println("First subscriber has completed.");
				});
		connectableFlux.subscribe( // this will not trigger anything. A connection to connectableFlux is required to trigger action.
				s -> { // on next
					System.out.println("Second subscriber has data: " + s);
				}, e -> { // on error
					System.err.println("Second subscriber has error: " + e);
				}, () -> { // on complete
					System.out.println("Second subscriber has completed.");
				});
		System.out.println("Subscriptions to connectableFlux ready.");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("About to connect... action should start now!");
		connectableFlux.connect();
		System.out.println("Connected!");
	}

	/**
	 * Demo of ConnectableFlux, with multiple subscribers and an auto connection.
	 */
	private void connectableFluxAutoDemo() {
		List<String> ashesToAshes = Arrays.asList("You'd", "better", "not", "mess", "with", "Major", "Tom"); // data for the flux
		Flux<String> sourceFlux = Flux.fromIterable(ashesToAshes).doOnSubscribe(s -> System.out.println("subscribed to source")); // prepare the flux
		Flux<String> connectableFlux = sourceFlux.publish().autoConnect(2); // make a connectable flux that will start publishing after 2 subscriptions
		connectableFlux.subscribe( // this will not trigger anything...
				s -> { // on next
					System.out.println("First subscriber has data: " + s);
				}, e -> { // on error
					System.err.println("First subscriber has error: " + e);
				}, () -> { // on complete
					System.out.println("First subscriber has completed.");
				});
		System.out.println("First subscription ready.");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("Making second subscription...");
		connectableFlux.subscribe( // this the second subscription and will cause an auto-connect.
				s -> { // on next
					System.out.println("Second subscriber has data: " + s);
				}, e -> { // on error
					System.err.println("Second subscriber has error: " + e);
				}, () -> { // on complete
					System.out.println("Second subscriber has completed.");
				});
	}
}
