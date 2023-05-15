package backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ComponentScan(basePackages = "backend")
public class BackendApplication {

    @Scheduled(cron = "0 46 21 * * ?", zone = "Europe/Paris")
    private void mrdej() {
        System.out.println("Vole");
    }
    
    public static void main(String[] args) {
        String env;
        if (System.getenv("BANK_ENV") == null) {
            env = "dev";
        } else {
            env = "prod";
        }

        new SpringApplicationBuilder()
                .profiles(env) // and so does this
                .sources(BackendApplication.class)
                .run(args);

    }

}
