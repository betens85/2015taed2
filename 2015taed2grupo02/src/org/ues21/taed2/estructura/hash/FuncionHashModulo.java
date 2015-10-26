package org.ues21.taed2.estructura.hash;

import org.ues21.taed2.estructura.TablaHash;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa la funcion hash modulo
 * 
 * @author grupo02
 *
 * @param <T>
 *            representa la clase parametrizada
 */
public class FuncionHashModulo<T extends Comparable<T> & Codeable> implements FuncionHash<T> {

	@Override
	public int calcularHash(TablaHash<T> tablaHash, T datos) {
		Integer k = datos.getCodigo();
		return k % tablaHash.getTamaño();
	}
	
	@Override
	public String getNombre() {
		return "Modulo";
	}
}