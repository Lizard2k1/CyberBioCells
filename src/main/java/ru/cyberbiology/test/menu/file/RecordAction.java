package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.*;

public class RecordAction extends MenuAction {
    public RecordAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return BEGIN_TEXT;
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
            if (!world.isRecording()) {
                world.startRecording();
                setText(SAVE_TEXT);
            } else {
                setText(getCaption());

                world.stopRecording();
                if (world.haveRecord()) {
                    //saveItem.setEnabled(true);
                    //deleteItem.setEnabled(true);
                    //recordItem.setEnabled(false);
                }
            }
        };
    }
}
