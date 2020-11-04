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
                createWorld();
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
