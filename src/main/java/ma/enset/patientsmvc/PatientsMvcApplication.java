package ma.enset.patientsmvc;

import ma.enset.patientsmvc.dao.Patient;
import ma.enset.patientsmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsMvcApplication.class, args);
	}
	//@Bean
	CommandLineRunner start(PatientRepository patientRepository){
		return args->{
		patientRepository.save(new Patient(null,"Hassan",new Date(),false,12));

		patientRepository.save(new Patient(null,"Mohammed",new Date(),true,321));

		patientRepository.save(new Patient(null,"Yasmine",new Date(),false,100));

		patientRepository.save(new Patient(null,"Hanae",new Date(),true,1000));

		patientRepository.findAll().forEach(p->{
			System.out.println(p.getNom());
		});
		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
