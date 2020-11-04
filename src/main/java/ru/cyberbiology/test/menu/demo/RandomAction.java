package ru.cyberbiology.test.menu.demo;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

public class RandomAction extends MenuAction {
    public RandomAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return "Random Demo";
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
            DemoUtils.fillMatrix(world, true);
        };
    }
}
