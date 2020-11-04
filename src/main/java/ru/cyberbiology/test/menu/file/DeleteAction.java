package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.DELETE_TEXT;

public class DeleteAction extends MenuAction {
    public DeleteAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return DELETE_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
//            world.deleteRecord();

//            saveItem.setEnabled(false);
//            deleteItem.setEnabled(false);
//            recordItem.setEnabled(true);
        };
    }
}
