package cn.watchdog.epay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("cn.watchdog.epay.mapper")
@EnableScheduling
@EnableAsync
public class EPayApplication {
	public static void main(String[] args) {
		SpringApplication.run(EPayApplication.class, args);
	}

}
