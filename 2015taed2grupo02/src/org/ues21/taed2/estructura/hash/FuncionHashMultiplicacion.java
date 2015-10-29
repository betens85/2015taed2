package org.ues21.taed2.estructura.hash;

import org.ues21.taed2.estructura.TablaHash;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa la funcion hash multiplicacion
 * 
 * @author grupo02
 *
 * @param <T>
 *            representa la clase parametrizada
 */
public class FuncionHashMultiplicacion<T extends Comparable<T> & Codeable> implements FuncionHash<T> {

	@Override
	public int calcularHash(TablaHash<T> tablaHash, T datos) {
		
		/*
		 * * Funcion Multiplicacion (apuntes de clase)*
		 * 
		 * 1 - Primero se multiplica el numero 'x'  por una constante R comprendida entre 0 y 1, donde x es el dato que se quiere insertar y R
		 *     en este caso es la inversa de la 'razon aurea'. --> R * x
		 * 
		 * 2 - Separar la parte decimal del numero obtenido fraccionario obtenido en el primer paso --> d= (R * x) - parteEntera(R * x)
		 * 
		 * 3 - Obtener la parte entera de la multiplicacion entre el tamaño de la tabla hash y el numero decimal obtenido en el paso 2.
		 *     h(x) = parteEntera(m * d)
		 */
		
		//paso 1
		double razonAurea = (1 + Math.sqrt(5)) / 2;
		double inversaRazonAurea = 1 / razonAurea;		
		double nroPaso1 = inversaRazonAurea * datos.getCodigo();
		
		//paso 2
		double parteDecimal= nroPaso1 - Math.floor(nroPaso1);
		
		//paso 3
		int parteEntera = (int) (tablaHash.getTamaño() * parteDecimal);		
		
		//retornamos la posicion
		return parteEntera;
	}
	
	@Override
	public String getNombre() {
		return "Multiplicacion";
	}
}