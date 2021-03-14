package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public final class PauseView  implements ActionListener{
	
	JFrame frame;
	JLabel label;
	
	JButton buttonWeiter;
	JButton buttonOptionen;
	JButton buttonVerlassen;
	
	//5 Panels (Nord, Süd, West, Ost, Mitte). Letztendlich wird nur das in der Mitte genutzt. Die anderen dienen dazu, einen Rand zu lassen
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	JPanel panel5;	//Panel 5 wird später das zentrale Panel. Es enthält die Knöpfe.
	
	//3 Panels, die sich in Panel 5 befinden
	JPanel panel6;
	JPanel panel7;
	JPanel panel8;
	
	public PauseView() {
		
		label = new JLabel("-  Pausiert  -");
		
		buttonWeiter = new JButton ("Weiter");
		buttonOptionen = new JButton("Einstellungen");
		buttonVerlassen = new JButton("Spiel verlassen");
		
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		
		//Farben können natürlich auch verändert werden, um Panels zu visualisieren
		Color backgroundColor = new Color(48, 52, 64);
		panel1.setBackground(backgroundColor);
		panel2.setBackground(backgroundColor);
		panel3.setBackground(backgroundColor);
		panel4.setBackground(backgroundColor);
		panel5.setBackground(backgroundColor);
		
		//Breite und Höhe (in der Reihenfolge) der Dimensionen der Panels
		panel1.setPreferredSize(new Dimension(100,100)); //Dimension(Breite, Höhe)
		panel2.setPreferredSize(new Dimension(70,100));
		panel3.setPreferredSize(new Dimension(100,100));
		panel4.setPreferredSize(new Dimension(75,100));
		panel5.setPreferredSize(new Dimension(100,60));
		
		//Panels mit dem BorderLayout haben fünf Ausrichtungen: oben, rechts, unten, links, Mitte
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.EAST);
		//frame.add(panel3, BorderLayout.SOUTH);
		frame.add(panel4, BorderLayout.WEST);
		frame.add(panel5, BorderLayout.CENTER);
		
		//----------------------- Panels innerhalb des zentralen Panels (panel5): Sub Panels ------------------
		panel6 = new JPanel();
		panel7 = new JPanel();
		panel8 = new JPanel();

		panel6.setBackground(backgroundColor);
		panel7.setBackground(backgroundColor);
		panel8.setBackground(backgroundColor);
		
		panel6.setPreferredSize(new Dimension(50,50)); 
		panel7.setPreferredSize(new Dimension(50,50));
		panel8.setPreferredSize(new Dimension(50,50));

		panel5.setLayout(new GridLayout(4,1, 100,10)); 	//GridLayout(Zeilen, Spalten der Tabelle, Abstand rechts / links, Abstand unten / oben)
		panel5.add(panel6, BorderLayout.NORTH); 
		panel5.add(panel7, BorderLayout.EAST);
		panel5.add(panel8, BorderLayout.SOUTH);

		//----------------------- Buttons auf den drei Panels: Weiter (unpausieren), Optionen (neues Fenster, Optionen), Spiel verlassen -----------------------
		panel6.add(buttonWeiter);
		panel7.add(buttonOptionen);
		panel8.add(buttonVerlassen);
			
		buttonWeiter.addActionListener(this);
		buttonOptionen.addActionListener(this);
		buttonVerlassen.addActionListener(this);
		
		buttonWeiter.setFocusable(false);		//Option lässt das Rechteck, das in einem Button erscheint, wenn man ihn angeklickt hat, verschwinden
		buttonOptionen.setFocusable(false);
		buttonVerlassen.setFocusable(false);
		
		buttonWeiter.setBackground(backgroundColor);
		buttonOptionen.setBackground(backgroundColor);
		buttonVerlassen.setBackground(backgroundColor);
		
		buttonWeiter.setForeground(Color.white);
		buttonOptionen.setForeground(Color.white);
		buttonVerlassen.setForeground(Color.white);
		
		buttonWeiter.setFont(new Font("Calibri", Font.BOLD ,20));
		buttonOptionen.setFont(new Font("Calibri", Font.BOLD ,20));
		buttonVerlassen.setFont(new Font("Calibri", Font.BOLD ,20));;
		
		//----------------------- "Pausiert" Label -----------------------
		panel1.add(label);
		label.setForeground(Color.white);
		label.setFont(new Font("Calibri", Font.BOLD ,25));
		label.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));	//Rand
		
		//----------------------- Frame Einstellungen -----------------------------------
		//frame.addActionListener(this); eigentlich will ich einen Listener für den ganzen Frame. mal schauen, wie das geht. mach ich aber ein andermal
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//Frame wird beim Klick auf rotes Kreuz geschlossen, aber nicht das ganze Programm (dispose statt exit)
		//frame.setTitle("Pause");
		frame.setPreferredSize(new Dimension(400, 500));
		frame.setUndecorated(true);			//versteckt die Titelleiste. Muss vor pack() geschrieben werden.
		frame.setAlwaysOnTop(true);
		frame.pack();
		frame.setLocationRelativeTo(null);	//zentriert das Fenster auf dem Bildschirm
		frame.setVisible(true);
	    //frame.setResizable(false);			//Fenster kann nicht vergrößert / verkleinert werden
		
	}
	
//	//ein ActionListener für alle Buttons
//	void addPauseListener(ActionListener listenPauseBtn) {
//		buttonWeiter.addActionListener(listenPauseBtn);
//		buttonOptionen.addActionListener(listenPauseBtn);
//		buttonVerlassen.addActionListener(listenPauseBtn);
//	}
//	
//	
//	
//	//Funktion wird gebraucht, um den Frame in der Controllerklasse schließen zu können (Klick auf Weiter)
//	public static JFrame getPauseFrame() {
//		return PauseView.frame;
//	}
//	
//	void closeFrame() {
//		frame.dispose();
//	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Button: Weiter
		if(e.getSource()==buttonWeiter) {
			Anzeige.isPaused = false;
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));	//schließt das PauseScreen Fenster
		}
		
		//Button: Optionen
		if(e.getSource()==buttonOptionen) {
			//schließt aktuelles Fenster, um Optionen zu öffnen	
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); 
			Optionen optionen = new Optionen();	
		}
		
		//Button: Spiel verlassen
		if(e.getSource()==buttonVerlassen) {
			System.exit(0);	//Beendet das Programm. Ein Wert, der nicht null wäre, würde angeben, dass ein Fehler der Grund für das Schließen des Programms war
		}
		
	}
	
}

