package matrizMath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import vectorMath.VectorMath;

public class MatrizMath {

	private Double[][] matriz;
	private int filas;
	private int columnas;

	/*
	 * Funcion para definir si una matriz es cuadrada
	 */
	private boolean cuadrada() {

		return (this.columnas == this.filas);
	}

	/*
	 * Constructor privado, solo usado para mostrar resultados
	 */
	private MatrizMath(int y, int x) {

		matriz = new Double[y][x];
		filas = y;
		columnas = x;
	}

	/*
	 * Constructor que toma un archivo de input
	 */
	public MatrizMath(String cad) {

		String linea;

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {

			archivo = new File("src/matrizMath/" + cad);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			if ((linea = br.readLine()) != null) {

				String parser[] = linea.split(" ");
				this.setFilas(Integer.parseInt(parser[0]));
				this.setColumnas(Integer.parseInt(parser[1]));

			} else {

				System.out
						.println("ERROR: Se tienen más datos de lo establecido.");
				return;
			}

			matriz = new Double[this.getFilas()][this.getColumnas()];

			for (int f = 0; f < getFilas(); f++) {
				for (int c = 0; c < getColumnas(); c++) {

					if ((linea = br.readLine()) != null) {

						matriz[f][c] = Double.parseDouble(linea);

					} else {

						System.out
								.println("ERROR: Se tienen más datos de lo establecido.");
						return;
					}

				}
			}

			if ((linea = br.readLine()) != null) {

				System.out
						.println("ERROR: Se tienen más datos de lo establecido.");
				this.matriz = null;
				this.setFilas(0);
				this.setColumnas(0);

				return;
			}

		} catch (IOException e) {

			e.printStackTrace();
			return;

		} catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("ERROR: Primera linea.");
			return;

		} finally {

			if (fr != null) {

				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/*
	 * Suma de matrices
	 */
	public MatrizMath suma(MatrizMath m) {

		if (this.getFilas() != m.getFilas()
				|| this.getColumnas() != m.getColumnas()) {

			return null;
		}

		MatrizMath resultado = new MatrizMath(this.getFilas(),
				this.getColumnas());

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++) {

				resultado.matriz[y][x] = this.matriz[y][x] + m.matriz[y][x];

			}
		}

		return resultado;
	}

	/*
	 * Resta de matrices
	 */
	public MatrizMath resta(MatrizMath m) {

		if (this.getFilas() != m.getFilas()
				|| this.getColumnas() != m.getColumnas()) {

			return null;
		}

		MatrizMath resultado = new MatrizMath(this.getFilas(),
				this.getColumnas());

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++) {

				resultado.matriz[y][x] = this.matriz[y][x] - m.matriz[y][x];

			}
		}

		return resultado;
	}

	/*
	 * Se muestra la matriz.
	 */
	public String toString() {

		StringBuffer cadena = new StringBuffer();

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++) {

				cadena.append(matriz[y][x]);
				cadena.append(" ");
			}
			cadena.append("\n");
		}

		return cadena.toString();
	}

	/*
	 * Override de la funcion equals.
	 */
	public boolean equals(MatrizMath m) {

		if (this.getFilas() != m.getFilas()
				|| this.getColumnas() != m.getColumnas()) {

			return false;
		}

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++) {

				if (!this.matriz[y][x].equals(m.matriz[y][x])) {

					return false;
				}

			}
		}

		return true;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public int getFilas() {
		return filas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public int getColumnas() {
		return columnas;
	}

	public Double getValor(int f, int c) {

		return this.matriz[f][c];
	}

	/*
	 * Producto de una matriz por un float
	 */
	public MatrizMath producto(Float valor) {

		MatrizMath resultado = new MatrizMath(this.filas, this.columnas);

		for (int f = 0; f < this.filas; f++) {
			for (int c = 0; c < this.columnas; c++) {

				resultado.matriz[f][c] = this.matriz[f][c] * valor;
			}
		}

		return resultado;
	}

	/*
	 * Producto de una matriz por un vector.
	 */
	public MatrizMath producto(VectorMath v) {

		if (v.getVector().length != this.columnas) {

			return null;
		}

		MatrizMath resultado = new MatrizMath(this.filas, 1);

		for (int f = 0; f < this.filas; f++) {

			resultado.matriz[f][0] = 0.0;

		}

		for (int f = 0; f < this.filas; f++) {

			for (int c = 0; c < this.columnas; c++) {

				resultado.matriz[f][0] += this.matriz[f][c] * v.getVector()[c];

			}
		}

		return resultado;

	}

	/*
	 * Producto entre dos matrices.
	 */
	public MatrizMath producto(MatrizMath m) {

		if (this.columnas != m.filas) {

			return null;
		}

		MatrizMath resultado = new MatrizMath(this.filas, m.columnas);

		for (int f = 0; f < resultado.filas; f++) {
			for (int c = 0; c < resultado.columnas; c++) {

				resultado.matriz[f][c] = 0.0;
			}
		}

		for (int f = 0; f < resultado.filas; f++) {
			for (int c = 0; c < resultado.columnas; c++) {
				for (int k = 0; k < this.columnas; k++) {

					resultado.matriz[f][c] += this.matriz[f][k]
							* m.matriz[k][c];
				}
			}
		}

		return resultado;
	}

	/*
	 * Norma Uno de una matriz
	 */
	public Double normaUno() {

		Double[] resultado = new Double[this.columnas];

		for (int c = 0; c < this.columnas; c++) {

			resultado[c] = 0.0;

			for (int f = 0; f < this.filas; f++) {

				resultado[c] += this.matriz[f][c];
			}
		}

		Double aux = resultado[0];
		for (int c = 0; c < this.columnas; c++) {

			if (aux < resultado[c]) {

				aux = resultado[c];
			}
		}

		return aux;
	}

	/*
	 * Norma Infinito de un vector.
	 */
	public Double normaInfinito() {

		Double[] resultado = new Double[this.filas];

		for (int f = 0; f < this.filas; f++) {

			resultado[f] = 0.0;

			for (int c = 0; c < this.columnas; c++) {

				resultado[f] += this.matriz[f][c];
			}
		}

		Double aux = resultado[0];
		for (int f = 0; f < this.filas; f++) {

			if (aux < resultado[f]) {

				aux = resultado[f];
			}
		}

		return aux;
	}

	public Double normaDos() {
		// TODO

		return null;
	}

	public MatrizMath inversa(){

		return this.producto((float) -1.0);
	}

	/*
	 * Determinante de una matriz.
	 */
	public Double determinante() {

		// TODO

		// if ( ! this.cuadrada()){

		return null;
		// }
		//
		// Double resultado = new Double(0.0);
		//
		// return resultado;
	}

	public static void main(String[] args) {

		MatrizMath m1 = new MatrizMath("matriz1.in");
		MatrizMath m2 = new MatrizMath("matriz2.in");
		VectorMath v1 = new VectorMath("vec1.in");

		System.out.println(m1.producto(new Float(-1.0)));

	}
}
