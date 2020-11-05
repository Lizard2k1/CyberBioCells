package ru.cyberbiology.test.bot;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.IBot;

import java.awt.*;

import static ru.cyberbiology.test.util.Consts.BOTH;
import static ru.cyberbiology.test.util.Consts.BOTW;

public class SBot extends Bot {
    private int delay = 150;
    private int posX;
    private int posY;
    private int targX;
    private int targY;
    private Runnable onEnd;
    private int step;
    private int maxStep = 0;
    private Graphics g;
    public SBot(World world, int x, int y) {
        this(world);
        setXY(x, y);
    }

    public SBot(World world) {
        super(world);
    }

    @Override
    public void step() {
        if (step == 0) {
            posX = x * BOTW;
            posY = y * BOTH;
        } else if (step > maxStep - delay && step < maxStep) {
            if (step > 1 && onEnd != null) {
                world.putToQueue(onEnd);
                onEnd = null;
            }
        } else if (step >= maxStep) {
            posX = x * BOTW;
            posY = y * BOTH;
            step = 0;
            maxStep = 0;
        } else if (maxStep > 0 && step > delay && step < maxStep - delay) {
            posX = x * BOTW + (step - delay) * (targX * BOTW - x * BOTW) / (maxStep - delay * 2);
            posY = y * BOTH + (step - delay) * (targY * BOTH - y * BOTH) / (maxStep - delay * 2);
        }
        step++;
    }

    @Override
    public void prep(int tx, int ty, int steps, Runnable onEnd) {
        targX = tx;
        targY = ty;
        step = 0;
        delay = steps / 2;
        maxStep = steps + delay * 2;
        this.onEnd = onEnd;
    }

    @Override
    public void paint(Graphics g) {
        if (g != null) {
            paint(g, posX, posY);
            this.g = g;
        }
    }

    @Override
    public void swap(IBot iBot) {
        if (iBot == null || this.equals(iBot)) {
            return;
        }
        resetPos(this);
        resetPos((SBot) iBot);
    }

    private void resetPos(SBot bot) {
        world.swapMatrix[bot.targX][bot.targY] = bot;
        bot.hasPos = false;
        bot.setXY(bot.targX, bot.targY);
        bot.posX = bot.x * BOTW;
        bot.posY = bot.y * BOTH;
    }

    @SuppressWarnings("unused")
    public void swapRbg(IBot iBot) {
        Bot bot = (Bot) iBot;
        int tRed = this.c_red;
        int tGreen = this.c_green;
        int tBlue = this.c_blue;
        this.c_red = bot.c_red;
        this.c_green = bot.c_green;
        this.c_blue = bot.c_blue;
        bot.c_red = tRed;
        bot.c_green = tGreen;
        bot.c_blue = tBlue;
    }
}
