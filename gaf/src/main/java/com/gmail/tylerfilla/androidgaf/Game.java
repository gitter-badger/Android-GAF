package com.gmail.tylerfilla.androidgaf;

import com.gmail.tylerfilla.androidgaf.util.LoopSync;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Game {

    private LoopThread loopThread;
    private Set<LoopHook> loopHookSet;

    private List<GamePiece> gamePieceList;

    private Game() {
        loopThread = new LoopThread();
        loopHookSet = new HashSet<>();

        gamePieceList = new ArrayList<>();
    }

    public LoopThread getLoopThread() {
        return loopThread;
    }

    public Set<LoopHook> getLoopHookSet() {
        return loopHookSet;
    }

    public void addLoopHook(LoopHook loopHook) {
        loopHookSet.add(loopHook);
    }

    public boolean removeLoopHoop(LoopHook loopHook) {
        return loopHookSet.remove(loopHook);
    }

    public List<GamePiece> getGamePieceList() {
        return gamePieceList;
    }

    public void addGamePiece(GamePiece gamePiece) {
        gamePieceList.add(gamePiece);
    }

    public boolean removeGamePiece(GamePiece gamePiece) {
        return gamePieceList.remove(gamePiece);
    }

    public void start() {
        loopThread.start();
    }

    public void stop() {
        loopThread.doStop();
    }

    public interface LoopHook {

        void hook();

    }

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

                // Loop sync
                LoopSync.sync();
            }
        }

    }

}
