package br.gov.caixa.sivit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class SivitApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SivitApplication.class, args);
    }

}
