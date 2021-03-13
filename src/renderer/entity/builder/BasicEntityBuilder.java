package renderer.entity.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import renderer.punkt.Punkt;
import renderer.shapes.Objekt;
import renderer.shapes.Polygon3D;

//Hier werden Objekte aus Dreiecken gebaut, bzw. die nötige Textdatei importiert

public class BasicEntityBuilder {
	
	//Methode zum Einlesen von Textdateien
	
	public static Objekt readFile(String path) {
		List<Punkt> punkte = new ArrayList<Punkt>();
		List<Polygon3D> polys = new ArrayList<Polygon3D>();
		try
		{
			File model = new File(path);
			Scanner myReader = new Scanner(model);

			while (myReader.hasNextLine())
			{

				String data = myReader.nextLine();
				if (data.contains("v"))
				{
					data = data.substring(2);
					
					String[] coords = data.split(" ");
					punkte.add(new Punkt(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));

				}
				if (data.contains("f"))
				{ 
					data = data.substring(2);
					String[] coords = data.split(" ");
					Punkt p1 = punkte.get(Integer.parseInt(coords[0]) - 1);
					Punkt p2 = punkte.get(Integer.parseInt(coords[1]) - 1);
					Punkt p3 = punkte.get(Integer.parseInt(coords[2]) - 1);
					
					polys.add(new Polygon3D(p1, p2, p3));
					

				}
				
			}
			myReader.close();
		} catch (FileNotFoundException e)
		{

		}

		Objekt w = new Objekt(polys.toArray(new Polygon3D[polys.size()]));
		
		return w;
	}
	
	//Ab hier werden Objekte gebaut
	
	public static Objekt createTeapot(float posx, float posy, float posz) {
		Objekt w = readFile("src/crap.txt");
		w.aendern(posx, posy, posz);
		return w;
	}
	public static Objekt createSpaceShip(float posx, float posy, float posz) {
		Objekt w = readFile("src/versuch1.txt");
		w.aendern(posx, posy, posz);
		return w;

	}

	public static Objekt createQuader(float posx, float posy, float posz, float laengeX, float laengeY, float laengeZ) {
		Punkt p1 = new Punkt(posx * laengeX, posy * laengeY, posz * laengeZ); // 0 0 0 p1
		Punkt p2 = new Punkt(posx * laengeX, (posy + 1) * laengeY, posz * laengeZ); // 0 1 0 p2
		Punkt p3 = new Punkt((posx + 1) * laengeX, (posy + 1) * laengeY, posz * laengeZ); // 1 1 0 p3
		Punkt p4 = new Punkt((posx + 1) * laengeX, posy * laengeY, posz * laengeZ);// 			// 1 0 0 p4
		Punkt p5 = new Punkt((posx + 1) * laengeX, (posy + 1) * laengeY, (posz + 1) * laengeZ);// 1 1 1 p5
		Punkt p6 = new Punkt((posx + 1) * laengeX, posy * laengeY, (posz + 1) * laengeZ);// 	// 1 0 1 p6
		Punkt p7 = new Punkt(posx * laengeX, (posy + 1) * laengeY, (posz + 1) * laengeZ);// 	// 0 1 1 p7
		Punkt p8 = new Punkt(posx * laengeX, posy * laengeY, +1 * laengeZ);// 			// 0 0 1 p8

		//		Punkt p1 = new Punkt(-0.5f * laengeX, -0.5f * laengeY, -0.5f * laengeZ);
		//		Punkt p2 = new Punkt(-0.5f * laengeX, +0.5f * laengeY, -0.5f * laengeZ);
		//		Punkt p3 = new Punkt(+0.5f * laengeX, +0.5f * laengeY, -0.5f * laengeZ);
		//		Punkt p4 = new Punkt(+0.5f * laengeX, -0.5f * laengeY, -0.5f * laengeZ);
		//		Punkt p5 = new Punkt(+0.5f * laengeX, +0.5f * laengeY, +0.5f * laengeZ);
		//		Punkt p6 = new Punkt(+0.5f * laengeX, -0.5f * laengeY, +0.5f * laengeZ);
		//		Punkt p7 = new Punkt(-0.5f * laengeX, +0.5f * laengeY, +0.5f * laengeZ);
		//		Punkt p8 = new Punkt(-0.5f * laengeX, -0.5f * laengeY, +0.5f * laengeZ);

		Objekt w = new Objekt(

				new Polygon3D(p1, p2, p3), //
				new Polygon3D(p1, p3, p4), // Front 

				new Polygon3D(p4, p3, p5), //
				new Polygon3D(p4, p5, p6), // Ost 

				new Polygon3D(p6, p5, p7), //
				new Polygon3D(p6, p7, p8), // Hinten

				new Polygon3D(p8, p7, p2), //
				new Polygon3D(p8, p2, p1), // Westen

				new Polygon3D(p2, p7, p5), //
				new Polygon3D(p2, p5, p3), // Top

				new Polygon3D(p4, p6, p8), //
				new Polygon3D(p4, p8, p1) // Unten

		);
		w.aendern(posx, posy, posz);

		return w;

	}

