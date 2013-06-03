package programaProbador;

import java.util.Random;

import sel.Sel;
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
		
		
		VectorMath vectorPrueba;
		
		
		//Sel sel1 = new Sel(path)
		while (correcto && count < 1)
		{
			dim =50; //r1.nextInt(4);
			if (dim >1)
			{
			mPrueba= new MatrizMath(dim,dim);
			mPrueba.generaMatrizAleatoria();
			
			vectorPrueba = new VectorMath(dim);
			vectorPrueba.generaVectorAleatorio();
			
			Sel selPrueba = new Sel(mPrueba, vectorPrueba);
			
			selPrueba.resolver();
			
			System.out.println(selPrueba);
			
			
			
			
			//System.out.printf ("Matriz generada numero %d, dimension: %d %d\n",count+1,dim,dim);
			//System.out.println (mPrueba);
			
			//double det1 = mPrueba.determinante2();
			//double det2 = mPrueba.determinanteObsoleto();
			//System.out.printf("Determinante %f - %f: ", det1, det2);
			
			
			
			

//			if (mPrueba.comparaDeterminantes(det1, det2))
//				System.out.println("Determinante Correcto");
//			else
//				{
//				System.out.println("Determinante NO Correcto");
//				correcto= false;
//				
//				}
//			
//			System.out.printf ("Matriz inversa numero %d\n",count+1,dim,dim);
//			mInversa = mPrueba.inversa();
//			System.out.println (mInversa);
			
			//determinante = mPrueba.determinante2();
//			if (!MatrizMath.comparaDeterminantes(det2, 0))
//			{
//			System.out.printf ("Producto Matriz x Inversa numero %d\n",count+1,dim,dim);
//			System.out.println (mPrueba.producto(mInversa));
//			}
			
			//System.out.printf ("Matriz Identidad numero %d\n",count+1,dim,dim);
			//System.out.println (mPrueba.identidad());
			
			//System.out.println (mPrueba);
			
//			if (!MatrizMath.comparaDeterminantes(det2, 0))
//				if (!mPrueba.producto(mInversa).equals(mPrueba.identidad()))
//				{
//					System.out.printf("Incorrecto");
//					correcto = false;
//				}
//			}	
			count++;
		}
		//System.out.println (correcto);		

		}
	}

}
