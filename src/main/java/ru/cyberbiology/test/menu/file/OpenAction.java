package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.MainWindow;
import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.File;

import static ru.cyberbiology.test.util.Consts.OPEN_TEXT;

public class OpenAction extends MenuAction {
    public OpenAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return OPEN_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.cb.zip","*.*");
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filter);
            if (fc.showSaveDialog((JFrame) window) == JFileChooser.APPROVE_OPTION)
            {
                File f	= fc.getSelectedFile();
                openFile(f);
            }
        };
    }

    @Override
    public void addTo(JMenu parent) {
        super.addTo(parent);
        File dir = new File(MainWindow.window.getFileDirectory());
        File[] files = dir.listFiles((dir1, name) -> name.endsWith("cb.zip"));
        if (files == null || files.length < 1) {
            return;
        }
        for (File file : files) {
            var item = new JMenuItem(file.getName());
            item.addActionListener(e -> openFile(file));
            parent.add(item);
        }
    }

    public void openFile(File f)
    {
        createWorld();
        world.openFile(f);
    }
}
