package ru.cyberbiology.test.menu.view;

import ru.cyberbiology.test.ViewMenuActionListener;
import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;
import ru.cyberbiology.test.prototype.view.IView;
import ru.cyberbiology.test.view.ViewBasic;
import ru.cyberbiology.test.view.ViewMultiCell;

import javax.swing.*;
import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.VIEW_TEXT;

public class ViewAction extends MenuAction {
    /**
     * Перечень возможных отрисовщиков
     */
    private final IView[] views = new IView[]{
            new ViewBasic(),
            new ViewMultiCell()
    };

    public ViewAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return VIEW_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return (e) -> { };
    }

    @Override
    public void addTo(JMenu parent) {
        for (IView view : views) {
            JMenuItem item = new JMenuItem(view.getName());
            parent.add(item);
            item.addActionListener(new ViewMenuActionListener(window, view));
        }
    }
}
