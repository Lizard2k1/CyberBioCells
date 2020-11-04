package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.prototype.IWindow;

public class TranspSortAction extends SortAction {
    public TranspSortAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return "Transposing Demo";
    }

    @Override
    void sort() {
        var wd = world.matrix.length;
        var ht = world.matrix[0].length;
        var max = Math.min(wd, ht);
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                var cell = ((Bot) world.matrix[i][j]);
                if (i < max && j < max) {
                    int ix = Math.min(max, j), jy = Math.min(max, i);
                    cell.prep(ix, jy, DEFAULT_STEPS, () -> {
                        cell.swap(world.matrix[ix][jy]);
                    });
                }
            }
        }
    }
}
