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
	
	final private double epsilon = 1 * Math.pow(10, -12);

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

	}

	// Metodo para calcular el error entre dos matrices
	private Double calcularErrorSolucion(MatrizMath m1, MatrizMath m2){
		
		MatrizMath restaMatrices = m1.resta(m2);
		return restaMatrices.normaDos();
		
	}
	
	// Metodo que muestra el resultado 
	public void mostrarResultado(){
		
		Double[] resultado = x.getVector();
		System.out.println("Vector de Resultados:");
		for(int i = 0; i < dim; i++){
			
			System.out.println(resultado[i]);
		}
	}
	

	public static void main(String[] args) {
		
		Sel s = new Sel("SEL");
		
	}
}
