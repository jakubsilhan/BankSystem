package backend;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ComponentScan(basePackages = "backend")
public class BackendApplication {

    @Scheduled(cron = "0 35 14 * * ?", zone = "Europe/Paris")
    private void updateCurrencies() throws MalformedURLException, IOException {
        InputStream in = new URL("https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.txt").openStream();
        Files.copy(in, Paths.get(System.getProperty("user.dir") + File.separator + "data" + File.separator + "currencies.txt"), StandardCopyOption.REPLACE_EXISTING);
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
