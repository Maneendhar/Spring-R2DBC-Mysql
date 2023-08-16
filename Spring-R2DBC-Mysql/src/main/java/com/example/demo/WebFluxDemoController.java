package com.example.demo;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.sound.sampled.AudioInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
public class WebFluxDemoController {
	@Autowired
	WorldXRepository worldXRepository;

	@GetMapping("/world_x/{id}")
	public Mono<city> findProduct(@PathVariable(name = "id") long id) {
		return worldXRepository.findById(id).log();
	}

	@GetMapping("/world_x")
	public Flux<city> findProduct() {
		return worldXRepository.findAll().distinct();

	}

	private static void sleepExec(int i) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/world_xcity")
	public List<Customer> getcityDetails() {
		return IntStream.rangeClosed(1, 50).peek(WebFluxDemoController::sleepExec)
				.mapToObj(i -> new Customer(i, "customer : " + i)).collect(Collectors.toList());
	}

	@GetMapping(value = "/world_x1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<city> finddata() {
		return worldXRepository.findAll()
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println(i))
				.map(i -> new city(i.getId(), i.getName(), i.getCountryCode(), i.getDistrict(), i.getInfo())).log();

	}
}
