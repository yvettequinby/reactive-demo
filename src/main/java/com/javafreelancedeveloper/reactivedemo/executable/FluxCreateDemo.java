package com.javafreelancedeveloper.reactivedemo.executable;

import reactor.core.publisher.Flux;

import com.javafreelancedeveloper.reactivedemo.other.PretendEventListener;
import com.javafreelancedeveloper.reactivedemo.other.PretendEventProcessor;

/**
 * Demo executable class for Flux.create
 * 
 * @author yvette.quinby
 *
 */
public class FluxCreateDemo {

	public static void main(String[] args) {
		FluxCreateDemo demo = new FluxCreateDemo();
		demo.eventProcessorBridgeDemo();
	}

	/**
	 * A demo of how to use the Flux.create method,
	 * combined with how to use Flux with "event listener"
	 * patterned code.
	 * 
	 * Create can work asynchronously or synchronously and is
	 * suitable for multiple emissions per round.
	 * 
	 */
	public void eventProcessorBridgeDemo() {
		PretendEventProcessor eventProcessor = new PretendEventProcessor(2); // create multiple (2) threads in our processor
		Flux<Double> eventProcessorBridge = Flux.create(sink -> {
			eventProcessor.register(new PretendEventListener() {

				public void onNext(Double d) {
					sink.next(d);
				}

				public void onComplete() {
					sink.complete();
				}

				public void onError(Exception e) {
					sink.error(e);
				}
			});
		});
		eventProcessorBridge.subscribe(d -> {
			System.out.println("Next number in subscription: " + d); // on next
			}, e -> {
				System.err.print("Error in subscription: " + e);
			}, () -> {
				System.out.println("Event Processor Bridge Flux Subscription Complete!");
			});
		eventProcessor.start();
	}
}
