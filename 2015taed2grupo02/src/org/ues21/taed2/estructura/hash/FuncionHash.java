package org.ues21.taed2.estructura.hash;

import org.ues21.taed2.estructura.TablaHash;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Interface que define la signatura para una funcion hash
 * 
 * @author grupo02
 *
 */
public interface FuncionHash<T extends Comparable<T> & Codeable> {

	/**
	 * Metodo que calcula el hash a ser utilizado por una tabla hash.
	 * @param tablaHash la tabla hash para la cual se utilizara la funcion
	 * @param datos losdatos que seran utilizados en la funcion
	 * @return el valor hash
	 */
	int calcularHash(TablaHash<T> tablaHash, T datos);
	
	/**
	 * Devuelve una representacion en texto del nombre de la funcion
	 * @return el nombre de la funcion
	 */
	String getNombre();
}
