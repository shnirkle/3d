package renderer.shapes;

import java.awt.Color;

import renderer.punkt.Vektor;

//Hier ist ein ganz simples ursprüngliches ("primal") Dreieck 

public class PrimTri {
	public Vektor[] points;
	public Color color = Color.white;
	
	public PrimTri(Vektor[] p, Color shade) {
		this.points = p;
		this.color = shade;
	}
	
//durchschnittliche Z Koordinate (hier die Tiefe) im Dreieck nehmen, um zu ermitteln, welche vor anderen liegen
	
	public float getAvgZ() {
		float avgZ = (this.points[0].z + this.points[1].z + this.points[2].z) / 3;
		return avgZ;
	}
}
