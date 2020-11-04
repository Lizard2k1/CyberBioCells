package ru.cyberbiology.test.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.view.IView;

import static ru.cyberbiology.test.util.Consts.*;

public class ViewBasic implements IView
{

	public ViewBasic()
	{
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getName()
	{
		// Отображение ...
		return "Базовое";
	}
    public Image paint(World world,JPanel canvas) {
    	int w = canvas.getWidth();
    	int h = canvas.getHeight();
    	//Создаем временный буфер для рисования
    	Image buf = canvas.createImage(w, h);
    	//подеменяем графику на временный буфер
    	Graphics g = buf.getGraphics();
    	
        g.drawRect(0, 0, world.width * BOTW + 1, world.height * BOTH + 1);

        world.population = 0;
        world.organic = 0;
        for (int y = 0; y < world.height; y++) {
            for (int x = 0; x < world.width; x++) {
                if (world.matrix[x][y] == null) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * BOTW,y * BOTH, BOTW, BOTH);
                } else {
                    world.matrix[x][y].setXY(x, y);
                    world.matrix[x][y].paint(g);
                }
            }
        }
        return buf;
    }
}
