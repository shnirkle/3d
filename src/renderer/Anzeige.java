package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import renderer.entity.EntityManager;
import renderer.punkt.Matrix;
import renderer.punkt.Vektor;
import renderer.steuerung.Eingabe;

public class Anzeige extends Canvas implements Runnable {
	public static boolean isPaused = false;
	private static final long serialVersionUID = 1L;
	private Color c�l�r = new Color(0, 0, 0);
	private Thread thread;
	private JFrame frame;
	private static String TITEL = "Sterne";
	public static int WIDTH = 1000;
	public static int HEIGHT = 1000;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double SCREENWIDTH = screenSize.getWidth();
	static double SCREENHEIGHT = screenSize.getHeight();

	public Vektor cam = new Vektor(0, 0, 0);
	private static boolean running = false;

	public EntityManager entityManager;

	private static Eingabe eingabe;

	public static void main(String[] args) {
		JFrame home = new JFrame();
		JLabel juno = new JLabel("3D", JLabel.CENTER);

		home.setTitle("Startmen� :)");
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		juno.setForeground(Color.WHITE);
		juno.setFont(new Font("Algerian", Font.PLAIN, 55));

		ImageIcon ii1 = new ImageIcon("src\\images\\Weltall.png"); //Bildquelle: https://www.deviantart.com/fi3ur/art/Fractal-Space-5-681865162
		Image img1 = ii1.getImage();
		Image img2 = img1.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_SMOOTH);
		home.setContentPane(new JLabel(new ImageIcon(img2)));

		//---------Button Settings-------
		JButton button = new JButton("START");

		button.setBounds(WIDTH / 2 - 40, HEIGHT / 2 + 30, 100, 40);

		button.setFocusPainted(false);
		button.setFocusable(false);
		button.setBackground(new Color(48, 52, 64));
		button.setForeground(Color.white);
		button.setFont(new Font("Calibri", Font.BOLD, 20));

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				home.dispose();
				init();
			}
		});

		//---------Layout-----------------
		GridBagConstraints gBC = new GridBagConstraints();
		gBC.gridwidth = 100;
		home.setLayout(new GridBagLayout());
		home.add(button, gBC);

		//--------------------------------

		home.setBounds((int) (Anzeige.SCREENWIDTH / 2 - WIDTH / 2), 0, 1000, 1000);
		home.pack();
		home.setVisible(true);
	}

	public static void init() {

		Anzeige display = new Anzeige();
		display.frame.setTitle(TITEL);
		display.frame.add(display);
		display.frame.setUndecorated(true);
		display.frame.setResizable(false);
		display.frame.setBounds((int) (Anzeige.SCREENWIDTH/2 - WIDTH/2), 0, 1000, 1000);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setVisible(true);
		display.start();

	}

	//Konstruktor f�r unsere Anzeige

	public Anzeige() {
		this.frame = new JFrame();
		this.entityManager = new EntityManager();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);

		Anzeige.eingabe = new Eingabe();

		this.frame.addKeyListener(Anzeige.eingabe.tastatur);

	}
	//Start und Stop Methode

	// Initialisieren des Threads und der Projektionsmatrix, welche auf unserem Bildschirm liegt

	public synchronized void start() {
		running = true;
		float near = 0.1f;
		float far = 10000f;
		float fov = 110;
		Matrix.initialisiereProjMatrix((float) Anzeige.WIDTH, (float) Anzeige.HEIGHT, near, far, fov);
		thread = new Thread(this, "Anzeige");
		this.thread.start();
	}

	public void stop() {
		running = false;
		try
		{
			this.thread.join();
		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

	}

	//Auf 60FPS setzen, Tastatur initialisieren, ... -> Was alles beim initialisieren gebraucht wird

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60;
		double delta = 0;
		int frames = 0;

		this.entityManager.init(Anzeige.eingabe);

		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				update();
				delta--;
				render();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
//				this.frame.setTitle(TITEL + " | " + frames + "fps");
				frames = 0;
			}
		}
		stop();

	}

	//Gibt der Tastatur den Fokus, damit der KeyListener funktioniert. -> Entity Updates im EntityManager	

	private void update() {

		if (!Anzeige.isPaused)
		{
			this.entityManager.update();
			this.frame.requestFocus();
		}

	}

	//Rendermethode: Zeichnet Hintergrund, [...] -> Das Interessante ist im EntityManager

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(c�l�r);

		this.entityManager.render(g);
		g.dispose();
		bs.show();
	}

}
