package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.EXIT_TEXT;

public class ExitAction extends MenuAction {
    public ExitAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return EXIT_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            world = window.getWorld();
            // Попытка корректно заверишть запись, если она велась
            // TODO: Не тестировалось!
            if (world != null && world.isRecording()) {
                world.stopRecording();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            System.exit(0);
        };
    }
}
