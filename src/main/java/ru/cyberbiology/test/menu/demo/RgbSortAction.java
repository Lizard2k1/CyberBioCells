package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.prototype.IWindow;

import java.util.ArrayList;
import java.util.List;

public class RgbSortAction extends SortAction {
    public RgbSortAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return "RGB Sort Demo";
    }

    @Override
    void sort() {
        var wd = world.matrix.length;
        var ht = world.matrix[0].length;
        List<Bot> list = new ArrayList<>(wd * ht);
        // collect cells
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                list.add((Bot) world.matrix[i][j]);
            }
        }
        // compare and sort cells
        list.sort((b1, b2) -> {
            if (b1.c_green == b2.c_green) {
                if (b1.c_blue == b2.c_blue) {
                    if (b1.c_red == b2.c_red) {
                        return 0;
                    }
                    return b1.c_red > b2.c_red ? 1 : - 1;
                }
                return b1.c_blue > b2.c_blue ? 1 : - 1;
            }
            return b1.c_green > b2.c_green ? 1 : - 1;
        });
        // prep to move cells
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                var cell = ((Bot) world.matrix[i][j]);
                var ind = list.indexOf(cell);
                int targX = ind - (ind / wd) * wd;
                int targY = ind % ht;
                cell.prep(targX, targY, DEFAULT_STEPS, () -> {
                    cell.swap(world.matrix[targX][targY]);
                });
            }
        }
    }
}
