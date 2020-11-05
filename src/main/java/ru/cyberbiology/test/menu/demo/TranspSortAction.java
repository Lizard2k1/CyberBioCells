package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.bot.SBot;
import ru.cyberbiology.test.prototype.IWindow;
import ru.cyberbiology.test.util.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.cyberbiology.test.util.MathUtils.*;

public class TranspSortAction extends SortAction {
    private static final boolean useRandom = true;
    private final int startRnd = 0;
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
        MathUtils.fnc = getFnc(r.nextInt(4));
        int rnd = useRandom ? r.nextInt(maxRnd) : prev++;
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                var cell = ((SBot) world.matrix[i][j]);
                sortByKind(cell, counter, rnd, i, j, wd, ht, max);
            }
        }
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
