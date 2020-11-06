package ru.cyberbiology.test.util;

public class Worker extends Thread {
    private boolean started = false;
    private boolean paused = false;
    private Runnable runner;
    private Runnable stopper;

    public Worker(Runnable runner, Runnable stopper) {
        this.runner = runner;
        this.stopper = stopper;
    }

    public void run() {
        started = true;
        while (started) {
            if (!paused) {
                runner.run();
            }
        }
        stopper.run();
        paused = false;// Закончили работу
        started = false;// Закончили работу
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    public void begin() {
        started = true;
    }

    public void end() {
        started = false;
    }

}
