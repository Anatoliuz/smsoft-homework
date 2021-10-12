package ru.smsoft;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import ru.smsoft.loader.service.Loader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Mojo(name = "info", defaultPhase = LifecyclePhase.COMPILE, threadSafe = true)
public class DataInfoPlugin extends AbstractMojo {

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final TimeUnit time = TimeUnit.SECONDS;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        var callables = new ArrayList<Callable<Void>>();
        var runnables = new ArrayList<Runnable>();
        AtomicReference<Date> start = new AtomicReference<>();
        AtomicReference<Date> finish = new AtomicReference<>();

        runnables.add(() -> {
            start.set(new Date());
            getLog().info("Loading started at: " + FORMATTER.format(start.get()));
            while (!Loader.isFinish()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            getLog().info("Loaded records = " + Loader.getLoadedCount());
        });

        runnables.add(() -> {
            try {
                Loader.main(new String[] {"C:\\Users\\afili\\Desktop\\homework\\homework_maven\\csv-to-db-loader\\src\\main\\resources\\test_case.csv"});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        runnables.forEach(r -> callables.add(toCallable(r)));

        try {
            executor.invokeAll(callables);
        } catch (Exception e) {
            getLog().info("Exception occured:" + e.getMessage());
        }
        finish.set(new Date());
        long diff = time.convert(finish.get().getTime() - start.get().getTime(), TimeUnit.MILLISECONDS);
        getLog().info("Loading finished at " + FORMATTER.format(new Date()) + " total time is " + diff + " s ?");
    }

    private Callable<Void> toCallable(final Runnable runnable) {
        return () -> {
            runnable.run();
            return null;
        };
    }


}
