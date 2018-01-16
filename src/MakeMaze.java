public class MakeMaze implements Runnable {
	private Maze m = new Maze();
	private Display display;
	
	public static void main(String[] args) {
		MakeMaze mm = new MakeMaze();
		Thread thread = new Thread(mm);
		thread.start();
	}
	
	public void run() {
		display = new Display("oooooooo, bludiskooo!", 500, 500);
		display.createDisplay();
		m.generateMaze(10, 10);
		m.pathFinder();
		while (true) {
			m.draw(display.getCanvas());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
