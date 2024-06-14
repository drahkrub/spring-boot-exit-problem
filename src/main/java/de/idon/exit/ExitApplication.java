package de.idon.exit;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ExitApplication {

    static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = SpringApplication.run(ExitApplication.class, args);
    }
    
    @PostConstruct
    public void postConstruct() {
        new Thread(() -> {
            try {
                // giving Tomcat some time to start
                Thread.sleep(500);
                String response = (new RestTemplate()).getForObject("http://localhost:8080", String.class);
                System.out.println("Simulated request response: " + response);
                // calling exit() here seems to works reliably
//                exit();
            } catch (Throwable t) {
                System.err.println(t.getMessage());
            }
        }).start();
    }

    void exit() {
        System.exit(SpringApplication.exit(ctx, () -> 42));
    }

    @RestController
    public class SomeController {

        @GetMapping
        public String get() {
            // calling exit() here gives varying exit codes,
            // sometimes 42 as intended, but sometimes also 0
            exit();
            return "hello";
        }
    }
}
