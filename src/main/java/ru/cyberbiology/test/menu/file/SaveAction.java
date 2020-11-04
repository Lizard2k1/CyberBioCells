package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;
// import java.io.File;

import static ru.cyberbiology.test.util.Consts.SAVE_TEXT;

public class SaveAction extends MenuAction {
    public SaveAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return SAVE_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            /* FileNameExtensionFilter filter = new FileNameExtensionFilter("*.cb.zip","*.*");
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filter);
            if (fc.showSaveDialog(window) == JFileChooser.APPROVE_OPTION)
            {
                world.saveRecord(fc.getSelectedFile());
                saveItem.setEnabled(false);
                deleteItem.setEnabled(false);
                recordItem.setEnabled(true);
            } */
//                world.saveRecord(new File("/Users/Kolya/Documents/workspace/CyberBiologyTest/save/test.cb.zip"));
//                saveItem.setEnabled(false);
//                deleteItem.setEnabled(false);
//                recordItem.setEnabled(true);
        };
    }
}
