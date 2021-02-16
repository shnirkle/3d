package renderer.punkt;

import renderer.Anzeige;

public class Matrix {
	public float[][] mat = new float[4][4];
	public static Matrix projectionMatrix = new Matrix();
	public static Matrix xRotMatrix = new Matrix();
	public static Matrix yRotMatrix = new Matrix();
	public static Matrix zRotMatrix = new Matrix();
	public static Matrix aender = new Matrix();
	float ar = (float) Anzeige.HEIGHT / Anzeige.WIDTH;
	public static Vektor cam = new Vektor(0, 0, 0);

	public static Vektor multMat(Vektor i, Matrix m) {
		Vektor a = new Vektor();
		float x = (float) (i.x * m.mat[0][0] + i.y * m.mat[1][0] + i.z * m.mat[2][0] + m.mat[3][0]);
		float y = (float) (i.x * m.mat[0][1] + i.y * m.mat[1][1] + i.z * m.mat[2][1] + m.mat[3][1]);
		float z = (float) (i.x * m.mat[0][2] + i.y * m.mat[1][2] + i.z * m.mat[2][2] + m.mat[3][2]);
		float w = (float) (i.x * m.mat[0][3] + i.y * m.mat[1][3] + i.z * m.mat[2][3] + m.mat[3][3]);
		a.setX(x);
		a.setY(y);
		a.setZ(z);
		a.w = w;
		return a;
	}

	public static void initialisiereProjMatrix(float w, float h, float near, float far, float fov) {

		float invTan = (float) (1 / Math.tan(Math.toRadians(0.5f * fov)));

		projectionMatrix.mat[0][0] = (h / w) * invTan;
		projectionMatrix.mat[1][1] = invTan;
		projectionMatrix.mat[2][2] = far / (far - near);
		projectionMatrix.mat[3][2] = (-far * near) / (far - near);
		projectionMatrix.mat[2][3] = 1.0f;
		projectionMatrix.mat[3][3] = 0.0f;
	}

	public static Vektor[] rotateAxisX(Vektor[] p, double Grad) {
		Vektor[] rotPunkte = new Vektor[3];

		xRotMatrix.mat[0][0] = 1.0f;
		xRotMatrix.mat[1][1] = (float) Math.cos(Grad);
		xRotMatrix.mat[1][2] = (float) Math.sin(Grad);
		xRotMatrix.mat[2][1] = (float) -Math.sin(Grad);
		xRotMatrix.mat[2][2] = (float) Math.cos(Grad);
		xRotMatrix.mat[3][3] = 1.0f;

		rotPunkte[0] = Matrix.multMat(p[0], xRotMatrix);
		rotPunkte[1] = Matrix.multMat(p[1], xRotMatrix);
		rotPunkte[2] = Matrix.multMat(p[2], xRotMatrix);

		return rotPunkte;
	}

	public static Vektor[] rotateAxisY(Vektor[] p, double Grad) {

		Vektor[] rotPunkte = new Vektor[3];
		yRotMatrix.mat[0][0] = (float) Math.cos(Grad);
		yRotMatrix.mat[0][2] = (float) Math.sin(Grad);
		yRotMatrix.mat[2][0] = (float) -Math.sin(Grad);
		yRotMatrix.mat[1][1] = 1.0f;
		yRotMatrix.mat[2][2] = (float) Math.cos(Grad);
		yRotMatrix.mat[3][3] = 1.0f;

		rotPunkte[0] = Matrix.multMat(p[0], yRotMatrix);
		rotPunkte[1] = Matrix.multMat(p[1], yRotMatrix);
		rotPunkte[2] = Matrix.multMat(p[2], yRotMatrix);
		return rotPunkte;
	}

	public static Vektor[] rotateAxisZ(Vektor[] p, double Grad) {
		Vektor[] rotPunkte = new Vektor[3];

		zRotMatrix.mat[0][0] = (float) Math.cos(Grad);
		zRotMatrix.mat[0][1] = (float) Math.sin(Grad);
		zRotMatrix.mat[1][0] = (float) -Math.sin(Grad);
		zRotMatrix.mat[1][1] = (float) Math.cos(Grad);
		zRotMatrix.mat[2][2] = 1.0f;
		zRotMatrix.mat[3][3] = 1.0f;

		rotPunkte[0] = Matrix.multMat(p[0], zRotMatrix);
		rotPunkte[1] = Matrix.multMat(p[1], zRotMatrix);
		rotPunkte[2] = Matrix.multMat(p[2], zRotMatrix);

		return rotPunkte;
	}
	public static void richteKameraMatrix(Vektor Pos, Vektor dir, Vektor orthDir) {
		Matrix richte = new Matrix();
	
		Vektor neuDir = Vektor.sub(dir, Pos);
	}
		public static Vektor[] aender(Vektor[] p, float x, float y, float z) {
	
		Vektor[] transl = new Vektor[3];

		aender.mat[0][0] = 1.0f;
		aender.mat[1][1] = 1.0f;
		aender.mat[2][2] = 1.0f;
		aender.mat[3][3] = 1.0f;
		aender.mat[3][0] = (float) x;
		aender.mat[3][1] = (float) y;
		aender.mat[3][2] = (float) z;

		transl[0] = Matrix.multMat(p[0], aender);
		transl[1] = Matrix.multMat(p[1], aender);
		transl[2] = Matrix.multMat(p[2], aender);

		return transl; 
	}

	public static Matrix matrixMult(Matrix m1, Matrix m2) {
		Matrix m3 = new Matrix();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				m3.mat[j][i] = m1.mat[j][0] * m2.mat[0][i] + m1.mat[j][1] * m2.mat[1][i] + m1.mat[j][2] * m2.mat[2][i] + m1.mat[j][3] * m2.mat[3][i];
			}
		}
		return m3;
	}

}