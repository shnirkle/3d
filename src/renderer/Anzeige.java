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
import java.io.File;
import java.util.Formatter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import renderer.entity.EntityManager;
import renderer.punkt.Matrix;
import renderer.punkt.Vektor;
import renderer.steuerung.Eingabe;
import renderer.world.Kamera;

public class Anzeige extends Canvas implements Runnable {
	public static boolean isPaused = false;
	private static final long serialVersionUID = 1L;
	private Color cölör = new Color(0, 0, 0);
	private Thread thread;
	private JFrame frame;
	private static String TITEL = "Sterne";
	public static int WIDTH = 1000;
	public static int HEIGHT = 1000;
	public static int framesATM = 0;
	public static double targetfps = 60;
	public static double ns = 1000000000.0/targetfps;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double SCREENWIDTH = screenSize.getWidth();
	static double SCREENHEIGHT = screenSize.getHeight();

	public Vektor cam = new Vektor(0, 0, 0);
	private static boolean running = false;

	public static EntityManager entityManager;

	private static Eingabe eingabe;
	private static double viewDistance = 1000;

	public static void main(String[] args) {
		JFrame home = new JFrame();
		JLabel juno = new JLabel("3D", JLabel.CENTER);

		home.setTitle("Startmenü :)");
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("src/images/raumschiff.png");
        home.setIconImage(icon.getImage());
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

	//Konstruktor für unsere Anzeige

	public Anzeige() {
		this.frame = new JFrame();
		ImageIcon icon = new ImageIcon("src/images/raumschiff.png");
        this.frame.setIconImage(icon.getImage());
		Anzeige.entityManager = new EntityManager();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);

		Anzeige.eingabe = new Eingabe();

		this.frame.addKeyListener(Anzeige.eingabe.tastatur);

	}
	
	//Abspielen unserer Hintergrundmusik
	
	public void playSound() {
	    try {
	        AudioInputStream input = AudioSystem.getAudioInputStream(new File("src/hintergrundmusik.wav").getAbsoluteFile());
	        Clip hintergrundmusik = AudioSystem.getClip();
	        hintergrundmusik.open(input);
	        hintergrundmusik.start();
	    } catch(Exception ex) {
	        System.out.println("Error beim Abspielen der Hintergrundmusik");
	        ex.printStackTrace();
	    }
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
		playSound();	//erstes Starten der Hintergrundmethode, dann wiederholen in der run-Methode
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
		int songdauer = 275 * 1000000000;

		Anzeige.entityManager.init(Anzeige.eingabe);

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
			}

			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
//				this.frame.setTitle(TITEL + " | " + frames + "fps");
			}
			if (System.currentTimeMillis() - timer > songdauer)	//Musik loopen
			{
				 playSound();
			}
		}
		stop();

	}

	//Gibt der Tastatur den Fokus, damit der KeyListener funktioniert. -> Entity Updates im EntityManager	

	private void update() {

		if (!Anzeige.isPaused)
		{
			Anzeige.entityManager.update();
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

		g.setColor(cölör);

		Anzeige.entityManager.render(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, HEIGHT - 30, WIDTH, 30);
		g.setColor(Color.WHITE);
		StringBuilder stringBuilder = new StringBuilder();
		Formatter formatter = new Formatter(stringBuilder);
		formatter.format("POS(X:Y:Z): (%-4f/%-4f/%-4f) VEL: %f", Kamera.vCamera.x, Kamera.vCamera.y, Kamera.vCamera.z, Kamera.VEL);
		String str = stringBuilder.toString();
//		String str = "POS(X:Y:Z): (" + Kamera.vCamera.x + "/" + Kamera.vCamera.y + "/" + Kamera.vCamera.z +")";
		g.drawString(str, 0, HEIGHT - 10);
		g.dispose();
		bs.show();
		
	}
	public static double getTargetFps() {
		return targetfps;
	}
	public static void changeFpsTarget(double fps) {
		ns = 1000000000.0/fps;
		targetfps = fps;
	}

	public static double getViewDistance() {
		return viewDistance;
	}

	public static void changeViewDistance(Double valueOf) {
		viewDistance = valueOf;
		
	}
}
