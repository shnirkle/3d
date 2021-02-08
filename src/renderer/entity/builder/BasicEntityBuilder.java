package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.Entity;
import renderer.entity.iEntity;

import renderer.punkt.Punkt;
import renderer.shapes.Objekt;
import renderer.shapes.Polygon3D;

public class BasicEntityBuilder {

	private static Color red = Color.RED;
	private static Color blue = Color.BLUE;

	public static iEntity createWürfel(float posx, float posy, float posz, float laenge) {

//		Punkt p1 = new Punkt(posx * laenge, posy * laenge, posz * laenge);// // 0 0 0 p1
//		Punkt p2 = new Punkt(posx * laenge, (posy + 1) * laenge, posz * laenge);// // 0 1 0 p2
//		Punkt p3 = new Punkt((posx + 1) * laenge, (posy + 1) * laenge, posz * laenge);// // 1 1 0 p3
//		Punkt p4 = new Punkt((posx + 1) * laenge, posy * laenge, posz * laenge);// // 1 0 0 p4
//		Punkt p5 = new Punkt((posx + 1) * laenge, (posy + 1) * laenge, (posz + 1) * laenge);// // 1 1 1 p5
//		Punkt p6 = new Punkt((posx + 1) * laenge, posy * laenge, (posz + 1) * laenge);// // 1 0 1 p6
//		Punkt p7 = new Punkt(posx * laenge, (posy + 1) * laenge, (posz + 1) * laenge);// // 0 1 1 p7
//		Punkt p8 = new Punkt(posx * laenge, posy * laenge, (posz + 1) * laenge);// // 0 0 1 p8

		Punkt p1 = new Punkt(0, 0, 0);
		Punkt p2 = new Punkt(0, 1, 0);
		Punkt p3 = new Punkt(1, 1, 0);
		Punkt p4 = new Punkt(1, 0, 0);
		Punkt p5 = new Punkt(1, 1, 1);
		Punkt p6 = new Punkt(1, 0, 1);
		Punkt p7 = new Punkt(0, 1, 1);
		Punkt p8 = new Punkt(0, 0, 1);

		Objekt w = new Objekt(
				
				
				
				
				
				
				new Polygon3D(p1, p2, p3), //
				new Polygon3D(p1, p3, p4),

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

		List<Objekt> ws = new ArrayList<Objekt>();
		ws.add(w);
		return new Entity(ws);
	}

	public static iEntity createDreieck(Punkt p1, Punkt p2, Punkt p3) {

		Objekt w = new Objekt(new Polygon3D(p1, p2, p3));

		List<Objekt> ws = new ArrayList<Objekt>();
		ws.add(w);
		return new Entity(ws);
	}

}
