package ru.smsoft.loader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import ru.smsoft.loader.loader.service.Loader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Mojo(name = "info", defaultPhase = LifecyclePhase.COMPILE, threadSafe = true)
public class DataInfoPlugin extends AbstractMojo {

    public static final String CSV_FILE_PATH = "C:\\Users\\afili\\Desktop\\homework\\homework_maven\\csv-to-db-loader\\src\\main\\resources\\test_case.csv";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public void execute() {
       Date start = Date.from(Instant.now());

        getLog().info("CSV to DB loading...");
        Loader loader = new Loader();
        Thread thread = new Thread(
                () -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            getLog().info("Loaded records = " + loader.getLoadedCount());
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        getLog().error("Some Error", e);
                    }
                }
        );

        thread.start();
        try {
            int loadedRowsNumber = loader.load(CSV_FILE_PATH);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        thread.interrupt();
        Date finish = Date.from(Instant.now());

        long diff = ChronoUnit.MILLIS.between(start.toInstant(), finish.toInstant());
        getLog().info("Loading finished at " + FORMATTER.format(finish) + " total time is " + diff + " s ?");
    }
}
