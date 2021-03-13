package renderer.shapes;

//Diese Klasse ist für Clipped-Dreiecke. Das heißt, wenn ein Dreieck
//außerhalb von unserem Bild ist, wird es nicht angezeigt. Ist es aber
//nur zum Teil aus unserem Bild, zerlegen wir es in geclippte Dreiecke
//um dann den Teil, der noch auf unserer Projektionsmatrix ist, anzuzeigen.

public class ClippedTri {
	public Polygon3D poly1;
	public Polygon3D poly2;
	public int numTri = 0;
	
	
	public ClippedTri(int i, Polygon3D poly1, Polygon3D poly2) {
		this.numTri = i;
		this.poly1 = poly1;
		this.poly2 = poly2;
	}
	public int getInt() {
		return numTri;
	}
}
