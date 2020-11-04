package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

public abstract class SortAction extends MenuAction {
    public static final int DEFAULT_STEPS = 1000;
    SortAction(IWindow window) {
        super(window);
    }

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
        };
    }

    abstract void sort();
}
