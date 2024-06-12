package com.example.lock_case;

import com.example.lock_case.services.SeatReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class LockCaseApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(LockCaseApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		for (int i = 1; i <= 10; i++) {
			int threadId = i;
			executorService.submit(() -> {
				SeatReservationService seatReservationService = applicationContext.getBean(SeatReservationService.class);
				boolean success = seatReservationService.reserveSeat("DENEME01", "Thread-" + threadId);
				if (success) {
					System.out.println("Seat reserved successfully by Thread-" + threadId);
				} else {
					System.out.println("Seat reservation failed by Thread-" + threadId);
				}
			});
		}

		executorService.shutdown();
	}


}
