package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.*;

public class SnapShotAction extends MenuAction {
    public SnapShotAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return SNAPSHOT_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            if (world == null) {
                int width = getPaintPanel().getWidth() / BOTW;// Ширина доступной части экрана для рисования карты
                int height = getPaintPanel().getHeight() / BOTH;// Боты 4 пикселя?
                world = new World(window, width, height);
                world.generateAdam();
                window.paint();
            }
            world.stop();
            setText(CONTINUE_TEXT);
            world.makeSnapShot();
        };
    }
}
