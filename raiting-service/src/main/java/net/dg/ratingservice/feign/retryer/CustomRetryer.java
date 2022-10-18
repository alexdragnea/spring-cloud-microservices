package net.dg.ratingservice.feign.retryer;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomRetryer implements Retryer {

    private final int maxAttempts;
    private final long backoff;
    int attempt;

    /**
     * Waits for 10
     * second before retrying.
     */
    public CustomRetryer() {
        this(1000, 3);
    }

    public CustomRetryer(long backoff,
                         int maxAttempts) {
        this.backoff = backoff;
        this.maxAttempts = maxAttempts;
        this.attempt = 0;
    }

    public void continueOrPropagate(RetryableException e) {

        if (attempt++ >= maxAttempts) {
            throw e;
        }

        try {
            TimeUnit.MILLISECONDS.sleep(backoff);
        } catch (InterruptedException ex) {

        }

        log.info("Retrying: " + e.request().url() + " attempt " + attempt);
    }

    @Override
    public Retryer clone() {
        return new CustomRetryer(backoff, maxAttempts);
    }
}