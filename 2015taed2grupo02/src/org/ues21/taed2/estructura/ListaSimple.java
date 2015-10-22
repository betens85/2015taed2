package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoLS;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa una lista simple
 * @author grupo02
 *
 */
public class ListaSimple<T extends Comparable<T> & Codeable> implements IEstructuraDeDatos<T> {
	private NodoLS<T> frente;

	@Override
	public T buscar(T datos) {
		NodoLS<T> resultado = null;
		
		if (!estaVacia()) {
			NodoLS<T> nodoAux = frente;
			while(nodoAux != null && resultado == null) {
				if(nodoAux.getDatos().compareTo(datos) == 0) {
					resultado = nodoAux;
				}
				nodoAux = nodoAux.getSiguiente();
			}
		}
		
		return resultado != null ? resultado.getDatos() : null;
	}

	@Override
	public void insertar(T datos) {
		insertarEnFrente(datos);
	}

	private void insertarEnFrente(T datos) {
		NodoLS<T> nodo = new NodoLS<>(datos);
		nodo.setSiguiente(this.frente);
		this.frente = nodo;
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
