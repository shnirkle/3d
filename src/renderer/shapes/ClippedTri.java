package renderer.shapes;

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
