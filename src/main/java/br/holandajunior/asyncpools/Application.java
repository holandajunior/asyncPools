package br.holandajunior.asyncpools;

import br.holandajunior.asyncpools.controllers.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by holandajunior on 12/05/17.
 */

@SpringBootApplication
@EnableAsync
public class Application {


    @Bean("taskExecutor")
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);

        return executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
