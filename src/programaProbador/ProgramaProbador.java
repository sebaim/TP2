package programaProbador;

import java.util.Random;

import vectorMath.VectorMath;
import matrizMath.MatrizMath;

public class ProgramaProbador {

	/**
	 * @param args
	 */	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatrizMath mPrueba = null;
		MatrizMath mInversa = null;
		boolean correcto = true;
		Random r1 = new Random();
		int dim =0;
		int count = 0;
		Double determinante = 0.00;
		while (correcto && count < 10)
		{
			dim =20; //r1.nextInt(4);
			if (dim >1)
			{
			mPrueba= new MatrizMath(dim,dim);
			mPrueba.generaMatrizAleatoria();
			System.out.printf ("Matriz generada numero %d, dimension: %d %d\n",count+1,dim,dim);
			System.out.println (mPrueba);
			
			System.out.printf("Determinante %d: ", mPrueba.determinante());
			System.out.printf ("Matriz inversa numero %d\n",count+1,dim,dim);
			mInversa = mPrueba.inversa();
			System.out.println (mInversa);
			
			determinante = mPrueba.determinante();
			if (determinante != 0)
			{
			System.out.printf ("Producto Matriz x Inversa numero %d\n",count+1,dim,dim);
			System.out.println (mPrueba.producto(mInversa));
			}
			
			//System.out.printf ("Matriz Identidad numero %d\n",count+1,dim,dim);
			//System.out.println (mPrueba.identidad());
			
			//System.out.println (mPrueba);
			
			if (mPrueba.determinante()!=0)
				if (!mPrueba.producto(mPrueba.inversa()).equals(mPrueba.identidad()))
					correcto = false;
			}	
			count++;
		}
		System.out.println (correcto);		
	}

}
