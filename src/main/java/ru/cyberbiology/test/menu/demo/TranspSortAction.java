package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.bot.SBot;
import ru.cyberbiology.test.prototype.IWindow;
import ru.cyberbiology.test.util.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.cyberbiology.test.util.MathUtils.*;

public class TranspSortAction extends SortAction {
    private static final boolean useRandom = true;
    private int startRnd = 0;
    private int stopRnd = maxRnd - 1;
    private boolean paused = true;
    private Worker awaiter;
    public TranspSortAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return "Transposing Demo";
    }

    private final Random r = new Random();
    private int prev = startRnd;
    @Override
    void sort() {
        var wd = world.matrix.length;
        var ht = world.matrix[0].length;
        var max = Math.min(wd, ht);
        AtomicInteger counter = new AtomicInteger(0);
        if (!useRandom && prev >= maxRnd) {
            prev = startRnd;
        }
        setupMathFnc(getFnc(r.nextInt(maxFnc * diffRnd)),
                () -> getFnc(r.nextInt((maxFnc + addFnc) * diffRnd)));
        int rnd = maxRnd;
        while (rnd > stopRnd) rnd = genRndValue();
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                var cell = ((SBot) world.matrix[i][j]);
                sortByKind(cell, counter, rnd, i, j, wd, ht, max);
            }
        }
//        setOnAction();
        setOnStop();
    }

    private void doAction() {
        super.getListener().actionPerformed(new ActionEvent(actionItem, 0, "1"));
    }

    @Override
    public ActionListener getListener() {
        return evt -> {
            paused = !paused;
            doAction();
        };
    }

    private void setOnStop() {
        onStop = () -> {
            if (paused) {
                return;
            }
            doAction();
        };
    }

    @SuppressWarnings("unused")
    private void setOnAction() {
        onAction = () -> {
            if (awaiter != null) {
                world.stop(awaiter, () -> {});
            }
            awaiter = world.start(() -> {
                if (!world.started()) {
                    // world.stop(awaiter);
                }
                //runner
            }, () -> {
                //stopper
            }, () -> {
                //onStart
            });
        };
    }

    private int genRndValue() {
        return useRandom ? r.nextInt(maxRnd - startRnd) + startRnd : prev++;
    }

    private void sortByKind(SBot bot, AtomicInteger counter, int rnd,
                            int i, int j, int wd, int ht, int max) {
        if (intersect(rnd, i, j, wd, ht, max)) {
            Point xy = convertXY(rnd, i, j, max);
            cellPrep(max, max, counter, bot, xy.x, xy.y);
        }
    }

    @Override
    protected KeyStroke menuAccelerator() {
        return KeyStroke.getKeyStroke(' ');
    }
}
