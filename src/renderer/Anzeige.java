package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.entity.EntityManager;
import renderer.punkt.Punkt;
import renderer.punkt.PunktTransform;
import renderer.shapes.Polygon3D;
import renderer.shapes.Würfel;
import renderer.steuerung.Eingabe;
import renderer.steuerung.KlickTyp;
import renderer.steuerung.Maus;
import renderer.steuerung.Tastatur;

public class Anzeige extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private JFrame frame;
	private static String titel = "Sterne";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	private static boolean running = false;

	private EntityManager entityManager;
	
	private Eingabe eingabe;

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
		this.entityManager = new EntityManager();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);

		this.eingabe = new Eingabe();
		
		this.addMouseListener(this.eingabe.maus);
		this.addMouseMotionListener(this.eingabe.maus);
		this.addMouseWheelListener(this.eingabe.maus);
		this.frame.addKeyListener(this.eingabe.tastatur);
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
//		this.frame.requestFocus();
		
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

		this.entityManager.render(g);
		g.dispose();
		bs.show();
	}
	
}
