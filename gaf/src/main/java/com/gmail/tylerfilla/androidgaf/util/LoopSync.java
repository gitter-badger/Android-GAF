package com.gmail.tylerfilla.androidgaf.util;

import java.util.HashMap;
import java.util.Map;

public class LoopSync {

    private static final Map<Thread, LoopState> stateMap = new HashMap<>();

    /**
     * Attempts to match the current loop's frequency to the target frequency on a per-thread
     * basis.
     */
    public static void sync() {
        // Get current thread
        Thread currentThread = Thread.currentThread();

        // Register current thread if not already
        if (!stateMap.containsKey(currentThread)) {
            stateMap.put(currentThread, new LoopState());
        }

        // Get loop state for current thread
        LoopState state = stateMap.get(currentThread);

        // Increment iteration count
        state.totalIterations++;

        // Calculate remaining time in nanoseconds
        long remainingTime = state.targetDuration - (System.nanoTime() - state.timeLastIteration);

        // Attempt to match target direction
        if (remainingTime > 0) {
            try {
                Thread.sleep(remainingTime / 1000000l, (int) (remainingTime % 1000000));
            } catch (InterruptedException ignored) {
            }
        }

        // Record time as next iteration's last iteration
        state.timeLastIteration = System.nanoTime();
    }

    /**
     * Sets this thread's loop target frequency.
     *
     * @param targetFrequency Target frequency in iterations per second
     */
    public static void target(double targetFrequency) {
        // Get current thread
        Thread currentThread = Thread.currentThread();

        // Register current thread if not already
        if (!stateMap.containsKey(currentThread)) {
            stateMap.put(currentThread, new LoopState());
        }

        // Get loop state for current thread
        LoopState state = stateMap.get(currentThread);

        // Set target frequency
        state.targetDuration = (long) (1000000000d / targetFrequency);
    }

    /**
     * Get elapsed time since last call to {@link #sync()} in seconds.
     * <p>
     * This is a convenience method for the following: {@code LoopSync.deltaNanos() / 1000000000d }
     *
     * @return Elapsed time in seconds
     */
    public static double delta() {
        return deltaNanos() / 1000000000d;
    }

    /**
     * Get elapsed time since last call to {@link #sync()} in nanoseconds.
     *
     * @return Elapsed time in nanoseconds
     */
    public static long deltaNanos() {
        // Get current thread
        Thread currentThread = Thread.currentThread();

        // Register current thread if not already
        if (!stateMap.containsKey(currentThread)) {
            stateMap.put(currentThread, new LoopState());
        }

        // Get loop state for current thread
        LoopState state = stateMap.get(currentThread);

        // Return difference between now and last iteration time
        return System.nanoTime() - state.timeLastIteration;
    }

    /**
     * Get remaining time until target iteration duration is exceeded in seconds.
     *
     * @return Remaining time in seconds
     */
    public static double peek() {
        return peekNanos() / 1000000000d;
    }

    /**
     * Get remaining time until target iteration duration is exceeded in nanoseconds.
     *
     * @return Remaining time in nanoseconds
     */
    public static long peekNanos() {
        // Get current thread
        Thread currentThread = Thread.currentThread();

        // Register current thread if not already
        if (!stateMap.containsKey(currentThread)) {
            stateMap.put(currentThread, new LoopState());
        }

        // Get loop state for current thread
        LoopState state = stateMap.get(currentThread);

        // Return remaining time
        return state.targetDuration - (System.nanoTime() - state.timeLastIteration);
    }

    private static class LoopState {

        private long targetDuration;
        private long timeLastIteration;
        private long totalIterations;

    }

}
