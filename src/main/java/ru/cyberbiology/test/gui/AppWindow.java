package ru.cyberbiology.test.gui;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.IWindow;
import ru.cyberbiology.test.prototype.view.IView;
import ru.cyberbiology.test.util.ProjectProperties;
import ru.cyberbiology.test.view.ViewBasic;

import javax.swing.*;
import java.awt.*;

public abstract class AppWindow extends JFrame implements IWindow {
    protected World world;
    /** буфер для отрисовки ботов */
    protected Image buffer	= null;

    /** актуальный отрисовщик*/
    protected IView view;
    protected JPanel paintPanel = new JPanel()
    {
        public void paint(Graphics g)
        {
            g.drawImage(buffer, 0, 0, null);
        }
    };
    protected ProjectProperties properties;

    public AppWindow() {
        properties	= new ProjectProperties("properties.xml");
        paintPanel.addMouseListener(new CustomListener(this));
        view = new ViewBasic();
    }

    @Override
    public ProjectProperties getProperties()
    {
        return this.properties;
    }

    @Override
    public JPanel getPaintPanel() {
        return paintPanel;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    public String getFileDirectory()
    {
        return this.properties.getFileDirectory();
    }

    public Image getBuffer() {
        return buffer;
    }
}
