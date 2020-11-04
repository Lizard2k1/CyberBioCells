package ru.cyberbiology.test.menu.file;

import ru.cyberbiology.test.menu.MenuAction;
import ru.cyberbiology.test.prototype.IWindow;

import java.awt.event.ActionListener;

import static ru.cyberbiology.test.util.Consts.PLAYER_TEXT;

public class PlayerAction extends MenuAction {
    public PlayerAction(IWindow window) {
        super(window);
    }

    @Override
    public String getCaption() {
        return PLAYER_TEXT;
    }

    @Override
    public ActionListener getListener() {
        return e -> {
            /*
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.cb.zip","*.*");
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filter);
            if (fc.showSaveDialog(window) == JFileChooser.APPROVE_OPTION)
            {
                File f	= fc.getSelectedFile();
                PlayerWindow fw	= new PlayerWindow();
                fw.openFile(f);
            }/*/
//todo            PlayerWindow fw	= new PlayerWindow();
            //fw.openFile(new File(world.getProperties().getFileDirectory()+"test.cb.zip"));
        };
    }
}
