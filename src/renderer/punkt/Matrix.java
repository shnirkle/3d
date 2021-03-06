package renderer.punkt;

import renderer.Anzeige;

public class Matrix {
	public float[][] mat = new float[4][4];
	public static Matrix projectionMatrix = new Matrix();
	public static Matrix xRotMatrix = new Matrix();
	public static Matrix yRotMatrix = new Matrix();
	public static Matrix zRotMatrix = new Matrix();
	public static Matrix aender = new Matrix();
	public static float ar = (float) Anzeige.HEIGHT / Anzeige.WIDTH;
	public static Vektor cam = new Vektor(0, 0, 0);
	public static double yGrad = 0;

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

	//Debugmethode, um eine Matrix zu printen
	
	public void printMat() {
		System.out.println("----");
		for (int i = 0; i < 4; i++)
		{
			
				System.out.println(this.mat[i][0] + "/" +this.mat[i][1] + "/" +this.mat[i][2] + "/" +this.mat[i][3]);
			
		}
	}
	
	//Initialisiere die Projektionsmatrix, welche wir dann tats�chlich sehen (Man k�nnte sagen, dass sie unser Bildschirm ist)

	public static void initialisiereProjMatrix(float w, float h, float near, float far, float fov) {

		float invTan = (float) (1.0f / Math.tan(Math.toRadians(0.5f * fov)));

		projectionMatrix.mat[0][0] = (h / w) * invTan;
		projectionMatrix.mat[1][1] = invTan;
		projectionMatrix.mat[2][2] = far / (far - near);
		projectionMatrix.mat[3][2] = (-far * near) / (far - near);
		projectionMatrix.mat[2][3] = 1.0f;
		projectionMatrix.mat[3][3] = 0.0f;
		
	}
	
	//Methoden zum Rotieren an Achsen
	//RotMatrix steht dabei f�r Rotationsmatrixen

	public static Matrix rotateAxisX(double Grad) {
		xRotMatrix.matrixInitialisierung();

		xRotMatrix.mat[0][0] = 1.0f;
		xRotMatrix.mat[1][1] = (float) Math.cos(Grad);
		xRotMatrix.mat[1][2] = (float) Math.sin(Grad);
		xRotMatrix.mat[2][1] = (float) -Math.sin(Grad);
		xRotMatrix.mat[2][2] = (float) Math.cos(Grad);
		xRotMatrix.mat[3][3] = 1.0f;

		return xRotMatrix;
	}

	public static Matrix rotateAxisY(double Grad) {
		yRotMatrix.matrixInitialisierung();
		yRotMatrix.mat[0][0] = (float) Math.cos(Grad);
		yRotMatrix.mat[0][2] = (float) Math.sin(Grad);
		yRotMatrix.mat[2][0] = (float) -Math.sin(Grad);
		yRotMatrix.mat[1][1] = 1.0f;
		yRotMatrix.mat[2][2] = (float) Math.cos(Grad);
		yRotMatrix.mat[3][3] = 1.0f;

		return yRotMatrix;
	}

	public static Matrix rotateAxisZ(double Grad) {
		zRotMatrix.matrixInitialisierung();

		zRotMatrix.mat[0][0] = (float) Math.cos(Grad);
		zRotMatrix.mat[0][1] = (float) Math.sin(Grad);
		zRotMatrix.mat[1][0] = (float) -Math.sin(Grad);
		zRotMatrix.mat[1][1] = (float) Math.cos(Grad);
		zRotMatrix.mat[2][2] = 1.0f;
		zRotMatrix.mat[3][3] = 1.0f;

		return zRotMatrix;
	}

