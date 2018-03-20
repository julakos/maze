import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class MakeMaze implements Runnable, ActionListener, KeyListener {
	private Maze m = new Maze();
	private Display display;
	private int a = 10, b = 10;

	public static void main(String[] args) {
		MakeMaze mm = new MakeMaze();
		Thread thread = new Thread(mm);
		thread.start();
	}

	public void run() {
		display = new Display("oooooooo, bludisko!", 500, 500);
		display.createDisplay(this);
		m.generateMaze(a, b);
		// m.pathFinder();
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					keyReleased(e);
				}
				return false;
			}
		});
		
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
			m.goToStart();
			m.clearPath();
			m.generateMaze(a, b);
		} else if (e.getActionCommand() == "Path") {
			m.pathFinder();
		} else if (e.getActionCommand() == "Reset") {
			m.goToStart();
			m.clearPath();
		} else if (e.getActionCommand() == "Size") {
			m.goToStart();
			m.clearPath();
			boolean isValueValid;
			String prompt = "Set Hight of maze";
			do {
				isValueValid = true;
				try {
					a = Integer.parseInt(JOptionPane.showInputDialog(prompt, "2-20"));

				} catch (NumberFormatException nfe) {
					isValueValid = false;
				}
				prompt = "Value has to be number between 2 and 20!";
			} while (!isValueValid);
			a = Math.min(20, Math.max(2, a));

			prompt = "Set Width of maze";
			do {
				isValueValid = true;
				try {
					b = Integer.parseInt(JOptionPane.showInputDialog(prompt, "2-20"));
				} catch (NumberFormatException nfe) {
					isValueValid = false;
				}
				prompt = "Value has to be number between 2 and 20!";
			} while (!isValueValid);
			b = Math.min(20, Math.max(2, b));
			m.goToStart();
			m.generateMaze(a, b);
		}
		m.draw(display.getCanvas());

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 39) {// vpravo
			m.move(1, 0);
		} else if (e.getKeyCode() == 37) { // vlavo
			m.move(-1, 0);
		} else if (e.getKeyCode() == 40) {// hore
			m.move(0, 1);
		} else if (e.getKeyCode() == 38) {// dole
			m.move(0, -1);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
