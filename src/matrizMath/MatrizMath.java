package matrizMath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import sel.Sel;
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

	// inicializa en 0 una matriz

	public void inicializa() {
		for (int fila = 0; fila < this.filas; fila++) {
			for (int columna = 0; columna < this.columnas; columna++)
				this.matriz[fila][columna] = (double) 0;
		}
	}

	// genera matriz aleatoria

	public void generaMatrizAleatoria() {
		Random random = new Random();
		// int dim = random.nextInt(10);

		// MatrizMath matrizPrueba = new MatrizMath(dim,dim);
		this.inicializa();

		for (int f = 0; f < this.columnas; f++)
			for (int c = 0; c < this.columnas; c++)
				this.setValor(f, c, random.nextInt(30)*random.nextDouble());

	}

	// crea matriz identidad

	public MatrizMath identidad() {
		if (!this.cuadrada())
			return null;
		MatrizMath identidad = new MatrizMath(this.filas, this.columnas);
		identidad.inicializa();
		for (int filas = 0; filas < this.filas; filas++)
			identidad.matriz[filas][filas] = 1.0;

		return identidad;

	}

	private Double[] productoLinea(Double d, int linea) {
		Double[] resultado = new Double[this.columnas];
		for (int i = 0; i < this.columnas; i++)
			resultado[i] = this.matriz[linea][i] * d;
		return resultado;
	}

	private Double[] sumaLinea(Double[] d, int linea) {
		Double[] resultado = new Double[this.columnas];
		for (int i = 0; i < this.columnas; i++)
			resultado[i] = this.matriz[linea][i] + d[i];
		return resultado;

	}

	private Double[] restaLinea(Double[] d, int linea) {
		Double[] resultado = new Double[this.columnas];
		for (int i = 0; i < this.columnas; i++)
			resultado[i] = this.matriz[linea][i] - d[i];
		return resultado;

	}

	private void inviertoFilas(int fila1, int fila2) {
		Double[] l1;
		Double[] l2;
		l1 = this.matriz[fila1];
		l2 = this.matriz[fila2];

		this.matriz[fila1] = l2;
		this.matriz[fila2] = l1;
	}

	private boolean comparoFilasMatrizConFilasIdentidad(Double[] a, Double[] b,
			int fi) {
		int ind = 0;
		// boolean es_igual = true;

		while (ind < a.length && comparaValores(a[ind], b[ind]))
			// Double.compare(a[ind], b[ind]) == 0)
			ind++;

		return ind == a.length;

	}

	private boolean comparoFilasMatrizConFilasTriangular(Double[] a, int fi) {
		int ind = 0;

		while (ind <= fi && a[ind] == 0)
			ind++;

		return ind == fi;

	}

	public static boolean comparaValores(double v1, double v2) {
		return (Math.abs(v1 - v2)) < Sel.EPSILON;
	}

	public static boolean comparaDeterminantes(double v1, double v2) {
		return (Math.abs(v1 - v2)) < 1;
	}

	// matriz inversa
	public MatrizMath inversa() {
		

		if (!this.cuadrada()) {
			System.out.println("La matriz no es cuadrada");
			return null;
		}

		// busco la matriz identidad
		MatrizMath identidad = this.identidad();

		Double[][] aux = new Double[this.filas][this.columnas];
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				aux[i][j] = matriz[i][j];
			}
		}
		MatrizMath MatIdentidad = this.identidad();
		// comparo cada fila con cada fila de la matriz identidad y si es igual
		// las acomodo
		for (int fm = 0; fm < this.filas; fm++) {
			for (int fi = 0; fi < this.filas; fi++) {
				if (comparoFilasMatrizConFilasIdentidad(this.matriz[fm],
						MatIdentidad.matriz[fi], fi)) {
					this.inviertoFilas(fm, fi);
					identidad.inviertoFilas(fm, fi);
				}
			}
		}

		int filapivot = 0;
		for (int c = 0; c < this.columnas; c++) {
			int fila = 0;

			double pivot = this.matriz[filapivot][c];
			// si el pivot es 0 tengo que calcular el 1 desde alguna otra fila
			if (comparaValores(pivot, 0)) {
				int linea = filapivot;

				while (linea < this.filas
						&& comparaValores(this.matriz[linea][c], 0))
					linea++;
				if (linea < this.filas) {

					double valor = this.matriz[linea][c];

					Double[] lineaASumarM = this
							.productoLinea(1 / valor, linea);
					Double[] lineaASumarI = identidad.productoLinea(1 / valor,
							linea);

					this.matriz[filapivot] = this.sumaLinea(lineaASumarM,
							filapivot);
					identidad.matriz[filapivot] = identidad.sumaLinea(
							lineaASumarI, filapivot);
				} else {
					System.out.println("La matriz no se puede invertir");
					return null;
				}

			} else {
				if (!comparaValores(pivot, 1)) {
					// obtengo el numero a dividir para que quede uno
					double operador = 1 / pivot;

					this.matriz[filapivot] = this.productoLinea(operador,
							filapivot);
					identidad.matriz[filapivot] = identidad.productoLinea(
							operador, filapivot);

				}
			}

			while (fila < this.filas) {
				// ahora que tengo el pivot uno hago que el resto de la columna
				// quede en 0

				if (fila != filapivot) {
					double valor = this.matriz[fila][c];
					if (!comparaValores(valor, 0)) // si es cero no hago nada
					{
						// multiplico el 1 del pivot por el numero que necesite
						// y resto las filas
						Double[] lineaARestarM = this.productoLinea(valor,
								filapivot);
						Double[] lineaARestarI = identidad.productoLinea(valor,
								filapivot);

						this.matriz[fila] = this
								.restaLinea(lineaARestarM, fila);
						identidad.matriz[fila] = identidad.restaLinea(
								lineaARestarI, fila);

						// comparo cada fila con cada fila de la matriz
						// identidad y si es igual
						// las acomodo

						boolean inverti = false;
						// for (int fm = 0; fm < this.filas; fm++) {
						for (int fi = 0; fi < this.filas; fi++) {
							if (fi != fila
									&& comparoFilasMatrizConFilasIdentidad(
											this.matriz[fila],
											MatIdentidad.matriz[fi], fi)) {
								this.inviertoFilas(fila, fi);
								identidad.inviertoFilas(fila, fi);
								inverti = true;
							}
						}
						// }
						if (inverti)
							fila--;
					}
				}
				fila++;
			}
			filapivot++;
		}

		this.matriz = new Double[this.filas][this.columnas];
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {

				matriz[i][j] = aux[i][j];
			}
		}
		return identidad;
	}

	//

	/*
	 * Constructor privado, solo usado para mostrar resultados
	 */
	public MatrizMath(int filas, int columnas) {

		matriz = new Double[filas][columnas];
		this.filas = filas;
		this.columnas = columnas;
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
						.println("ERROR: Se tienen m�s datos de lo establecido.");
				return;
			}

			matriz = new Double[this.getFilas()][this.getColumnas()];

			for (int f = 0; f < getFilas(); f++) {
				for (int c = 0; c < getColumnas(); c++) {

					if ((linea = br.readLine()) != null) {

						matriz[f][c] = Double.parseDouble(linea);

					} else {

						System.out
								.println("ERROR: Se tienen mas datos de lo establecido.");
						return;
					}

				}
			}

			if ((linea = br.readLine()) != null) {

				System.out
						.println("ERROR: Se tienen m�s datos de lo establecido.");
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

	public void setValor(int fila, int columna, Double valor) {

		if (fila >= 0 && columna >= 0 && fila < this.filas
				&& columna < this.columnas) {

			this.matriz[fila][columna] = new Double(valor);
		}

	}

	/*
	 * Suma de matrices
	 */
	public MatrizMath suma(MatrizMath m) {

		if (this.getFilas() != m.getFilas()
				|| this.getColumnas() != m.getColumnas())
			return null;

		MatrizMath resultado = new MatrizMath(this.getFilas(),
				this.getColumnas());

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++)
				resultado.matriz[y][x] = this.matriz[y][x] + m.matriz[y][x];
		}

		return resultado;
	}

	/*
	 * Resta de matrices
	 */
	public MatrizMath resta(MatrizMath m) {

		if (this.getFilas() != m.getFilas()
				|| this.getColumnas() != m.getColumnas())
			return null;

		MatrizMath resultado = new MatrizMath(this.getFilas(),
				this.getColumnas());

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++)
				resultado.matriz[y][x] = this.matriz[y][x] - m.matriz[y][x];
		}

		return resultado;
	}

	/*
	 * Se muestra la matriz.
	 */
	@Override
	public String toString() {

		StringBuffer cadena = new StringBuffer();

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++) {

				cadena.append(String.format("%10.3f", matriz[y][x]));

				cadena.append("\t\t");
			}
			cadena.append("\n");
		}
		return cadena.toString();
	}

	/*
	 * Override de la funcion equals.
	 */
	public boolean equals(MatrizMath m) {

		double error = Math.abs(this.resta(m).normaDos());
		System.out.printf ("Error: \n%.30f\n%.30f\n%.30f\n%.30f",this.identidad().normaDos(), m.normaDos(),error,this.identidad().normaDos()- m.normaDos());
		return error < Sel.EPSILON;

		// if (this.getFilas() != m.getFilas()
		// || this.getColumnas() != m.getColumnas())
		// return false;
		//
		// for (int y = 0; y < this.getFilas(); y++) {
		// for (int x = 0; x < this.getColumnas(); x++) {
		// if (!(Math.abs(this.matriz[y][x] - (m.matriz[y][x]))< Sel.EPSILON))
		// return false;
		// }
		// }
		//
		// return true;
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
			for (int c = 0; c < this.columnas; c++)
				resultado.matriz[f][c] = this.matriz[f][c] * valor;
		}

		return resultado;
	}

	/*
	 * Producto de una matriz por un vector.
	 */
	public VectorMath producto(VectorMath v) {

		if (v.getVector().length != this.columnas) {

			return null;
		}

		MatrizMath resultado = new MatrizMath(this.filas, 1);

		resultado.inicializa();

		for (int f = 0; f < this.filas; f++) {

			for (int c = 0; c < this.columnas; c++)
				resultado.matriz[f][0] += this.matriz[f][c] * v.getVector()[c];
		}

		VectorMath vectorResultado = new VectorMath(this.filas);

		for (int f = 0; f < this.filas; f++) {
			vectorResultado.agregarValor(f, resultado.matriz[f][0]);
		}

		return vectorResultado;

	}

	/*
	 * Producto entre dos matrices.
	 */
	public MatrizMath producto(MatrizMath m) {

		if (this.columnas != m.filas)
			return null;

		MatrizMath resultado = new MatrizMath(this.filas, m.columnas);

		resultado.inicializa();

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.filas; j++) {
				for (int k = 0; k < this.filas; k++) {
					resultado.matriz[i][j] += this.matriz[i][k]
							* m.matriz[k][j];
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

				resultado[c] += Math.abs(this.matriz[f][c]);
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
	 * Norma Infinito de una matriz.
	 */
	public Double normaInfinito() {

		Double[] resultado = new Double[this.filas];

		for (int f = 0; f < this.filas; f++) {

			resultado[f] = 0.0;

			for (int c = 0; c < this.columnas; c++) {

				resultado[f] += Math.abs(this.matriz[f][c]);
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

//	public MatrizMath traspuesta() {
//
//		MatrizMath resultado = new MatrizMath(this.filas, this.columnas);
//
//		for (int i = 0; i < this.filas; i++) {
//			for (int j = 0; j < this.columnas; j++) {
//
//				resultado.matriz[j][i] = this.matriz[i][j];
//
//			}
//
//		}
//
//		return resultado;
//	}

	/*
	 * Norma dos de una matriz:
	 * 
	 * Su formula es para matrices simetricas 
	 * ||A||_2 = max{ | Li(A) | }
	 * 
	 * donde Li(A) = son los autovalores de A
	 */
<<<<<<< HEAD
	public double normaDos() {

		//MatrizMath productoIntermedio = this.producto(this.traspuesta());
		
 		double valorMayor = this.matriz[0][0];
 		double valorMenor = this.matriz[0][0];
 		
=======
	public Double normaDos() {

		Double valorMayor = this.matriz[0][0];
>>>>>>> branch 'master' of https://github.com/sebaim/TP2.git
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {

<<<<<<< HEAD
				if (this.matriz[i][j] > valorMayor)
					valorMayor = this.matriz[i][j];
				if (this.matriz[i][j] < valorMenor)
					valorMenor = this.matriz[i][j];
					
=======
				if (this.matriz[i][j] > valorMayor) {

					valorMayor = this.matriz[i][j];
				}
>>>>>>> branch 'master' of https://github.com/sebaim/TP2.git
			}

		}

<<<<<<< HEAD
		//valorMayor = Math.abs(valorMayor);
		return Math.pow(valorMayor-valorMenor,2);
=======
		return Math.abs(valorMayor);
>>>>>>> branch 'master' of https://github.com/sebaim/TP2.git

	}

	// adjunto
	MatrizMath adjunto(int fila, int col) {
		MatrizMath adjunto = new MatrizMath(this.filas - 1, this.columnas - 1);
		// adjunto.inicializa();
		int fa = 0;
		int ca = 0;

		for (int f = 0; f < this.filas; f++) {
			ca = 0;
			if (f != fila) {
				for (int c = 0; c < this.columnas; c++) {
					if (c != col) {
						adjunto.matriz[fa][ca] = this.matriz[f][c];
						ca++;
					}
				}
				fa++;
			}
		}
		return adjunto;
	}

	/*
	 * Determinante de una matriz.
	 */
	public Double determinanteObsoleto() {

		if (!this.cuadrada())
			return null;

		if (this.filas == 2)
			return (this.matriz[0][0] * this.matriz[1][1] - this.matriz[0][1]
					* this.matriz[1][0]);

		double determinante = 0.0;

		for (int c = 0; c < this.columnas; c++) {
			determinante += this.matriz[0][c]
					* this.adjunto(0, c).determinanteObsoleto()
					* (c % 2 == 0 ? 1 : -1);
		}
		return determinante;

	}

	// determinante por triangulacion
	public Double determinante2() {
		double determinante = 1.0;
		double multiplico = 1;
		double pivot = 0;
		int filaPivot = 0;
		int filaMatriz = 0;

		MatrizMath aux = new MatrizMath(this.filas, this.columnas);
		aux.matriz = this.matriz.clone();

		if (!this.cuadrada())
			return null;

		for (int fm = 0; fm < this.filas; fm++) {
			for (int ft = 0; ft < this.filas; ft++) {
				if (ft != 0
						&& ft != fm
						&& comparoFilasMatrizConFilasTriangular(
								this.matriz[fm], ft)) {
					this.inviertoFilas(fm, ft);
					multiplico *= -1;
				}
			}

		}

		for (int c = 0; c <= this.columnas; c++) {
			filaMatriz = filaPivot;
			while (filaMatriz < this.filas) {
				if (filaPivot == filaMatriz) // obtengo el pivot y lo dejo en 1
				{
					pivot = this.matriz[filaPivot][c];
					// valorACancelar = this.matriz[filaMatriz][c];
					//
					// if (!comparaValores(pivot,valorACancelar))
					// {
					// double operador = 1 / pivot;
					// this.matriz[filaPivot] =
					// this.productoLinea(operador,filaPivot);
					// multiplico *=operador;
					// }
				} else {
					if (comparaValores(pivot, 0)) {
						int linea = filaPivot;
						while (linea < this.filas
								&& comparaValores(this.matriz[linea][c], 0))
							linea++;
						if (linea < this.filas) {
							double valor = this.matriz[linea][c];
							Double[] lineaASumarM = this.productoLinea(
									1 / valor, linea);
							this.matriz[filaPivot] = this.sumaLinea(
									lineaASumarM, filaPivot);
						}

						pivot = this.matriz[filaPivot][c];
					}
					// double valor = this.matriz[filaMatriz][c];
					double valorACancelar = this.matriz[filaMatriz][c];
					if (!comparaValores(valorACancelar, 0)) // si es cero no
															// hago nada
					{
						// multiplico el 1 del pivot por el numero que necesite
						// y resto las filas
						Double[] lineaARestarM = this.productoLinea(
								valorACancelar / pivot, filaPivot);
						// multiplico *=(valorACancelar/pivot);
						this.matriz[filaMatriz] = this.restaLinea(
								lineaARestarM, filaMatriz);

					}
				}

				filaMatriz++;
			}
			filaPivot++;
		}

		for (int diag = 0; diag < this.columnas; diag++)
			determinante *= this.matriz[diag][diag];
		determinante *= multiplico;

		this.matriz = aux.matriz;

		return determinante;
	}

	public static void main(String[] args) {

		// ejemplo 1 (inversa)
		MatrizMath m1 = new MatrizMath("matriz1.in");
		MatrizMath mIdentidad = new MatrizMath(m1.filas, m1.columnas);
		
		 for (int i = 1; i < 23; i++) {
		 String archivo = String.format("matriz%d.in", i);
		// String archivo = String.format("matriz10.in");
		 System.out.println(archivo);
		
		 m1 = new MatrizMath(archivo);
		 mIdentidad = m1.identidad();
		 if (mIdentidad != null)
			 System.out.println(m1.identidad().normaDos());

		 System.out.println("Matriz:");
		 System.out.println(m1);
		 System.out.print("Determinante: ");
		 //Double determinante = m10.determinanteObsoleto();
		 Double det2 = m1.determinante2();
		//
		 
		 System.out.println(det2);
		 System.out.print("Inversa: ");
		
		 MatrizMath inversa = m1.inversa();
		 System.out.println(inversa);
		 if (inversa != null) {
		
		 System.out.println("Matriz x Inversa: ");
		
		 System.out.println(m1.producto(inversa));
		
		 if (m1.producto(inversa).equals(m1.identidad()))
		 System.out.println("Son iguales");
		 else {
		 System.out.println("NO Son iguales");
		 break;
		 }
		 }
		 }

		// System.out.println(m4);

		// System.out.println(m6);
		// System.out.println(m6.adjunto(0, 0));
		// System.out.println(m6.adjunto(0, 1));
		// System.out.println(m6.adjunto(0, 2));

		// System.out.println(m7.inversa());
		// System.out.println(m4.identidad());
		// System.out.println(m4.inversa());

		// System.out.println(m4.producto(m4.inversa()));

		// System.out.println(m1.producto(new Float(-1.0)));

		// System.out.println(m6);
		// System.out.println(m6.identidad());
		// System.out.println(m6.inversa());

		// System.out.println(m6.producto(m6.inversa()));

		// System.out.println(m4);
		//System.out.println(m4.normaDos());
		
		// System.out.println(m4.traspuesta());
	}

}
