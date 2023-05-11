package backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(basePackages = "backend")
public class BackendApplication {

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
