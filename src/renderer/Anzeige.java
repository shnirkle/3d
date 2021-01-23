package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.punkt.Punkt;
import renderer.shapes.Polygon3D;
import renderer.shapes.Würfel;

public class Anzeige extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private JFrame frame;
	private static String titel = "Sterne";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private static boolean running = false;

	private Würfel w;

	public static void main(String[] args) {
		Anzeige display = new Anzeige();
		display.frame.setTitle(titel);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setResizable(false);
		display.frame.setVisible(true);
		display.start();

	}

	public Anzeige() {
		this.frame = new JFrame();

		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Anzeige");
		this.thread.start();
	}

	public void stop() {
		running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60;
		double delta = 0;
		int frames = 0;

		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) { // Code stammt nicht von mir es ist um Fehler zu verhindern

				update();
				delta--;
				render();
				frames++;
			}
			

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(titel + " | " + frames + "fps");
				frames = 0;
			}
		}
		stop();

	}

	private void init() {
		int s = 100;
		Punkt p1 = new Punkt(s / 2, -s / 2, -s / 2);
		Punkt p2 = new Punkt(s / 2, s / 2, -s / 2);
		Punkt p3 = new Punkt(s / 2, s / 2, s / 2);
		Punkt p4 = new Punkt(s / 2, -s / 2, s / 2);
		Punkt p5 = new Punkt(-s / 2, -s / 2, -s / 2);
		Punkt p6 = new Punkt(-s / 2, s / 2, -s / 2);
		Punkt p7 = new Punkt(-s / 2, s / 2, s / 2);
		Punkt p8 = new Punkt(-s / 2, -s / 2, s / 2);
		this.w = new Würfel(
				
				new Polygon3D(Color.RED, p1, p2, p3, p4),
				new Polygon3D(Color.BLUE,p5, p6, p7, p8),
				new Polygon3D(Color.WHITE,p1, p2, p5, p6),
				new Polygon3D(Color.GREEN,p1, p5, p8, p4),
				new Polygon3D(Color.YELLOW,p2, p6, p7, p3),
				new Polygon3D(Color.ORANGE,p4, p3, p7, p8));

	}

	private void update() {

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH * 2, HEIGHT * 2);

		w.render(g);
		g.dispose();
		bs.show();
	}

}
