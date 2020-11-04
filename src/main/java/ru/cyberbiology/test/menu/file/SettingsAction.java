package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import javax.swing.*;
import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.SETTINGS_TEXT;

public class SettingsAction extends MenuAction {
    public SettingsAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return SETTINGS_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> showPropertyDialog();
    }

    public void showPropertyDialog()
    {
        JTextField fileDirectoryName = new JTextField();
        fileDirectoryName.setText(window.getProperties().getFileDirectory());
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Директория для хранения файлов записи"),
                fileDirectoryName,
            };
        int result = JOptionPane.showConfirmDialog((JFrame) window, inputs, SETTINGS_TEXT,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null);
        if (result == JOptionPane.OK_OPTION) {
            window.getProperties().setFileDirectory(fileDirectoryName.getText());
        }
    }
}
