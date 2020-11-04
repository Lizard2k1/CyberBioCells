package ru.cyberbiology.test.gui;

import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.gene.IBotGeneController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static ru.cyberbiology.test.util.Consts.BOTH;
import static ru.cyberbiology.test.util.Consts.BOTW;

public class CustomListener implements MouseListener {
    private final AppWindow appWindow;

    public CustomListener(AppWindow appWindow) {
        this.appWindow = appWindow;
    }

    public void mouseClicked(MouseEvent e) {
        var world = appWindow.getWorld();
        if (world.started()) {
            return;//Если идет обсчет не суетимся, выводить ничего не надо.
        }

        Point p = e.getPoint();
        int x = (int) p.getX();
        int y = (int) p.getY();
        int botX = (x - 2) / BOTW;
        int botY = (y - 2) / BOTH;
        Bot bot = (Bot) world.getBot(botX, botY);
        if (bot != null) {
            {
                Graphics g = appWindow.getBuffer().getGraphics();
                g.setColor(Color.MAGENTA);
                g.fillRect(botX * BOTW, botY * BOTH, BOTW, BOTH);
//                    g.setColor(Color.BLACK);
                //                  g.drawRect(botX * 4, botY * 4, 4, 4);
                appWindow.getPaintPanel().repaint();
            }
            StringBuilder buf = new StringBuilder();
            buf.append("<html>");
            buf.append("<p>Многоклеточный: ");
            switch (bot.isMulti()) {
                case 0:// - нет,
                    buf.append("нет</p>");
                    break;
                case 1:// - есть MPREV,
                    buf.append("есть MPREV</p>");
                    break;
                case 2:// - есть MNEXT,
                    buf.append("есть MNEXT</p>");
                    break;
                case 3:// есть MPREV и MNEXT
                    buf.append("есть MPREV и MNEXT</p>");
                    break;
            }
            buf.append("<p>c_blue=").append(bot.c_blue);
            buf.append("<p>c_green=").append(bot.c_green);
            buf.append("<p>c_red=").append(bot.c_red);
            buf.append("<p>direction=").append(bot.direction);
            buf.append("<p>health=").append(bot.health);
            buf.append("<p>mineral=").append(bot.mineral);


            //buf.append("");

            IBotGeneController cont;
            for (int i = 0; i < Bot.MIND_SIZE; i++) {//15
                int command = bot.mind[i];  // текущая команда

                // Получаем обработчика команды
                cont = Bot.geneController[command];
                if (cont != null)// если обработчик такой команды назначен
                {
                    buf.append("<p>");
                    buf.append(i);
                    buf.append("&nbsp;");
                    buf.append(cont.getDescription(bot, i));
                    buf.append("</p>");
                }
            }

            buf.append("</html>");
            JComponent component = (JComponent) e.getSource();
            //System.out.println(bot);
            appWindow.getPaintPanel().setToolTipText(buf.toString());
            MouseEvent phantom = new MouseEvent(component, MouseEvent.MOUSE_MOVED,
                    System.currentTimeMillis() - 2000, 0, x, y, 0, false);
            ToolTipManager.sharedInstance().mouseMoved(phantom);
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
