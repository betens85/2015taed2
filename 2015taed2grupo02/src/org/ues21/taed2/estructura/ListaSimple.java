package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoLD;
import org.ues21.taed2.estructura.Nodo.NodoLS;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa una lista simple
 * @author grupo02
 *
 */
public class ListaSimple<T extends Comparable<T> & Codeable> implements IEstructuraDeDatos<T> {
	private NodoLD<T> frente;

	@Override
	public T buscar(T datos) {
		NodoLS<T> resultado = null;
		return resultado != null ? resultado.getDatos() : null;
	}

	@Override
	public void insertar(T datos) {
		insertarAlFrente(datos);
	}

	private void insertarAlFrente(T datos) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean estaVacia() {
		return frente == null;
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.LISTA_SIMPLE;
	}
}
