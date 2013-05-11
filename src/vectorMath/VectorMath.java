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

import matrizMath.MatrizMath;

public class VectorMath {

	private Double[] vector;

	private VectorMath (int tam)
	{
		vector = new Double [tam];
	}
	
	public VectorMath (String path)
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
	        	 this.vector = new Double[Integer.parseInt(linea)];
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
	
	public Double[] getVector() {
		return vector;
	}
		


	//suma
	private VectorMath suma(VectorMath v) {

		if (this.vector.length != v.vector.length) 
			return null;
		
		VectorMath resultado = new VectorMath(this.vector.length);

		for (int i = 0; i < this.vector.length; i++) 
			resultado.vector[i] = this.vector[i] + v.vector[i];
		return resultado;
	}
	
	//resta
	private VectorMath resta(VectorMath v) {

		if (this.vector.length != v.vector.length) 
			return null;
		
		VectorMath resultado = new VectorMath(this.vector.length);

		for (int i = 0; i < this.vector.length; i++) 
			resultado.vector[i] = this.vector[i] - v.vector[i];
		return resultado;
	}
	
	//producto
	private VectorMath producto (double k)
	{		
		VectorMath resultado = new VectorMath(this.vector.length);
		for (int i= 0; i < this.vector.length; i++)
			resultado.vector[i] = this.vector[i] * k;
		return resultado;		
	}
	
	public Double producto(VectorMath v) {
		if (this.vector.length != v.vector.length)
			return null;
		Double resultado = new Double(0);

		for (int i = 0; i < this.vector.length; i++)
			resultado += this.vector[i] * v.vector[i];
		
		return resultado;
	}
	
	public VectorMath producto(MatrizMath m) {

		if (this.vector.length != m.getColumnas())
			return null;
		
		VectorMath resultado = new VectorMath(m.getFilas());

		for (int c = 0; c < resultado.vector.length; c++)
			resultado.vector[c] = 0.0;

		for (int f = 0; f < m.getFilas(); f++) {
			for (int c = 0; c < m.getColumnas(); c++)
				resultado.vector[f] += m.getValor(f, c) * this.vector[c];
		}
		return resultado;
	}
	
	//norma
	private Double normaUno ()
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
	
	@Override
	public String toString() {
		return "vector=" + Arrays.toString(vector);
	}
	
	@Override
	public boolean equals(Object v) {
		
		VectorMath vector = (VectorMath) v;
		if (this.vector.length != vector.getVector().length)
			return false;
		
		for (int i = 0; i < this.vector.length; i++) {
			if (!(this.vector[i].equals(vector.vector[i])))
				return false;			
		}
		return true;
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
