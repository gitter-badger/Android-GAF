package com.gmail.tylerfilla.androidgaf;

import com.gmail.tylerfilla.androidgaf.util.LoopSync;

import java.util.HashSet;
import java.util.Set;

/**
 * The core of Android GAF. This class represents a game, provides a timed game loop, and offers
 * basic lifecycle control methods for building your own game. This system contains no concept of
 * graphics or physics; as such, Android GAF can be leveraged to build both simple 2D board games
 * and more complex 3D games.
 */
public abstract class Game {

    /**
     * The loop thread from which all hook and iteration control originates.
     */
    protected LoopThread loopThread;

    /**
     * The set of loop hooks executed prior to the primary iteration.
     */
    protected Set<LoopHook> loopHookSet;

    /**
     * The current stage containing game objects.
     */
    protected Stage stage;

    protected Game() {
        loopThread = new LoopThread();
        loopHookSet = new HashSet<>();
        stage = new Stage();
    }

    /**
     * Get the loop thread.
     *
     * @return The loop thread instance
     */
    public LoopThread getLoopThread() {
        return loopThread;
    }

    /**
     * Get the set of loop hooks.
     *
     * @return The loop hook set
     */
    public Set<LoopHook> getLoopHookSet() {
        return loopHookSet;
    }

    /**
     * Add a loop hook. Loop hooks are implementations of {@link LoopHook} used to externally hook
     * into the game loop. Hooks receive control in a nonspecific order, but all hooks are run
     * before the primary game loop iteration. Hooks are run on the game loop thread.
     *
     * @param loopHook The loop hook to add
     */
    public void addLoopHook(LoopHook loopHook) {
        loopHookSet.add(loopHook);
    }

    /**
     * Remove a loop hook.
     *
     * @param loopHook The loop hook to remove
     * @return Whether or not the removal was successful
     */
    public boolean removeLoopHoop(LoopHook loopHook) {
        return loopHookSet.remove(loopHook);
    }

    /**
     * Get the current stage. By default, a newly created game will contain an empty stage.
     *
     * @return The current stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Set the current stage.
     *
     * @param stage The new stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Start the game loop.
     */
    public void start() {
        loopThread.start();
    }

    /**
     * Stop the game loop. Once stopped, the game loop may not be restarted.
     */
    public void stop() {
        loopThread.doStop();
    }

    /**
     * Method called just before the game loop starts. Do not call this method directly.
     */
    public abstract void onStart();

    /**
     * Method called just after the game loop stops. There is no guarantee that this method will be
     * called in all exit instances. Do not call this method directly.
     */
    public abstract void onStop();

    /**
     * The primary game loop iteration method. Do not call this method directly; it will be called
     * once per game loop iteration (after all hooks are executed) from the game loop thread. Start
     * the game loop with the {@link #start()} method.
     */
    public abstract void loop();

    /**
     * Representation of a modular hook that installs functionality to the game loop to be executed
     * prior to the primary iteration. All execution occurs on the game loop thread, and the order
     * in which hooks are executed is undefined.
     */
    public interface LoopHook {

        /**
         * Execution function of this hook. Implement this to provide functionality.
         */
        void hook();

    }

    /**
     * The game loop thread. This thread provides the execution context for all actions that
     * immediately take place during game loop iterations.
     */
    private class LoopThread extends Thread {

        private static final double DEFAULT_LOOP_TARGET_FREQUENCY = 60d;

        private double loopTargetFrequency;

        private volatile boolean running;

        private LoopThread() {
            loopTargetFrequency = DEFAULT_LOOP_TARGET_FREQUENCY;

            running = false;
        }

        private double getLoopTargetFrequency() {
            return loopTargetFrequency;
        }

        private void setLoopTargetFrequency(double loopTargetFrequency) {
            this.loopTargetFrequency = loopTargetFrequency;
        }

        private void doStop() {
            running = false;

            // Interrupt in case we're waiting
            interrupt();
        }

        @Override
        public void run() {
            // Set running flag
            running = true;

            // Set loop target frequency
            LoopSync.target(loopTargetFrequency);

            // Game loop
            while (running) {
                // Call all loop hooks
                for (LoopHook loopHook : loopHookSet) {
                    loopHook.hook();
                }

                // Perform main loop
                loop();

                // Loop sync
                LoopSync.sync();
            }
        }

    }

}
