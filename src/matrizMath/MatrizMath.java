package matrizMath;

import java.math.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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
	
	//inicializa en 0 una matriz
	
	private void inicializa ()
	{
		for (int fila = 0; fila < this.filas; fila++)
		{
			for (int columna =0; columna < this.columnas ; columna++)
				this.matriz[fila][columna] = (double) 0;
		}
	}
	
	//crea matriz identidad
	
	private MatrizMath identidad ()
	{
		if (!this.cuadrada())
			return null;
		MatrizMath identidad = new MatrizMath(this.filas, this.columnas);
		identidad.inicializa();
		for (int filas = 0; filas < this.filas; filas ++)
			identidad.matriz[filas][filas]=1.0;
		
		return identidad;
				
	}
	
	private Double[] productoLinea (Double d, int linea)
	{
		Double[] resultado = new Double [this.columnas];
		for (int i=0; i < this.columnas ;i++)
			resultado [i] = this.matriz[linea][i]* d;
		return resultado;
	}
	
	private Double[] sumaLinea (Double[]d, int linea)
	{
		Double[] resultado = new Double [this.columnas];
		for (int i=0; i < this.columnas ;i++)
			resultado [i] = this.matriz[linea][i] + d[i];
		return resultado;
			
	}
	
	private Double[] restaLinea (Double[]d, int linea)
	{
		Double[] resultado = new Double [this.columnas];
		for (int i=0; i < this.columnas ;i++)
			resultado [i] = this.matriz[linea][i] - d[i];
		return resultado;
			
	}
	
	//matriz inversa
	private MatrizMath inversa ()
	{
		//busco la matriz identidad
		MatrizMath identidad = this.identidad();	
		
		int filapivot = 0;
		for (int c = 0; c < this.columnas ; c++)
		{			
			int fila = 0;			
			
			double pivot = this.matriz[filapivot][c];
			//si el pivot es 0 tengo que calcular el 1 desde alguna otra fila
			if (pivot == 0)
			{
				int linea =0;
				while (this.matriz[linea][c] == 0)
					linea++;
				
				double valor =this.matriz[linea][c];
				
				Double []lineaASumar = this.productoLinea(1/valor, linea);
				
				this.matriz[filapivot] = this.sumaLinea(lineaASumar, filapivot);
				identidad.matriz[filapivot] = identidad.sumaLinea(lineaASumar, filapivot);
					
					
			}
			else
			{
				if (pivot != 1)
				{
					// obtengo el numero a dividir para que quede uno
					double operador = 1/pivot;
			
					this.matriz[filapivot] = this.productoLinea(operador,filapivot);
					identidad.matriz [filapivot] = identidad.productoLinea(operador,filapivot);
			
				}
			}		
			
			
			while (fila < this.filas)			
			{				
				//ahora que tengo el pivot uno hago que el resto de la columna quede en 0
				if (fila != filapivot)
				{
					double valor = this.matriz[fila][c];
					if (valor != 0) //si es cero no hago nada
					{
						//multiplico el 1 del pivot por el numero que necesite y resto las filas					
						Double []lineaARestarM = this.productoLinea(valor, filapivot);
						Double []lineaARestarI = identidad.productoLinea(valor, filapivot);
					
						this.matriz[fila] = this.restaLinea(lineaARestarM,fila);
						identidad.matriz[fila] = identidad.restaLinea(lineaARestarI, fila);						
					}
				}
				fila++;
			}
			filapivot++;
		}
		
		return identidad;
				
		
	}
	
	//
	
	/*
	 * Constructor privado, solo usado para mostrar resultados
	 */
	private MatrizMath(int filas, int columnas) {

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

		if (this.getFilas() != m.getFilas()	|| this.getColumnas() != m.getColumnas()) 
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

		if (this.getFilas() != m.getFilas()	|| this.getColumnas() != m.getColumnas())
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

				cadena.append(String.format("%10s", matriz[y][x]));
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

		if (this.getFilas() != m.getFilas()	|| this.getColumnas() != m.getColumnas()) 
			return false;		

		for (int y = 0; y < this.getFilas(); y++) {
			for (int x = 0; x < this.getColumnas(); x++) {
				if (!this.matriz[y][x].equals(m.matriz[y][x]))
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
	public MatrizMath producto(VectorMath v) {

		if (v.getVector().length != this.columnas) {

			return null;
		}

		MatrizMath resultado = new MatrizMath(this.filas, 1);

		resultado.inicializa();	

		for (int f = 0; f < this.filas; f++) {

			for (int c = 0; c < this.columnas; c++) 
				resultado.matriz[f][0] += this.matriz[f][c] * v.getVector()[c];
		}

		return resultado;
		
	}

	/*
	 * Producto entre dos matrices.
	 */
	public MatrizMath producto(MatrizMath m) {

		if (this.columnas != m.filas) 
			return null;		

		MatrizMath resultado = new MatrizMath(this.filas, m.columnas);

		resultado.inicializa();

		for (int f = 0; f < resultado.filas; f++) {
			for (int c = 0; c < resultado.columnas; c++) {
				for (int k = 0; k < this.columnas; k++) 
					resultado.matriz[f][c] += this.matriz[f][k] * m.matriz[k][c];				
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
		// TODO

		return null;
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
		MatrizMath m3 = new MatrizMath("matriz3.in");
		MatrizMath m4 = new MatrizMath("matriz4.in");
		VectorMath v1 = new VectorMath("vector1.in");

		System.out.println(m3);
		System.out.println(m3.identidad());
		System.out.println(m3.inversa());
		
		 System.out.println((m4.producto(m4.inversa())).normaUno());
		 
		//System.out.println(m1.producto(new Float(-1.0)));

	}
}
