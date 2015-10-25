package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoLD;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa una lista doble enlazada
 * 
 * @author grupo02
 * @param <T>
 *            representacion parametrizada generica de los datos que contendra
 */
public class ListaDobleEnlazada<T extends Comparable<T> & Codeable> implements IEstructuraDeDatos<T> {
	private NodoLD<T> primero;
	private NodoLD<T> ultimo;

	@Override
	public T buscar(T datos) {
		NodoLD<T> resultado = buscarHaciaAtras(datos);
		return resultado != null ? resultado.getDatos() : null;
	}

	@Override
	public void insertar(T datos) {
		insertarAlFinal(datos);
	}

	@Override
	public boolean estaVacia() {
		return primero == null || ultimo == null;
	}
	
	/**
	 * Permite insertar un nodo al principio de la lista
	 * @param datos los datos que formaran parte del nodo a insertar
	 */
	private void insertarAlPrincipio(T datos) {
		NodoLD<T> nuevoNodo = new NodoLD<>(datos);
		
		if (estaVacia()) {
			primero = nuevoNodo;
			ultimo = nuevoNodo;
			primero.setSiguiente(ultimo);
		} else {
			NodoLD<T> nodoAux = primero;
			primero = nuevoNodo;
			nuevoNodo.setSiguiente(nodoAux);
		}
	}

	/**
	 * Permite insertar un nodo al final de la lista
	 * @param datos los datos que formaran parte del nodo a insertar
	 */
	private void insertarAlFinal(T datos) {
		NodoLD<T> nuevoNodo = new NodoLD<>(datos);
		
		if (estaVacia()) {
			primero = nuevoNodo;
			ultimo = nuevoNodo;
			primero.setSiguiente(ultimo);
		} else {
			NodoLD<T> nodoAux = ultimo;
			ultimo = nuevoNodo;
			primero.setSiguiente(ultimo);
			nuevoNodo.setAnterior(nodoAux);
		}
	}
	
	/**
	 * Permite la busqueda de datos hacia adelante, es decir a partir del primer elemento de la lista
	 * @param datos los datos a buscar dentro de la lista
	 * @return el nodo resultado
	 */
	private NodoLD<T> buscarHaciaAdelante(T datos) {

		if (!estaVacia()) {
			NodoLD<T> nodoAux = primero;
			while (nodoAux != null) {
				if (nodoAux.getDatos().compareTo(datos) == 0) {
					return nodoAux;
				}
				nodoAux = nodoAux.getSiguiente();
			}
		}

		return null;
	}

	
	/**
	 * Permite la busqueda de datos hacia atras, es decir a partir del ultimo elemento de la lista
	 * @param datos los datos a buscar dentro de la lista
	 * @return el nodo resultado
	 */
	private NodoLD<T> buscarHaciaAtras(T datos) {

		if (!estaVacia()) {
			NodoLD<T> nodoAux = ultimo;
			while (nodoAux != null) {
				if (nodoAux.getDatos().compareTo(datos) == 0) {
					return nodoAux;
				}
				nodoAux = nodoAux.getAnterior();
			}
		}

		return null;
	}

	/**
	 * Metodo que permite borrar un elemento de la lista a partir de un criterio de busqueda pasado por parametro
	 * @param datos parametro de busqueda de nodo a borrar
	 */
	public void borrar(T datos) {
		NodoLD<T> nodoABorrar = buscarHaciaAdelante(datos);

		if (nodoABorrar != null) {
			nodoABorrar.getSiguiente().setAnterior(nodoABorrar.getAnterior());
			nodoABorrar.getAnterior().setSiguiente(nodoABorrar.getSiguiente());
		}
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.LISTA_DOB_ENLAZADA;
	}
}
