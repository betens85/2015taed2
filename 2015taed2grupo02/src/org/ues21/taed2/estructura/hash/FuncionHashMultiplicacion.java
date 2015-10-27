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
		
		Double aux = ((1 + Math.sqrt(5)/ 2)) * datos.getCodigo();
		String auxStr = String.valueOf(aux);
		
		Double parteDecimal = new Double(auxStr.split("\\.")[1]);
		
		String parteEntera = String.valueOf(tablaHash.getTamaño() * parteDecimal).split("\\1.")[0];
		
		return new Integer(parteEntera);
	}
	
	@Override
	public String getNombre() {
		return "Multiplicacion";
	}
}