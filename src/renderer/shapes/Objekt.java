package renderer.shapes;

import java.awt.Color;
import java.util.List;

import renderer.Anzeige;
import renderer.punkt.Matrix;
import renderer.punkt.Vektor;
import renderer.world.Kamera;

public class Objekt {

	private Polygon3D[] polygons;
	private float xGrad, yGrad, zGrad;
	public float xOff, yOff, zOff;

	public Objekt(Color color, Polygon3D... polygons) {
		this.polygons = polygons;
		for(Polygon3D p : polygons) {
			p.baseColor = color;
		}

	}

	//Objekt aus Polygons

	public Objekt(Polygon3D... polygons) {
		//this.color = Color.WHITE;
		this.polygons = polygons;

	}

	public Objekt(List<Polygon3D> polys) {
		this.polygons = polys.toArray(new Polygon3D[polys.size()]);
	}

	//Rendern f�r die Rotationen unserer Rotationsmatrixen / Weltmatrix
	
	public void render() {
		if(Vektor.sub(new Vektor(xOff, yOff, zOff), Kamera.vCamera).length < Anzeige.getViewDistance()) {
		Matrix xRot = Matrix.rotateAxisX(xGrad);
		Matrix yRot = Matrix.rotateAxisY(yGrad);
		Matrix zRot = Matrix.rotateAxisZ(zGrad);
		
		Matrix mTrans = Matrix.aenderKoordinaten(xOff, yOff, zOff);
		
		Matrix weltMat = new Matrix();
		weltMat.matrixInitialisierung();
		
		weltMat = Matrix.multMatrix_Matrix(zRot, xRot);
		weltMat = Matrix.multMatrix_Matrix(yRot, weltMat);
		weltMat = Matrix.multMatrix_Matrix(weltMat, mTrans);
		
		
		
		for (Polygon3D poly : this.polygons)
		{
			
			poly.calc(weltMat);
		} 
		}
	}

	

	//Abweichung (Offset) beim Rotieren beheben

	public void aendern(float x, float y, float z) {
		xOff += x;
		yOff += y;
		zOff += z;
	}

	//Rotieren, dann sortieren
	public void changeBaseColor(Color color) {
		for(Polygon3D p : polygons) {
			p.baseColor = color;
		}
	}
	public void rotate(float xGrad, float yGrad, float zGrad) {
		this.xGrad = xGrad;
		this.yGrad = yGrad;
		this.zGrad = zGrad;
		this.sortieren();
	}

	private void sortieren() {
		//		Polygon3D.polygonSortieren(this.polygons);
	}

	public Polygon3D[] getPolygons() {
		return this.polygons;
	}

	public void scale(float scale) {
		for(Polygon3D p : this.polygons) {
			p.scale(scale);
		}
		
	}
}
