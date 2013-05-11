//Complejo:
//Implementar los métodos que permitan realizar las siguientes operaciones entre objetos de la clase:
//sumar, restar,  multiplicar,  comparar (equals), obtener el modulo (modulo),  toString().
//Sobrecargue las funciones de manera que se pueda trabajar con objetos no complejos,
//por ejemplo,   sumar  un complejo  con un número  entero.

package complejo;

public class Complejo {

	
	private float a;
	private float b;
	
	Complejo ()
	{
		a= 0;
		b = 0;
	}
	
	Complejo (float a)
	{
		this.a = a;
		this.b = 0;
		
	}
	
	Complejo (float a, float b)
	{
		this.a = a;
		this.b= b;
	}
	
	//suma	
	Complejo suma (Complejo c2)
	{
		Complejo result = new Complejo();
		result.a = this.a + c2.a ;
		result.b = this.b + c2.b ;
		return result;
	}
	
	Complejo suma (int entero)
	{
		return new Complejo (this.a+entero,this.b);
	}
	
	//restar
	
	Complejo resta (Complejo c2)
	{
		Complejo result = new Complejo();
		result.a = this.a - c2.a ;
		result.b = this.b - c2.b ;
		return result;
	}
	
	Complejo resta (int entero)
	{
		return new Complejo (this.a-entero,this.b);
	}
	
	//multiplicar
	
	Complejo multiplicar (Complejo c2)
	{
		Complejo result = new Complejo();
		//(a + bi ) (c + di ) = (ac - bd) + (ad + bc)i 
		result.a = (this.a * c2.a- this.b * c2.b);
		result.b = (this.a * c2.b+this.b*c2.a);
		return result;
				
	}
	
	Complejo multiplicar (int entero)
	{
		return this.multiplicar(new Complejo(entero,0));
	}
	
	//comparar
	
	
	@Override
	public String toString() {
		return "Complejo [a=" + a + ", b=" + b + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complejo other = (Complejo) obj;
		if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
			return false;
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
			return false;
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Complejo c1 = new Complejo(1,2);
		Complejo c2 = new Complejo(2,3);
		Complejo c3 = c1.suma(c2);
		Complejo c4 = c1.suma(3);
		System.out.println (c4);
		

	}

}
