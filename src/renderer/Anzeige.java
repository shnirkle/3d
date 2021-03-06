package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.entity.EntityManager;
import renderer.punkt.Matrix;
import renderer.punkt.Vektor;
import renderer.steuerung.Eingabe;

public class Anzeige extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private Color cölör = new Color(0, 0, 0);
	private Thread thread;
	private JFrame frame;
	private static String titel = "Sterne";
	public static int WIDTH = 1080;
	public static int HEIGHT = 1080;
	public Vektor cam = new Vektor(0,0,0);
	private static boolean running = false;

	public EntityManager entityManager;

	private static Eingabe eingabe;


	public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        HEIGHT = (int) screenSize.getHeight();
        WIDTH = (int) screenSize.getWidth();
        Anzeige display = new Anzeige();
        display.frame.setTitle(titel);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display. frame.setResizable(false);
        display.frame.setVisible(true);
        display.start();

    }

	public Anzeige() {
		this.frame = new JFrame();
		this.entityManager = new EntityManager();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);

		this.eingabe = new Eingabe();
		
		this.frame.addKeyListener(this.eingabe.tastatur);
		

	
		
		
	
	}

	public synchronized void start() {
		running = true;
		float near = 0.1f;
		float far = 10000f;
		float fov= 110;
		Matrix.initialisiereProjMatrix((float) Anzeige.WIDTH, (float) Anzeige.HEIGHT, near, far, fov);
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

		this.entityManager.init(this.eingabe);

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns; 
			lastTime = now;
			while (delta >= 1) {
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

	private void update() {

		this.entityManager.update();
		this.frame.requestFocus();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(cölör);
		

		this.entityManager.render(g);
		g.dispose();
		bs.show();
	}

}
