package computersecurity.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @since 14-Apr-21
 * @see computersecurity.server.controller.UserController
 */
@SpringBootApplication
public class ServerMain {
    private static boolean wasShutDown = false;
    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        System.out.println("Enter Main");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ServerMain.class);
        applicationContext = builder.run(args);

        Runtime.getRuntime().addShutdownHook(new Thread(ServerMain::shutdown, "ServerShutdownThread"));
        System.out.println("Exit Main");
    }

    private static void shutdown() {
        if (!wasShutDown) {
            wasShutDown = true;
            System.out.println("Shutting down Server");
            SpringApplication.exit(applicationContext, () -> 0);
        }
    }
}

