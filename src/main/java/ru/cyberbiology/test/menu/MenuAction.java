package ru.cyberbiology.test.menu;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.IWindow;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class MenuAction {
    protected World world;
    protected final IWindow window;
    protected JMenuItem actionItem;

    public MenuAction(IWindow window) {
        this.window = window;
    }

    public abstract String getCaption();
    public abstract ActionListener getListener();

    public void addTo(JMenu parent) {
        addTo(parent, false);
    }

    public void addTo(JMenu parent, boolean addSeparator) {
        this.world = window.getWorld();
        actionItem = new JMenuItem(getCaption());
        parent.add(actionItem);
        actionItem.addActionListener(getListener());
        if (addSeparator) {
            parent.addSeparator();
        }
    }

    public void setText(String text) {
        actionItem.setText(text);
    }

    protected JPanel getPaintPanel() {
        return window.getPaintPanel();
    }
}
