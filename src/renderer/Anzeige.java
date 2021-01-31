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
import renderer.steuerung.KlickTyp;
import renderer.steuerung.Maus;

public class Anzeige extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private JFrame frame;
	private static String titel = "Sterne";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private static boolean running = false;

	private EntityManager entityManager;
	
	private Maus maus;

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
		
		this.maus = new Maus();
		this.addMouseListener(this.maus);
		this.addMouseMotionListener(this.maus);
		this.addMouseWheelListener(this.maus);
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

		this.entityManager.init();

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

	int startX, startY;
	double ges = 2.5; //ges steht für Geschwindigkeit
	
	private void update() {
		this.entityManager.update();
		
	//Mauskontrolle
		
		//System.out.println(this.maus.getX() + "," + this.maus.getY()); //Mauskoordinaten anzeigen
		//System.out.println(this.maus.getButton()); //Maustaste anzeigen
			int x = this.maus.getX();
			int y = this.maus.getY();
			if (this.maus.getButton() == KlickTyp.Linksklick) { // Höhe - Breite - Rotierung
				int xDif = x - startX;
				int yDif = y - startY;
				
				this.entityManager.rotate(true, 0, -yDif/ges, -xDif/ges);
			}
			else if (this.maus.getButton() == KlickTyp.Rechtsklick) { //Tiefe - Rotierung
				int xDif = x - startX;
			
				this.entityManager.rotate(true, -xDif/ges, 0, 0);
			}
			
			else if (this.maus.getButton() == KlickTyp.Mittelklick) { //Zoom zurücksetzen

				PunktTransform.standardzoom();
			}
			
			if (this.maus.scrollUp()) {
				PunktTransform.reinzoomen();
			} else if (this.maus.scrollDown()) {
				PunktTransform.rauszoomen();
			}
			
			this.maus.resetScroll();
			
			startX = x;
			startY = y;
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