	//Wohin soll die Kamera schauen? -> Matrix, damit Oben, Vorne und Rechts auch weiterhin Oben, Vorne und Rechts sind
	
public static Matrix richteKameraMatrix(Vektor blickrichtung, Vektor orthogonal, Vektor position) {
		
//		dir = new Vektor(0,0,1);
//		orthDir = new Vektor(0,1,0);
		
		//Neues Vorne
		Vektor neuVorne = Vektor.sub(blickrichtung, position);
		neuVorne.normVektor();

		// Neues Oben
		Vektor a = Vektor.multVektor_Faktor(neuVorne, Vektor.skalarprodukt(orthogonal, neuVorne));
		Vektor neuOben = Vektor.sub(orthogonal, a);
		neuOben.normVektor();

		// Neues Rechts
		Vektor neuRechts = Vektor.kreuzprodukt(neuOben, neuVorne);
		
		// Translationsmatrix f�r unsere neuen Dimensionen konstruieren
		Matrix matrix = new Matrix();
		matrix.mat[0][0] = (float) neuRechts.x;
		matrix.mat[0][1] = (float) neuRechts.y;
		matrix.mat[0][2] = (float) neuRechts.z;
		matrix.mat[0][3] = 0.0f;
		matrix.mat[1][0] = (float) neuOben.x;
		matrix.mat[1][1] = (float) neuOben.y;
		matrix.mat[1][2] = (float) neuOben.z;
		matrix.mat[1][3] = 0.0f;
		matrix.mat[2][0] = (float) neuVorne.x;
		matrix.mat[2][1] = (float) neuVorne.y;
		matrix.mat[2][2] = (float) neuVorne.z;
		matrix.mat[2][3] = 0.0f;
		matrix.mat[3][0] = (float) position.x;
		matrix.mat[3][1] = (float) position.y;
		matrix.mat[3][2] = (float) position.z;
		matrix.mat[3][3] = 1.0f;
		return matrix;
	}

	public void matrixInitialisierung() {

		this.mat[0][0] = 1.0f;
		this.mat[1][1] = 1.0f;
		this.mat[2][2] = 1.0f;
		this.mat[3][3] = 1.0f;

	}

	//Matrixkoordinaten �ndern
	
		public static Matrix aenderKoordinaten(float x, float y, float z) {

			aender.mat[0][0] = 1.0f;
			aender.mat[1][1] = 1.0f;
			aender.mat[2][2] = 1.0f;
			aender.mat[3][3] = 1.0f;
			aender.mat[3][0] = (float) x;
			aender.mat[3][1] = (float) y;
			aender.mat[3][2] = (float) z;
			return aender;
		}

		//Multipliziere zwei Matrixen
		
		public static Matrix multMatrix_Matrix(Matrix m1, Matrix m2) {
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

		//Matrix invertieren -> wichtig f�r Kamera
		
		public static Matrix matrixInvertierung(Matrix m) { 
			Matrix matrix = new Matrix();
			matrix.mat[0][0] = m.mat[0][0];
			matrix.mat[0][1] = m.mat[1][0];
			matrix.mat[0][2] = m.mat[2][0];
			matrix.mat[0][3] = 0.0f;
			matrix.mat[1][0] = m.mat[0][1];
			matrix.mat[1][1] = m.mat[1][1];
			matrix.mat[1][2] = m.mat[2][1];
			matrix.mat[1][3] = 0.0f;
			matrix.mat[2][0] = m.mat[0][2];
			matrix.mat[2][1] = m.mat[1][2];
			matrix.mat[2][2] = m.mat[2][2];
			matrix.mat[2][3] = 0.0f;
			matrix.mat[3][0] = -(m.mat[3][0] * matrix.mat[0][0] + m.mat[3][1] * matrix.mat[1][0] + m.mat[3][2] * matrix.mat[2][0]);
			matrix.mat[3][1] = -(m.mat[3][0] * matrix.mat[0][1] + m.mat[3][1] * matrix.mat[1][1] + m.mat[3][2] * matrix.mat[2][1]);
			matrix.mat[3][2] = -(m.mat[3][0] * matrix.mat[0][2] + m.mat[3][1] * matrix.mat[1][2] + m.mat[3][2] * matrix.mat[2][2]);
			matrix.mat[3][3] = 1.0f;
			return matrix;

		}
}