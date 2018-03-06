import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JOptionPane;

public class MakeMaze implements Runnable, ActionListener, KeyListener {
	private Maze m = new Maze();
	private Display display;
	public int a = 10, b = 15;
	boolean isValueValid = true;

	public static void main(String[] args) {
		MakeMaze mm = new MakeMaze();
		Thread thread = new Thread(mm);
		thread.start();
	}

	public void run() {
		display = new Display("oooooooo, bludiskooo!", 500, 500);
		display.createDisplay(this);
		m.generateMaze(a, b);
		// m.pathFinder();
		while (true) {
			m.draw(display.getCanvas());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "New") {
			m.clearPath();
			m.generateMaze(a, b);
			m.draw(display.getCanvas());
		} else if (e.getActionCommand() == "Path") {
			m.pathFinder();
			m.draw(display.getCanvas());
		} else if (e.getActionCommand() == "Reset") {
			System.out.println("idem");
			m.clearPath();
			m.draw(display.getCanvas());
		} else if (e.getActionCommand() == "Size") {
			String prompt = "Set Hight of maze";
			do {
				isValueValid = true;
				try {
					a = Integer.parseInt(JOptionPane.showInputDialog(prompt, "2-20"));
				} catch (NumberFormatException fe) {
					isValueValid = false;
				}
				prompt = "Value have to be number between 2 and 20!";
			} while (!isValueValid);

			prompt = "Set Width of maze";
			do {
				isValueValid = true;
				try {
					b = Integer.parseInt(JOptionPane.showInputDialog(prompt, "2-20"));
				} catch (NumberFormatException fe) {
					isValueValid = false;
				}
				prompt = "Value have to be number between 2 and 20!";
			} while (!isValueValid);
			if (a < 2)
				a = 2;
			if (a > 20)
				a = 20;
			if (b < 2)
				b = 2;
			if (b > 20)
				b = 20;

			m.generateMaze(a, b);
			m.draw(display.getCanvas());

		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("presed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 39) {
			System.out.println("idem");
			m.move(1, 0, display.getCanvas()); // vpravo
		} else if (e.getKeyCode() == 37) {
			m.move(-1, 0, display.getCanvas()); // vlavo
		} else if (e.getKeyCode() == 38) {
			m.move(0, 1, display.getCanvas()); // hore
		} else if (e.getKeyCode() == 40) {
			m.move(0, -1, display.getCanvas()); // dole
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
