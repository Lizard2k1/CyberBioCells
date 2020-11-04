package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.*;

public class RunAction extends MenuAction {
    public RunAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return RUN_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            if (world == null) {
                int width = getPaintPanel().getWidth() / BOTW;// Ширина доступной части экрана для рисования карты
                int height = getPaintPanel().getHeight() / BOTH;// Боты 4 пикселя?
                world = new World(window, width, height);
                window.setWorld(world);
                world.generateAdam();
                window.paint();
            }
            if (!world.started()) {
                world.start();//Запускаем его
                setText(PAUSE_TEXT);

            } else {
                world.stop();
                setText(CONTINUE_TEXT);
//todo                snapShotItem.setEnabled(true);
            }
        };
    }
}
