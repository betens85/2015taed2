package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoLD;

/**
 * @author grupo02
 *
 */
public class ListaDobleEnlazada<T extends Comparable<T>> implements IEstructuraDeDatos<T> {
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
	
	private NodoLD<T> buscarHaciaAdelante(T datos) {

		if (!estaVacia()) {
			NodoLD<T> nodoAux = primero;
			while (nodoAux.getSiguiente() != null) {
				if (nodoAux.getDatos().compareTo(datos) == 0) {
					return nodoAux;
				}
				nodoAux = nodoAux.getSiguiente();
			}
		}

		return null;
	}

	private NodoLD<T> buscarHaciaAtras(T datos) {

		if (!estaVacia()) {
			NodoLD<T> nodoAux = ultimo;
			while (nodoAux.getAnterior() != null) {
				if (nodoAux.getDatos().compareTo(datos) == 0) {
					return nodoAux;
				}
				nodoAux = nodoAux.getAnterior();
			}
		}

		return null;
	}

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
