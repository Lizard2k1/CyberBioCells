package ru.cyberbiology.test.prototype;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.view.IView;
import ru.cyberbiology.test.util.ProjectProperties;

import javax.swing.*;

public interface IWindow {
    void paint();
    void setView(IView view);
    ProjectProperties getProperties();
    JPanel getPaintPanel();
    World getWorld();
    void setWorld(World world);
}
