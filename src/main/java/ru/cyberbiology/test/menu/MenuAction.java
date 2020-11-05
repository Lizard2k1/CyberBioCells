package ru.cyberbiology.test.menu;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.IWindow;

import javax.swing.*;
import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.BOTH;
import static ru.cyberbiology.test.util.Consts.BOTW;

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

    protected KeyStroke menuAccelerator() {
        return null;
    }

    public void addTo(JMenu parent, boolean addSeparator) {
        this.world = window.getWorld();
        actionItem = new JMenuItem(getCaption());
        parent.add(actionItem);
        actionItem.addActionListener(getListener());
        if (menuAccelerator() != null) {
            actionItem.setAccelerator(menuAccelerator());
        }
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

    protected void createWorld() {
        int width = getPaintPanel().getWidth() / BOTW;// Ширина доступной части экрана для рисования карты
        int height = getPaintPanel().getHeight() / BOTH;// Боты 4 пикселя?
        world = new World(window, width, height);
        window.setWorld(world);
    }
}
