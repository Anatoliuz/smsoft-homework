package ru.smsoft.loader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import ru.smsoft.loader.loader.service.Loader;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Mojo(name = "info", defaultPhase = LifecyclePhase.COMPILE, threadSafe = true)
public class DataInfoPlugin extends AbstractMojo {

    public static final String CSV_FILE_PATH = "C:\\Users\\afili\\Desktop\\homework\\homework_maven\\csv-to-db-loader\\src\\main\\resources\\test_case.csv";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public void execute() {
       Date start = Date.from(Instant.now());

        getLog().info("CSV to DB loading...");

        Thread thread = new Thread(
                () -> {
                    try {
                        int loadedRowsNumber = Loader.load(CSV_FILE_PATH);
                        getLog().info("Loaded records = " + loadedRowsNumber);

                    } catch (Exception e) {
                        getLog().error("Some Error", e);
                    }
                }
        );

        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
           getLog().error("Kek", e);
        }

        Date finish = Date.from(Instant.now());

        long diff = ChronoUnit.MILLIS.between(start.toInstant(), finish.toInstant());
        getLog().info("Loading finished at " + FORMATTER.format(finish) + " total time is " + diff + " s ?");
    }
}
