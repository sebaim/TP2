//Implementar los métodos que permitan realizar las siguientes operaciones entre objetos de la clase: 
//suma de vectores
//resta de vectores
//producto de un vectorMath por otro vectorMath
//producto de un vectorMath por una MatrizMath
//producto de un vectorMath por un Real
//normaUno(); normaDos; normaInfinito()
//equals().


package vectorMath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class VectorMath {

	private double[] vector;
	private int dim;

	private VectorMath (int dim)
	{
		vector = new double [dim];
	}
	
	private VectorMath (String path)
	{	      
	      File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File ("src/VectorMath/" + path);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Leo dimension del vector y lo creo
	         String linea;
	                  	         
	         if ((linea = br.readLine())!= null)
	         {
	        	 this.vector = new double[Integer.parseInt(linea)];
		         //Leo valores del vector y los asigno
	        	 
	        	 int cont = 0;
	        	 
	        	 while((linea=br.readLine())!=null && cont < this.vector.length)
	        	 {
	        		 vector[cont]= Double.parseDouble(linea);
	        		 cont++;
	        	 }
	        	 
	        	 if (cont == this.vector.length)
	        		 return;
		            
	         }
	         System.out.println("El archivo no contiene lo esperado");
	        this.vector = null;

	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }   
	}
	
	@Override
	public String toString() {
		return "vector=" + Arrays.toString(vector);
	}

	//suma
	private VectorMath suma (VectorMath vector2)
	{
		int dim = this.vector.length > vector2.vector.length ? this.vector.length : vector2.vector.length;
		VectorMath resultado = new VectorMath(dim);
		for (int i= 0; i<dim ; i++)
		{
			double val1 = i > this.vector.length-1 ? 0 : this.vector[i];
			double val2 = i > vector2.vector.length ? 0 : vector2.vector[i];
			
			resultado.vector[i] = val1 + val2;
		}
		
		return resultado;
	}
	
	//resta
	private VectorMath resta (VectorMath vector2)
	{
		int dim = this.vector.length > vector2.vector.length ? this.vector.length : vector2.vector.length;
		VectorMath resultado = new VectorMath(dim);
		for (int i= 0; i<dim ; i++)
		{
			double val1 = i > this.vector.length-1 ? 0 : this.vector[i];
			double val2 = i > vector2.vector.length ? 0 : vector2.vector[i];
			
			resultado.vector[i] = val1 - val2;
		}
		
		return resultado;
	}
	
	//producto
	VectorMath producto (double k)
	{
		
		VectorMath resultado = new VectorMath(this.vector.length);
		for (int i= 0; i < this.vector.length; i++)
			resultado.vector[i] = this.vector[i] * k;
		return resultado;
		
	}
	
	Double producto (VectorMath vector2)
	{
		if (this.vector.length != vector2.vector.length)
			return null;
		
		Double resultado = (double) 0;
		for (int i= 0; i < this.vector.length; i++)
			resultado += this.vector[i] * vector2.vector[i];
		return resultado;		
	}	
	
	//norma
	Double normaUno ()
	{
		Double resultado = new Double(0);
		for (int i =0; i < this.vector.length; i++)
			resultado += Math.abs(this.vector[i]);
		
		return resultado;
	}
	
	public Double normaDos() {

		Double resultado = new Double(0);

		for (int i = 0; i < this.vector.length; i++) 
			resultado += Math.pow(this.vector[i], 2);
		
		resultado = Math.sqrt(resultado);

		return resultado;
	}
	
	public Double normaInfinito() {

		Double resultado = new Double(0);

		for (int i = 0; i < this.vector.length; i++) {

			if (resultado < Math.abs(this.vector[i])) 
				
				resultado = Math.abs(this.vector[i]);			
		}

		return resultado;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VectorMath v1 = new VectorMath("vector1.in");
		VectorMath v2 = new VectorMath("vector2.in");
		VectorMath v3 = new VectorMath("vector3.in");
		VectorMath v4 = new VectorMath("vector4.in");
		System.out.println(v1);
		System.out.println(v2);
		System.out.println(v3);
		System.out.println(v4);
		
//		VectorMath sumav1v4 = v1.suma(v4);
//		VectorMath sumav1v3 = v1.suma(v3);
//		
//		System.out.println(sumav1v4);
//		System.out.println(sumav1v3);
//		
//		System.out.println(v1.resta(v4));
//		System.out.println(v1.resta(v3));
		
		System.out.println(v1.producto(-1));	
		System.out.println(v1.producto(v2));
		
		
	}

}
