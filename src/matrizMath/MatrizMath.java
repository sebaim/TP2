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
	
	public void generaMatrizAleatoria ()
	{
		Random random = new Random();
		//int dim = random.nextInt(10);
				
		//MatrizMath matrizPrueba = new MatrizMath(dim,dim);
		this.inicializa();
		
		for (int f=0; f< this.columnas; f++)
			for (int c=0; c < this.columnas ; c++)
				this.setValor(f, c, (double) random.nextInt(20)); 		
				
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

	private boolean comparoFilasMatrizConFilasIdentidad(Double[] a, Double[] b, int fi) {
		int ind = 0;
		// boolean es_igual = true;

		while ( ind < a.length && Double.compare(a[ind], b[ind]) == 0)
			ind++;

		return ind == a.length;

	}	 
	
	private boolean comparaValores (double v1, double v2)
	{
		return (Math.abs(v1-v2)) < Sel.EPSILON;
	}

	// matriz inversa
	public MatrizMath inversa() {
		// si el determinante es 0 la matriz no se puede invertir
		
		if (!this.cuadrada()) 
		{
			System.out.println("La matriz no es cuadrada");
			return null;
		}
		
		if (this.determinante() == 0) 
		{
			System.out.println("La matriz no se puede invertir, su determinante es 0 ");
			return null;
		}
		
		// busco la matriz identidad
		MatrizMath identidad = this.identidad();
		
		Double[][] aux = new Double[this.filas][ this.columnas];
		for(int i=0; i< this.filas; i++){
			for(int j=0; j< this.columnas; j++){				
				aux[i][j] = matriz[i][j];
			}
		}
		// comparo cada fila con cada fila de la matriz identidad y si es igual
		// las acomodo
		for (int fm = 0; fm < this.filas; fm++) {
			for (int fi = 0; fi < this.filas; fi++) {
				if (comparoFilasMatrizConFilasIdentidad(this.matriz[fm], this.identidad().matriz[fi],fi)) {
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
			if (comparaValores(pivot,0)) {
				int linea = filapivot;
				while (linea < this.filas && comparaValores(this.matriz[linea][c], 0))
					linea++;
				if (linea < this.filas) {

					double valor = this.matriz[linea][c];

					Double[] lineaASumarM = this.productoLinea(1 / valor, linea);
					Double[] lineaASumarI = identidad.productoLinea(1 / valor, linea);

					this.matriz[filapivot] = this.sumaLinea(lineaASumarM,filapivot);
					identidad.matriz[filapivot] = identidad.sumaLinea(lineaASumarI, filapivot);
				} else {
					System.out.println("La matriz no se puede invertir");
					return null;
				}

			} else {
				if (!comparaValores(pivot,1)) {
					// obtengo el numero a dividir para que quede uno
					double operador = 1 / pivot;

					this.matriz[filapivot] = this.productoLinea(operador,filapivot);
					identidad.matriz[filapivot] = identidad.productoLinea(operador, filapivot);

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
						Double[] lineaARestarM = this.productoLinea(valor,filapivot);
						Double[] lineaARestarI = identidad.productoLinea(valor,filapivot);

						this.matriz[fila] = this.restaLinea(lineaARestarM, fila);
						identidad.matriz[fila] = identidad.restaLinea(lineaARestarI, fila);
						
						// comparo cada fila con cada fila de la matriz identidad y si es igual
						// las acomodo
						boolean inverti = false;
						for (int fm = 0; fm < this.filas; fm++) {
							for (int fi = 0; fi < this.filas; fi++) {
								if (fi!= fm && comparoFilasMatrizConFilasIdentidad(this.matriz[fm], this.identidad().matriz[fi],fi)) {
									this.inviertoFilas(fm, fi);
									identidad.inviertoFilas(fm, fi);
									inverti = true;
								}
							}
						}
						if (inverti)
							fila--;
					}
				}
				fila++;
			}
			filapivot++;
		}
		
		System.out.println("Matriz original llevada a identidad:");
		System.out.println(this);

		this.matriz = new Double[this.filas][ this.columnas];
		for(int i=0; i< this.filas; i++){
			for(int j=0; j< this.columnas; j++){
				
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

		if (this.getFilas() != m.getFilas()
				|| this.getColumnas() != m.getColumnas())
			return false;

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++) {
				if (!(Math.abs(this.matriz[y][x] - (m.matriz[y][x]))< Sel.EPSILON))
					return false;
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

	public Double normaDos() {

		Double resultado = (double) 0;

		for (int c = 0; c < this.columnas; c++) {

			for (int f = 0; f < this.filas; f++) {

				resultado += Math.pow(this.matriz[f][c], 2);
			}
		}

		return Math.sqrt(resultado);
	}

	// adjunto
	MatrizMath adjunto(int fila, int col) {
		MatrizMath adjunto = new MatrizMath(this.filas - 1, this.columnas - 1);
		//adjunto.inicializa();
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
	public Double determinante() {

		if (!this.cuadrada())
			return null;
		
		if (this.filas == 2)
			return (this.matriz[0][0] * this.matriz[1][1] - this.matriz[0][1]
					* this.matriz[1][0]);

		double determinante = 0.0;

		for (int c = 0; c < this.columnas; c++) {
			determinante += this.matriz[0][c]* this.adjunto(0, c).determinante() * (c % 2 == 0 ? 1 : -1);
		}
		return determinante;

	}

	public static void main(String[] args) {

		// ejemplo 1 (inversa)
		MatrizMath m1 = new MatrizMath("matriz1.in");

		// ejemplo 2 (inversa)
		MatrizMath m2 = new MatrizMath("matriz2.in");

		// ejemplo 3 (inversa)
		MatrizMath m3 = new MatrizMath("matriz3.in");

		// ejemplo 4 caso 4 x 4
		MatrizMath m4 = new MatrizMath("matriz4.in");

		// ejemplo 5 caso que fallaba
		MatrizMath m5 = new MatrizMath("matriz5.in");

		// ejemplo 6 caso determinante en matriz 5x5
		MatrizMath m6 = new MatrizMath("matriz6.in");

		// ejemplo 7 caso no se puede resolver (det 0)
		MatrizMath m7 = new MatrizMath("matriz7.in");
		
		// ejemplo 8 matriz 4x3
		MatrizMath m8 = new MatrizMath("matriz8.in");
		
		// ejemplo 9 matriz caso falla
		MatrizMath m9 = new MatrizMath("matriz9.in");
		
		// ejemplo 10 matriz caso falla
		MatrizMath m10 = new MatrizMath("matriz17.in");

		// VectorMath v1 = new VectorMath("vector1.in");

		// System.out.println(m1);
		// System.out.println(m1.normaDos());
		// System.out.println(m3.identidad());
		
		for (int i = 1; i< 19 ; i++)
		{
			String archivo = String.format("matriz%d.in", i);
		//String archivo = String.format("matriz8.in");
			System.out.println(archivo);		
			
			m10 = new MatrizMath(archivo);
			System.out.println("Matriz:");
			System.out.println(m10);
			System.out.print("Determinante: ");
			Double determinante = m10.determinante();
			System.out.println(determinante);
			System.out.print("Inversa: ");
						
			MatrizMath inversa = m10.inversa();
			System.out.println(inversa);
			if (inversa != null)
			{
				
				System.out.println("Matriz x Inversa: ");
			
				System.out.println(m10.producto(inversa));			
			
				if (m10.producto(inversa).equals(m10.identidad()))
					System.out.println("Son iguales");
				else
					System.out.println("NO Son iguales");
			}
		}
		

		//System.out.println(m4);

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

	}	
	
}
