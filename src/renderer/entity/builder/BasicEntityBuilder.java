package renderer.entity.builder;

import java.awt.Color;
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

	public static Objekt createTeapot(Color c, float posx, float posy, float posz) {
		Objekt w = readFile("src/teapot.txt");
		w.aendern(posx, posy, posz);
		w.changeBaseColor(c);
		w.rotate(180f, 0, 0);
		return w;
	}

	public static Objekt createSpaceShip(Color c, float posx, float posy, float posz) {
		Objekt w = readFile("src/spaceship.txt");
		w.aendern(posx, posy, posz);
		w.changeBaseColor(c);
		return w;

	}
	public static Objekt createRing(Color c, float posx, float posy, float posz) {
		Objekt w = readFile("src/ring.txt");
		w.aendern(posx, posy, posz);
		w.changeBaseColor(c);
		return w;
		
	}
	public static Objekt createWürfel(Color c, float posx, float posy, float posz, float laenge) {

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
		w.changeBaseColor(c);
		
		return w;
	}
	
	public static Objekt createSphere(Color c, Punkt m, float r, int res)  {

		if (res < 4)
		{
			res = 4;
		}
        
        Punkt[] punkte = new Punkt[(int) (2*Math.pow(res,2) - 1)];
        for (int i = 0; i < punkte.length; i++) {
            punkte[i] = new Punkt(0, 0, 0);
        }
        int count = 0;
        Punkt punkt = new Punkt(0,0,0);
        double theta;
        double phi;
        double dt = (2*Math.PI)/res;
        double dp = Math.PI/res;
        Polygon3D[] polys = new Polygon3D[(int) (2*Math.pow(res,2) + res * 2)]; //////////////////
        for (int i = 0; i < polys.length; i++) {
            polys[i] = new Polygon3D(new Punkt(0, 0, 0), new Punkt(0, 0, 0), new Punkt(0, 0, 0));
        }
        //Punktmenge der Sphäre berechnen (10 Breitengrade, 10 Längengrade)

        for(int pi = 0; pi <= res; pi++) {
                    phi = pi * dp;
                    for(int ti = 0; ti <= res; ti++) {
                        theta = ti * dt;
                        punkt.x = (float) ((r * Math.sin(theta) * Math.sin(phi)) + m.x);
                        punkt.y = (float) (r * Math.cos(phi) + m.y);
                        punkt.z = (float) (r * Math.cos(theta) * Math.sin(phi) + m.z);
                        punkte[count].x = punkt.x;
                        punkte[count].y = punkt.y;
                        punkte[count].z = punkt.z;
                        count++;
                    }
                }
                count = 0;
//Dreiecke aus der Punktmenge machen
                for(int pi = 0; pi <= res; pi++) {
                    for(int ti = 0; ti < res; ti++) {
                        int x0 = ti;
                        int x1 = ti+1;
                        int y0 = pi * (res+1);
                        int y1 = (pi + 1) * (res+1);
                        polys[count] = new Polygon3D(punkte[x0+y0], punkte[x0+y1], punkte[x1+y0]);
                        polys[count+1] = new Polygon3D(punkte[x1+y0], punkte[x0+y1], punkte[x1+y1]);
                        count += 2;
                    }
                }
        //aus Dreiecken letztendliche Kugel bauen
                Objekt sphere = new Objekt(c, polys);

            return sphere;
        }

}
