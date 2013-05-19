package sel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import vectorMath.VectorMath;
import matrizMath.MatrizMath;

public class Sel {

	private int dim;

	private MatrizMath m;

	private VectorMath x, b;

	private Double error;

	final static public double EPSILON = 1 * Math.pow(10, -12);

	public Sel(String path) {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File("src/sel/" + path + ".in");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;

			// Leo dimension del sistema
			if ((linea = br.readLine()) != null) {

				dim = Integer.parseInt(linea);

				// Se crea el vector de resultados "b"
				this.b = new VectorMath(dim);

				// Leo valores y los asigno al vector
				int cont = 0;
				while (cont < dim && (linea = br.readLine()) != null) {
					b.agregarValor(cont, Double.parseDouble(linea));
					cont++;
				}

				// Se crea la Matriz M
				this.m = new MatrizMath(dim, dim);

				// Se llena la matriz con los valores del archivo de entrada

				while ((linea = br.readLine()) != null) {

					String[] valores = linea.split(" ");

					m.setValor(Integer.parseInt(valores[0]),
							Integer.parseInt(valores[1]),
							Double.parseDouble(valores[2]));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void resolver() {

		this.error = EPSILON;
		
		// calculo de la inversa de M
		MatrizMath Minversa = this.m.inversa();
		if (Minversa != null) {

			// Se calcula la matriz identidad I'
			MatrizMath Iprima = this.m.producto(Minversa);

			this.error = calcularErrorSolucion(this.m.identidad(), Iprima).doubleValue();
			if ( this.error >= EPSILON) {

				return;
			}

			// Se calcula el vector X'
			VectorMath Xprima = Minversa.producto(b);

			// Se calcula el vector B'
			VectorMath Bprima = m.producto(Xprima);

			// Se calcula el error || B - B' ||2
			this.error = calcularErrorSolucion(this.b, Bprima).doubleValue();

			// El resultado X se le asigna al calculado X'
			this.x = Xprima;
		}
	}

	// Metodo para calcular el error entre dos matrices
	private Double calcularErrorSolucion(MatrizMath m1, MatrizMath m2) {

		MatrizMath restaMatrices = m1.resta(m2);
		return restaMatrices.normaDos();

	}

	// Metodo para calcular el error entre dos vectores
	private Double calcularErrorSolucion(VectorMath v1, VectorMath v2) {

		VectorMath restaVectores = v1.resta(v2);
		return restaVectores.normaDos();

	}

	// Metodo que muestra el resultado
	private void mostrarResultado() {
		if (x == null)
			System.out.println("No se encontro una solucion");
		else {
			Double[] resultado = x.getVector();
			System.out.println("Vector de Resultados:");
			for (int i = 0; i < dim; i++) {

				System.out.println(resultado[i]);
			}
		}
	}

	/*
	 * Testea si el error es muy grande
	 */
	public boolean test() {

		if (this.error >= EPSILON) {

			return false;
		} else {

			this.mostrarResultado();
			return true;
		}
	}

	public static void main(String[] args) {

		Sel s = new Sel("SEL2");
		s.resolver();		
		s.test();

	}
}
