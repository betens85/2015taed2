package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoLS;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa una lista simple
 * 
 * @author grupo02
 * @param <T>
 *            representacion parametrizada generica de los datos que contendra
 */
public class ListaSimple<T extends Comparable<T> & Codeable> implements IEstructuraDeDatos<T> {
	private NodoLS<T> frente;
	private int cantidadNodos;

	@Override
	public T buscar(T datos) {
		NodoLS<T> resultado = null;
		
		if (!estaVacia()) { //
			NodoLS<T> nodoAux = frente; // se busca desde el frente hacia adelante
			while(nodoAux != null && resultado == null) {
				if(nodoAux.getDatos().compareTo(datos) == 0) {// si son iguales es el nodo buscado
					resultado = nodoAux;
				}
				nodoAux = nodoAux.getSiguiente();
			}
		}
		
		//si resultado no es null se devuelves sus datos sino null
		return resultado != null ? resultado.getDatos() : null;
	}

	@Override
	public void insertar(T datos) {
		insertarEnFrente(datos);
		this.cantidadNodos++;
	}

	/**
	 * Metodo que inserta por el nodo frente de la lista
	 * @param datos los datos que formaran parte del nodo a insertar.
	 */
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

	@Override
	public int getCantidadNodos() {
		return cantidadNodos;
	}
}
