package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.bot.SBot;
import ru.cyberbiology.test.prototype.IBot;

public class DemoUtils {
    public static void fillMatrix(World world, boolean force) {
        var wd = world.matrix.length;
        var ht = world.matrix[0].length;
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                if (checkSBot(world.matrix[i][j]) || force) {
                    cleanPrevCell(world.matrix[i], j);
                    world.matrix[i][j] = new SBot(world);
                    var cell = ((Bot) world.matrix[i][j]);
                    cell.c_blue = (int) Math.round(Math.random() * 255);
                    cell.c_green = (int) Math.round(Math.random() * 255);
                    cell.c_red = (int) Math.round(Math.random() * 255);
                    cell.setXY(i, j);
                    cell.step();
                }
            }
        }
        world.paint();
    }

    private static void cleanPrevCell(IBot[] matrix, int j) {
        var dead = ((Bot) matrix[j]);
        if (dead != null) {
            dead.alive = 0;
            matrix[j] = null;
        }
    }

    public static boolean checkSBot(IBot bot) {
        return !(bot instanceof SBot);
    }
}
