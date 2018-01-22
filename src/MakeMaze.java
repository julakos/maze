import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MakeMaze implements Runnable, ActionListener {
	private Maze m = new Maze();
	private Display display;
	public int a = 10, b = 10;

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
			m.generateMaze(a, b);
			m.draw(display.getCanvas());
		} else if (e.getActionCommand() == "Path") {
			m.pathFinder();
			m.draw(display.getCanvas());
		} else if(e.getActionCommand() == "Reset") {
			m.clearPath();
			m.draw(display.getCanvas());
		}
	}
}
