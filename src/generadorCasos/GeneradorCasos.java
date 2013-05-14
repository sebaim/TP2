package generadorCasos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import matrizMath.MatrizMath;
import vectorMath.VectorMath;

public class GeneradorCasos {

	private MatrizMath m;
	private int dim;
	private VectorMath b;
	
	public GeneradorCasos(String path) {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File("src/generadorCasos/" + path + ".in");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;
			
			if((linea = br.readLine()) != null)
			{
				String[] valoresVect = linea.split(" ");
				dim = valoresVect.length;
				
				this.b = new VectorMath(dim);
			
				for (int i=0 ; i < valoresVect.length ; i++)
				{
					b.agregarValor(i, (double) Integer.parseInt(valoresVect[i]));
				}	
				
				this.m = new MatrizMath(dim, dim);
				
				int f=0;
				
				while ((linea = br.readLine()) != null) 
				{
					String[] valoresMat = linea.split(" ");
					
					for( int c=0 ; c < valoresMat.length; c++)
						m.setValor(f, c, (double) Integer.parseInt(valoresMat[c]));
					
					f++;
					
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
	
	public void generarCaso(String path)
	{
		 FileWriter fichero = null;
	        PrintWriter pw = null;
	        try
	        {
	            fichero = new FileWriter("src/generadorCasos/"+ path +".in");
	            pw = new PrintWriter(fichero);
	            
	            pw.println(this.dim);
	            
	            Double[] vect = this.b.getVector();
	            
	            for( int i=0; i<dim ; i++)
	            	pw.println(vect[i]);
	            
	            for( int f=0 ; f < dim ; f++)
	            	for( int c=0 ; c < dim ; c++)
	            		pw.println(f + " " + c + " " + this.m.getValor(f, c) );
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           try {
	           // Nuevamente aprovechamos el finally para 
	           // asegurarnos que se cierra el fichero.
	           if (null != fichero)
	              fichero.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
	        }
	}
	
	public static void main(String[] args) {
	
		GeneradorCasos caso = new GeneradorCasos("GCaso1"); 
		
		System.out.println(caso.b.toString());
		System.out.println(caso.m.toString());
		
		caso.generarCaso("Caso1");
		
		
	} 

	
}
