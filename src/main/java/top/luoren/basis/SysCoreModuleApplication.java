package top.luoren.basis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.converter.json.GsonFactoryBean;

/**
 * @author luoren
 */
@EnableCaching
@SpringBootApplication
public class SysCoreModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysCoreModuleApplication.class, args);
    }

}
