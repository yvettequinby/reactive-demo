package com.javafreelancedeveloper.reactivedemo.executable;

import reactor.core.publisher.Flux;

import com.javafreelancedeveloper.reactivedemo.other.PretendEventListener;
import com.javafreelancedeveloper.reactivedemo.other.PretendEventProcessor;

/**
 * Demo executable class for Flux.push
 * 
 * @author yvette.quinby
 *
 */
public class FluxPushDemo {

	public static void main(String[] args) {
		FluxPushDemo demo = new FluxPushDemo();
		demo.eventProcessorBridgeDemo();
	}
	
	/**
	 * A demo of how to use the Flux.create method,
	 * combined with how to use Flux with "event listener"
	 * patterned code.
	 * 
	 * Suitable for processing events from a single producer.  
	 * Can also be asynchronous and can manage backpressure.  
	 * Only one producing thread may invoke next, complete, or error at a time.
	 */
	public void eventProcessorBridgeDemo() {
		PretendEventProcessor eventProcessor = new PretendEventProcessor(1); // create multiple (1) threads in our processor
		Flux<Double> eventProcessorBridge = Flux.push(sink -> {
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
