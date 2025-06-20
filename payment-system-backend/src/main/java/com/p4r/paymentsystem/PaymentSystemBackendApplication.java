package com.p4r.paymentsystem;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.p4r.paymentsystem.entities.Payment;
import com.p4r.paymentsystem.entities.Student;
import com.p4r.paymentsystem.enums.PaymentStatus;
import com.p4r.paymentsystem.enums.PaymentType;
import com.p4r.paymentsystem.repository.PaymentRepository;
import com.p4r.paymentsystem.repository.StudentRepository;

@SpringBootApplication
public class PaymentSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSystemBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
		return args -> {
			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Luis")
					.lastName("Romo")
					.code("1234")
					.programId("TEST1")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Angel")
					.lastName("Gonzalez")
					.code("777")
					.programId("TEST2")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Eduardo")
					.lastName("Perez")
					.code("4282")
					.programId("TEST1")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Gonzalo")
					.lastName("Jimenez")
					.code("7839")
					.programId("TEST2")
					.build());

			PaymentType paymentTypes[] = PaymentType.values();

			Random random = new Random();

			studentRepository.findAll().forEach(student -> {
				for (int i = 0; i < 10; i++) {
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.quantity(1000 + (int) (Math.random() * 20000))
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(student)
							.build();

					paymentRepository.save(payment);
				}
			});
		};
	}

}
