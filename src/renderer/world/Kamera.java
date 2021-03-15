package renderer.world;

import renderer.punkt.Matrix;
import renderer.punkt.Vektor;

public class Kamera {

	public static float VEL = 0.0f;
	public static Vektor vCamera = new Vektor(0, 1.5f, 4.0f);
	public static Vektor vLook = new Vektor(0, 0, 0);
	public static double yGrad = 0;
	public static double xGrad = 0;
	public static double zGrad = 0;
	public static Matrix sicht;
	public static Vektor vDir = new Vektor(0, 0, 1);
	public static Vektor vUp = new Vektor(0, 1, 0);
	public static Vektor vRight = new Vektor(1, 0, 0);

	public static void up(float u) {
		vCamera.setY(vCamera.y + u);
	}

	//Verschiedene Methoden zum Rotieren/Bewegen der Kamera, damit wir die Objekte abh�ngig von unserer Position behandeln k�nnen

	public static void rotierenLR(double angX, double angY, double angZ) {
		yGrad += angY;
		xGrad += angX;
		zGrad += angZ;

	}

	public static void vorwaerts(float u) {
		VEL += u;

		//		vCamera.setZ(vCamera.normZ + u);
	}

	//Kamerakoordinaten

	public static float getX() {
		return Kamera.vCamera.x;
	}

	public static float getY() {
		return Kamera.vCamera.y;
	}

	public static float getZ() {
		return Kamera.vCamera.z;
	}

	//Updaten der Kamerainfos, um Vektoren f�r "das n�chste Sehen" zu erhalten und die Matrixen dementsprechen zu �ndern

	public static void updateCam() {

		if (Math.abs(VEL) < 3)
		{
			VEL *= 0.8f;
			if (Math.abs(VEL) < 0.1f)
			{
				VEL = 0;
			}
		}
		Vektor vforw = Vektor.multVektor_Faktor(vLook, VEL);
		vCamera = Vektor.add(vCamera, vforw);

		Matrix xRot = Matrix.rotateAxisX(xGrad);
		Matrix yRot = Matrix.rotateAxisY(yGrad);
		Matrix zRot = Matrix.rotateAxisZ(zGrad);

		Vektor vTarget = new Vektor(0, 0, 1);
		Vektor vUpTarget = new Vektor(0, 1, 0);
		Vektor vRightTarget = new Vektor(0, 0, 1);

		vLook = Matrix.multMat(vTarget, xRot);
		vUpTarget = Matrix.multMat(vUpTarget, xRot);

		vUpTarget = Matrix.multMat(vUpTarget, yRot);
		vLook = Matrix.multMat(vLook, yRot);

		vUpTarget = Matrix.multMat(vUpTarget, zRot);
		vLook = Matrix.multMat(vLook, zRot);

		vDir = Vektor.add(vCamera, vLook);
		Matrix camMatrix = Matrix.richteKameraMatrix(vDir, vUpTarget, vCamera); //M

		sicht = Matrix.matrixInvertierung(camMatrix);
		//		sicht.printMat();
	}

	public static void KameraZur�ck() {
		yGrad = 0;
		xGrad = 0;
		zGrad = 0;
		vCamera = new Vektor(0, 0, 0);

	}
}
