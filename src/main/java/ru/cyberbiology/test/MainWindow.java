package ru.cyberbiology.test;


import ru.cyberbiology.test.gui.AppWindow;
import ru.cyberbiology.test.menu.file.*;
import ru.cyberbiology.test.menu.view.ViewAction;
import ru.cyberbiology.test.prototype.view.IView;
import ru.cyberbiology.test.view.ViewBasic;

import javax.swing.*;
import java.awt.*;

import static ru.cyberbiology.test.util.Consts.APP_CAPTION;
import static ru.cyberbiology.test.util.Consts.VIEW_TEXT;

public class MainWindow extends AppWindow {
    public static MainWindow window;
   // JPanel paintPanel = new JPanel(new FlowLayout());

    public JLabel generationLabel = new JLabel(" Generation: 0 ");
    public JLabel populationLabel = new JLabel(" Population: 0 ");
    public JLabel organicLabel = new JLabel(" Organic: 0 ");
    
    public JLabel recorderBufferLabel = new JLabel("");
    public JLabel memoryLabel = new JLabel("");
    
    public JLabel frameSavedCounterLabel = new JLabel("");
    public JLabel frameSkipSizeLabel = new JLabel("");

    public MainWindow() {
    	window	= this;

        setTitle(APP_CAPTION);
        setSize(new Dimension(1800, 900));
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize();
        if (fSize.height > sSize.height) { fSize.height = sSize.height; }
        if (fSize.width  > sSize.width)  { fSize.width = sSize.width; }
        //setLocation((sSize.width - fSize.width)/2, (sSize.height - fSize.height)/2);
        setSize(new Dimension(sSize.width, sSize.height));

        setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);

        Container container = getContentPane();

        container.setLayout(new BorderLayout());// у этого лейаута приятная особенность - центральная часть растягивается автоматически
        container.add(paintPanel, BorderLayout.CENTER);// добавляем нашу карту в центр
        //container.add(paintPanel);

        initStatusPanel(container);

        initMainMenu();

        view = new ViewBasic();
        this.pack();
        this.setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);

        // don't show dialog after start app
//        String tmp = this.getFileDirectory();
//        if(tmp==null || tmp.length()==0) {
//            showPropertyDialog();
//        }
    }

    private void initMainMenu() {
        JMenuBar menuBar = new JMenuBar();
        initFileMenu(menuBar);
        initViewMenu(menuBar);
        this.setJMenuBar(menuBar);
    }

    private void initFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        new RunAction(this).addTo(fileMenu);
        new SnapShotAction(this).addTo(fileMenu);
        new RecordAction(this).addTo(fileMenu);
        new SaveAction(this).addTo(fileMenu);
        new DeleteAction(this).addTo(fileMenu);
        new PlayerAction(this).addTo(fileMenu, true);
        new SettingsAction(this).addTo(fileMenu, true);
        new ExitAction(this).addTo(fileMenu);

        menuBar.add(fileMenu);
    }

    private void initViewMenu(JMenuBar menuBar) {
        JMenu viewMenu = new JMenu(VIEW_TEXT);
        new ViewAction(this).addTo(viewMenu);
        menuBar.add(viewMenu);
    }

    private void initStatusPanel(Container container) {
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        container.add(statusPanel, BorderLayout.SOUTH);

        generationLabel.setPreferredSize(new Dimension(140, 18));
        generationLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(generationLabel);

        populationLabel.setPreferredSize(new Dimension(140, 18));
        populationLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(populationLabel);

        organicLabel.setPreferredSize(new Dimension(140, 18));
        organicLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(organicLabel);

        memoryLabel.setPreferredSize(new Dimension(140, 18));
        memoryLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(memoryLabel);

        recorderBufferLabel.setPreferredSize(new Dimension(140, 18));
        recorderBufferLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(recorderBufferLabel);

        frameSavedCounterLabel.setPreferredSize(new Dimension(140, 18));
        frameSavedCounterLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(frameSavedCounterLabel);

        frameSkipSizeLabel.setPreferredSize(new Dimension(140, 18));
        frameSkipSizeLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(frameSkipSizeLabel);
    }

	@Override
	public void setView(IView view)
	{
		this.view	= view;
	}
    public void paint() {
    	buffer = this.view.paint(this.world, this.paintPanel);
        generationLabel.setText(" Generation: " + world.generation);
        populationLabel.setText(" Population: " + world.population);
        organicLabel.setText(" Organic: " + world.organic);
        recorderBufferLabel.setText(" Buffer: " + world.recorder.getBufferSize());
        
        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        memoryLabel.setText(" Memory MB: " + memory / (1024L * 1024L));
        
        frameSavedCounterLabel.setText(" Saved frames: " + world.world.recorder.getFrameSavedCounter());
        frameSkipSizeLabel.setText(" Skip frames: " + world.world.recorder.getFrameSkipSize());
        

        paintPanel.repaint();
    	/*
    	int w = canvas.getWidth();
    	int h = canvas.getHeight();
    	//Создаем временный буфер для рисования
    	Image buf = canvas.createImage(w, h);
    	//подеменяем графику на временный буфер
    	Graphics g = buf.getGraphics();
    	
        g.drawRect(0, 0, width * 4 + 1, height * 4 + 1);

        population = 0;
        organic = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[x][y] == null) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * 4,y * 4, 4, 4);
                } else if ((matrix[x][y].alive == 1) || (matrix[x][y].alive == 2)) {
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(x * 4, y * 4, 4, 4);
                    organic = organic + 1;
                } else if (matrix[x][y].alive == 3) {
                    g.setColor(Color.BLACK);
                    g.drawRect(x * 4, y * 4, 4, 4);

//                    g.setColor(new Color(matrix[x][y].c_red, matrix[x][y].c_green, matrix[x][y].c_blue));
                    int green = (int) (matrix[x][y].c_green - ((matrix[x][y].c_green * matrix[x][y].health) / 2000));
                    if (green < 0) green = 0;
                    if (green > 255) green = 255;
                    int blue = (int) (matrix[x][y].c_blue * 0.8 - ((matrix[x][y].c_blue * matrix[x][y].mineral) / 2000));
                    g.setColor(new Color(matrix[x][y].c_red, green, blue));
//                    g.setColor(new Color(matrix[x][y].c_red, matrix[x][y].c_green, matrix[x][y].c_blue));
                    g.fillRect(x * 4 + 1, y * 4 + 1, 3, 3);
                    population = population + 1;
                }
            }
        }
        
        generationLabel.setText(" Generation: " + String.valueOf(generation));
        populationLabel.setText(" Population: " + String.valueOf(population));
        organicLabel.setText(" Organic: " + String.valueOf(organic));
        
        buffer = buf;
        canvas.repaint();
        */
    }




/*    public void print() {
        //Выводим в консоль текущее состояние симуляции
        String out;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[x][y] == null) {
                    out = " . ";
                } else if (!matrix[x][y].alive) {
                    out = " x ";
                } else {
                    out = "[" + matrix[x][y].health + "]";
                }
                System.out.print(out);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
*/

    public static void main(String[] args) {
    	MainWindow.window	= new MainWindow();
    }
}
