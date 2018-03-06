

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	
	public JFrame getFrame() {
		return frame;
	}

	private Canvas canvas;

	private String title;
	private int width, height;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}

	public void createDisplay(MakeMaze listener) {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));

		//Container pane = frame.getContentPane();
		//pane.add(canvas, BorderLayout.CENTER);
		frame.add(canvas);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.pack();
		canvas.createBufferStrategy(2);
		
		final MenuBar menuBar = new MenuBar();

		final Menu mazeMenu = new Menu("Maze");
		final Menu pathMenu = new Menu("Path");

		MenuItem resetMenuItem = new MenuItem("Reset");
		resetMenuItem.setActionCommand("Reset");
		mazeMenu.add(resetMenuItem);
		resetMenuItem.addActionListener(listener);	
		
		MenuItem newMenuItem = new MenuItem("New");
		newMenuItem.setActionCommand("New");
		mazeMenu.add(newMenuItem);
		newMenuItem.addActionListener(listener);
		
		MenuItem sizeMenuItem = new MenuItem("Size");
		sizeMenuItem.setActionCommand("Size");
		mazeMenu.add(sizeMenuItem);
		sizeMenuItem.addActionListener(listener);

		MenuItem pathMenuItem = new MenuItem("Path");
		pathMenuItem.setActionCommand("Path");
		pathMenu.add(pathMenuItem);
		pathMenuItem.addActionListener(listener);

		MenuItem lookMenuItem = new MenuItem("Look");
		lookMenuItem.setActionCommand("Look");
		pathMenu.add(lookMenuItem);		
		
		newMenuItem.addActionListener(listener);
		
		menuBar.add(mazeMenu);
		menuBar.add(pathMenu);


		frame.setMenuBar(menuBar);

	}

	public Canvas getCanvas() {
		return canvas;
	}




}
