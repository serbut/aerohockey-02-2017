package com.aerohockey.mechanics;

import com.aerohockey.mechanics.utils.TimeHelper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by sergeybutorin on 16.04.17.
 */
@Service
public class MechanicsExecutor implements Runnable {
    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger(MechanicsExecutor.class);
    private static final long STEP_TIME = 50;

    private final @NotNull GameMechanics gameMechanics;

    private final @NotNull Clock clock = Clock.systemDefaultZone();

    private final Executor tickExecutor = Executors.newSingleThreadExecutor();

    @Autowired
    public MechanicsExecutor(@NotNull GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @Override
    public void run() {
        long lastFrameMillis = STEP_TIME;
        while (true) {
            final long before = clock.millis();

            gameMechanics.gmStep(lastFrameMillis);

            final long after = clock.millis();
            TimeHelper.sleep(STEP_TIME - (after - before));

            if (Thread.currentThread().isInterrupted()) {
                gameMechanics.reset();
                return;
            }
            final long afterSleep = clock.millis();
            lastFrameMillis = afterSleep - before;
        }
    }
}
