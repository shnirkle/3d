package renderer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import renderer.world.Kamera;

// Bereiche, die Optionen abdecken:
// - Leistung / Qualität (FPS Limit, Anzahl der Objekte, View Distance, Orientierungshilfen)
// - Raumschiff (FOV, Beschleunigung / max. Tempo)
// - Audio (SFX, Musik)?
// - Weiteres (Wireframe)

public class Optionen {

	JFrame frame;

	//TabbedPane: Layout, das das Wechseln zwischen Panels mithilfe von Tabs erlaubt.
	//Viel praktischer als das CardLayout (mit dem ich erstmal meine Zeit verschwendet habe -- ahhhh!!!), 
	//da das Wechseln zwischen den Panels im Design inbegriffen ist --> keine extra Knöpfe
	JTabbedPane tabpane;

	//Insgesamt habe ich vier Bereiche in den Optionen => 4 Panels
	JPanel panelLeistung;
	JPanel panelRaumschiff;
	JPanel panelAudio;
	JPanel panelWeiteres;

	public Optionen() {
		init(); //Initialisierung der Variablen (zusammengefasst in Funktion)
		setupTabbedLayout(); //Implementierung des TabbedLayouts
		specializePanels(); //Jedes große "Kategorie-Panel" enthält Subpanels, die wiederum GUI-Elemente für Einstellungen enthalten. Dafür hatte ich allerdings keine Zeit mehr

		frame.add(tabpane);

		//Wenn das Fenster geschlossen wird

		WindowListener schliessen = new WindowAdapter() {

			@SuppressWarnings("unused")
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
				PauseView pauseview = new PauseView();
			}
		};
		frame.addWindowListener(schliessen);

		//----------------------- Frame Einstellungen ------------------------
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Frame wird beim Klick auf rotes Kreuz geschlossen, aber nicht das ganze Programm (dispose statt exit)
		frame.setPreferredSize(new Dimension(900, 500));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	//Methode, in der der ganze Initialisierungs-Mist drin ist - dann wird der Konstruktor nicht zugemüllt
	public void init() {

		frame = new JFrame("Einstellungen");

		panelLeistung = new JPanel();
		panelRaumschiff = new JPanel();
		panelAudio = new JPanel();
		panelWeiteres = new JPanel();
	}

	public void setupTabbedLayout() {

		tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		tabpane.addTab("Render Einstellungen", panelLeistung);
		tabpane.addTab("Raumschiff", panelRaumschiff);
		tabpane.addTab("Audio", panelAudio);
		tabpane.addTab("Szene", panelWeiteres);

		//------------ Verschönerung der Tab-Titel ---------------
		JLabel lab = new JLabel("Render Einstellungen");
		lab.setPreferredSize(new Dimension(50, 30));
		tabpane.setTabComponentAt(0, lab); // tab index, jLabel
	}

