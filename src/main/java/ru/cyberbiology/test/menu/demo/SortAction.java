package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.bot.SBot;
import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SortAction extends MenuAction {
    public static final int DEFAULT_STEPS = 500;
    SortAction(IWindow window) {
        super(window);
    }

    protected Runnable onAction;
    protected Runnable onStop;

    @Override
    public ActionListener getListener() {
        return e -> {
            world = window.getWorld();
            if (world == null) {
                createWorld();
            } else {
                world.stop();
            }
            DemoUtils.fillMatrix(world, false);
            sort();
            window.paint();
            world.start();
            if (onAction != null) {
                onAction.run();
            }
        };
    }

    abstract void sort();


    protected void cellPrep(int wd, int ht, AtomicInteger counter, SBot cell, int targX, int targY) {
        cell.prep(targX, targY, DEFAULT_STEPS, () -> {
            int count = counter.incrementAndGet();
            try {
                cell.swap(world.matrix[targX][targY]);
            } catch (ArrayIndexOutOfBoundsException obe) {
                // do nothing
            }
            if (count >= wd * ht) {
                world.stop();
                world.matrix = world.swapMatrix;
                window.paint();
                if (onStop != null) {
                    onStop.run();
                }
            }
        });
    }
}
