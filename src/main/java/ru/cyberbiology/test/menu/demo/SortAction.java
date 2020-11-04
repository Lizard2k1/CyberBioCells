package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.bot.SBot;
import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

public class SortAction extends MenuAction {
    public SortAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return "Sorting Demo";
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            if (world == null) {
                createWorld();
            } else {
                world.stop();
            }
            fillMatrix();
            window.paint();
        };
    }

    private void fillMatrix() {
        var wd = world.matrix.length;
        var ht = world.matrix[0].length;
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                if (checkBot(world.matrix[i][j])) {
                    world.matrix[i][j] = new SBot(world);
                }
                var cell = ((Bot) world.matrix[i][j]);

                cell.c_blue = (int) Math.round(Math.random() * 255);
                cell.c_green = (int) Math.round(Math.random() * 255);
                cell.c_red = (int) Math.round(Math.random() * 255);
            }
        }
    }

    private boolean checkBot(IBot bot) {
        return !(bot instanceof SBot);
    }
}
