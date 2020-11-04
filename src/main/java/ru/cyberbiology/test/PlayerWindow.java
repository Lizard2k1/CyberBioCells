package ru.cyberbiology.test;


import ru.cyberbiology.test.gui.AppWindow;
import ru.cyberbiology.test.menu.file.OpenAction;
import ru.cyberbiology.test.menu.view.ViewAction;
import ru.cyberbiology.test.prototype.view.IView;
import ru.cyberbiology.test.view.ViewBasic;

import javax.swing.*;
import java.awt.*;

public class PlayerWindow extends AppWindow
{
    //public JLabel generationLabel = new JLabel(" Generation: 0 ");
    public JLabel populationLabel = new JLabel(" Population: 0 ");
    public JLabel organicLabel = new JLabel(" Organic: 0 ");
    
    //public JLabel recorderBufferLabel = new JLabel("");
    public JLabel memoryLabel = new JLabel("");
    
    //public JLabel frameSavedCounterLabel = new JLabel("");
    //public JLabel frameSkipSizeLabel = new JLabel("");
    
    public PlayerWindow()
    {
    	//window	= this;
        setTitle("CyberBiologyTest 1.0.0");
        setSize(new Dimension(1800, 900));
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize();
        if (fSize.height > sSize.height) { fSize.height = sSize.height; }
        if (fSize.width  > sSize.width)  { fSize.width = sSize.width; }
        //setLocation((sSize.width - fSize.width)/2, (sSize.height - fSize.height)/2);
        setSize(new Dimension(sSize.width, sSize.height));
        
        
        setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);

        Container container = getContentPane();

        container.setLayout(new BorderLayout());// у этого лейаута приятная особенность - центральная часть растягивается автоматически
        container.add(paintPanel, BorderLayout.CENTER);// добавляем нашу карту в центр
        //container.add(paintPanel);
        
        
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        container.add(statusPanel, BorderLayout.SOUTH);
        
        //generationLabel.setPreferredSize(new Dimension(140, 18));
       // generationLabel.setBorder(BorderFactory.createLoweredBevelBorder());
       // statusPanel.add(generationLabel);
        
        populationLabel.setPreferredSize(new Dimension(140, 18));
        populationLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(populationLabel);
        
        organicLabel.setPreferredSize(new Dimension(140, 18));
        organicLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(organicLabel);

        memoryLabel.setPreferredSize(new Dimension(140, 18));
        memoryLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(memoryLabel);
        
      //  recorderBufferLabel.setPreferredSize(new Dimension(140, 18));
      //  recorderBufferLabel.setBorder(BorderFactory.createLoweredBevelBorder());
      //  statusPanel.add(recorderBufferLabel);
        
      //  frameSavedCounterLabel.setPreferredSize(new Dimension(140, 18));
      //  frameSavedCounterLabel.setBorder(BorderFactory.createLoweredBevelBorder());
      //  statusPanel.add(frameSavedCounterLabel);
        
      //  frameSkipSizeLabel.setPreferredSize(new Dimension(140, 18));
      //  frameSkipSizeLabel.setBorder(BorderFactory.createLoweredBevelBorder());
      //  statusPanel.add(frameSkipSizeLabel);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenu openMenu = new JMenu("Открыть");
        fileMenu.add(openMenu);

        new OpenAction(this).addTo(openMenu);

        menuBar.add(fileMenu);
        
        JMenu viewMenu = new JMenu("Вид");
        menuBar.add(viewMenu);

        new ViewAction(this).addTo(viewMenu);
        this.setJMenuBar(menuBar);
        
        this.pack();
        this.setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
    }

	@Override
	public void setView(IView view)
	{
		this.view	= view;
		this.paint();
	}

    public void paint() {
    	buffer = this.view.paint(this.world, this.paintPanel);
        //generationLabel.setText(" Generation: " + String.valueOf(world.generation));
        populationLabel.setText(" Population: " + world.population);
        organicLabel.setText(" Organic: " + world.organic);
        //recorderBufferLabel.setText(" Buffer: " + String.valueOf(world.recorder.getBufferSize()));
        
        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        memoryLabel.setText(" Memory MB: " + memory / (1024L * 1024L));
        
        //frameSavedCounterLabel.setText(" Saved frames: " + String.valueOf(world.world.recorder.getFrameSavedCounter()));
        //frameSkipSizeLabel.setText(" Skip frames: " + String.valueOf(world.world.recorder.getFrameSkipSize()));
        

        paintPanel.repaint();
    }
}