	public void specializePanels() {
		//Diese Panels dienen momentan nur als Platzhalter, da mir die Zeit fehlte, die Optionen fertig zu machen.
		//Es können noch mehr Subpanels in den Panels (Leistung, Raumschiff, etc.) erschaffen und nach einem beliebigen Layout angeordnet werden.
		//Letztendlich würden die Subpanels die für den Einstellungsbereich spezifischen Einstellungen beinhalten.
		//Bei Raumschiff könnte man zum Beispiel die Geschwindigkeit einstellen, bei Leistung die maximalen FPS und so weiter.
		//Je nach Einstellung kann man dafür Slider, Textfelder, usw. benutzen.

		JPanel leistung1 = new JPanel();
		JPanel raumschiff1 = new JPanel();
		JPanel audio1 = new JPanel();
		JPanel weiteres1 = new JPanel();

		panelLeistung.add(leistung1);
		panelRaumschiff.add(raumschiff1);
		panelAudio.add(audio1);
		panelWeiteres.add(weiteres1);

		//---------- Hinzufügen von GUI-Elementen auf den Subpanels - erstmal nur JLabels als Platzhalter ----------
		JLabel labLeistung = new JLabel("Vorstellbare Einstellungen: Maximale FPS, Renderdistanz der Objekte, maximale Anzahl an Objekten, ...");
		JLabel labRaumschiff = new JLabel("Vorstellbare Einstellungen: Geschwindigkeit, FOV, Steuerung (+ Anzeigen der momentanen Tastenbelegung), ...");
		JLabel labAudio = new JLabel("Vorstellbare Einstellungen (hätten wir Musik gemacht): Musiklautstärke, SFX-Lautstärke, ...");
		JLabel labWeiteres = new JLabel("Vorstellbare Einstellungen: Wireframe (nur Kanten der Objekte), andere Einstellungen ohne eindeutige Kategorie");
		
		
		
		//-----------------------------------------------
		String fpsS = Double.toString(Anzeige.getTargetFps());
		String vdS = Double.toString(Anzeige.getViewDistance());
		
		JCheckBox kettenRahmen = new JCheckBox("", false);
		JTextField fps = new JTextField(fpsS);
		JTextField viewDistance = new JTextField(vdS);  
		JButton labLeistungApply = new JButton("Anpassen");
		
		
		leistung1.add(labLeistung);
		leistung1.add(fps);
		leistung1.add(labLeistungApply);
		leistung1.add(viewDistance);
		leistung1.add(kettenRahmen);
		raumschiff1.add(labRaumschiff);
		audio1.add(labAudio);
		weiteres1.add(labWeiteres);
		labLeistungApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				Anzeige.changeFpsTarget(Double.valueOf(fps.getText()));
				Anzeige.changeViewDistance(Double.valueOf(viewDistance.getText()));
				Renderer.setWireFrame(kettenRahmen.isSelected());
				
			}
		});
		
		
		
		
		//---------------------------------------------
		/*
		 * --> Teapot
		 * --> Grid
		 * --> Spaceship
		 * --> SolarsystemScale
		 * --> SolarsystemModel
		 * --> Earth & Moon
		 * --> Saturn
		 * 
		 */
		List<JButton> buttonList = new ArrayList<JButton>();
		
		JButton createTeapot = new JButton("Lade Teekanne");
		JButton createGrid = new JButton("Lade Grid");
		JButton createSpaceship = new JButton("Lade Spaceship");
		JButton createSolarsystemScale = new JButton("Lade Sonnensytem im Maßstab");
		JButton createSolarsystemModel = new JButton("Lade Sonnensystem nicht im Maßstab");
		JButton createEarth_Moon = new JButton("Lade Erde und Mond");
		JButton createSaturn = new JButton("Lade Saturn");
		
		buttonList.add(createEarth_Moon);
		buttonList.add(createSolarsystemScale);
		buttonList.add(createSolarsystemModel);
		buttonList.add(createTeapot);
		buttonList.add(createGrid);
		buttonList.add(createSpaceship);		
		buttonList.add(createSaturn);
		
		
		
		for(JButton jb : buttonList) {
			weiteres1.add(jb);
			jb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					
					int a = buttonList.indexOf(jb);
					
					switch (a)
					{
					case 0: 
						Kamera.KameraZurück();
//						System.out.println("0");
						Anzeige.entityManager.createEarth_Moon();
						break;
					case 1:
						Kamera.KameraZurück();
//						System.out.println("1");
						Anzeige.entityManager.createSolarSystem(0.0001f, 0.0001f);
						break;
					case 2:
						Kamera.KameraZurück();
//						System.out.println("2");
						Anzeige.entityManager.createSolarSystemNOTSCALE();;
						break;
					case 3:
						Kamera.KameraZurück();
//						System.out.println("3");
						Anzeige.entityManager.createTeapot();
						break;
					case 4:
						Kamera.KameraZurück();
//						System.out.println("4");
						Anzeige.entityManager.createGrid();
						break;
					default:
//						System.out.println("def");
						break;
					}
				}
			});
			
		}
		
		
		//	//----------------------- Ästhetik -----------------------
		//		Color backgroundColor = new Color(48, 52, 64);
		//		leistung1.setBackground(backgroundColor);
		//		raumschiff1.setBackground(backgroundColor);
		//		audio1.setBackground(backgroundColor);
		//		weiteres1.setBackground(backgroundColor);
	}

}