	public static Objekt createWürfel(float posx, float posy, float posz, float laenge) {

		//		Punkt p1 = new Punkt(posx * laenge, posy * laenge, posz * laenge);				 	// 0 0 0 p1
		//		Punkt p2 = new Punkt(posx * laenge, (posy + 1) * laenge, posz * laenge);		 	// 0 1 0 p2
		//		Punkt p3 = new Punkt((posx + 1) * laenge, (posy + 1) * laenge, posz * laenge);	 	// 1 1 0 p3
		//		Punkt p4 = new Punkt((posx + 1) * laenge, posy * laenge, posz * laenge);// 			// 1 0 0 p4
		//		Punkt p5 = new Punkt((posx + 1) * laenge, (posy + 1) * laenge, (posz + 1) * laenge);// 1 1 1 p5
		//		Punkt p6 = new Punkt((posx + 1) * laenge, posy * laenge, (posz + 1) * laenge);// 	// 1 0 1 p6
		//		Punkt p7 = new Punkt(posx * laenge, (posy + 1) * laenge, (posz + 1) * laenge);// 	// 0 1 1 p7
		//		Punkt p8 = new Punkt(posx * laenge, posy * laenge, (posz + 1) * laenge);// 			// 0 0 1 p8

		Punkt p1 = new Punkt(-0.5f * laenge, -0.5f * laenge, -0.5f * laenge);
		Punkt p2 = new Punkt(-0.5f * laenge, +0.5f * laenge, -0.5f * laenge);
		Punkt p3 = new Punkt(+0.5f * laenge, +0.5f * laenge, -0.5f * laenge);
		Punkt p4 = new Punkt(+0.5f * laenge, -0.5f * laenge, -0.5f * laenge);
		Punkt p5 = new Punkt(+0.5f * laenge, +0.5f * laenge, +0.5f * laenge);
		Punkt p6 = new Punkt(+0.5f * laenge, -0.5f * laenge, +0.5f * laenge);
		Punkt p7 = new Punkt(-0.5f * laenge, +0.5f * laenge, +0.5f * laenge);
		Punkt p8 = new Punkt(-0.5f * laenge, -0.5f * laenge, +0.5f * laenge);

		Objekt w = new Objekt(

				new Polygon3D(p1, p2, p3), //
				new Polygon3D(p1, p3, p4), // Front 

				new Polygon3D(p4, p3, p5), //
				new Polygon3D(p4, p5, p6), // Ost

				new Polygon3D(p6, p5, p7), //
				new Polygon3D(p6, p7, p8), // Hinten

				new Polygon3D(p8, p7, p2), //
				new Polygon3D(p8, p2, p1), // Westen

				new Polygon3D(p2, p7, p5), //
				new Polygon3D(p2, p5, p3), // Top

				new Polygon3D(p4, p6, p8), //
				new Polygon3D(p4, p8, p1) // Unten

		);
		w.aendern(posx, posy, posz);

		return w;
	}

}
