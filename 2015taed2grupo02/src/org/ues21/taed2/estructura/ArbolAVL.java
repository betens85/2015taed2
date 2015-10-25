package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoArbol;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa estructura y comportamiento de un arbol AVL
 * 
 * @author grupo02
 * 
 * @param <T>
 *            representacion parametrizada generica de los datos que contendra
 */
public class ArbolAVL<T extends Comparable<T> & Codeable> extends ArbolBinarioDeBusqueda<T> {

	@Override
	public void insertar(T datos) throws Exception {
		Boolean h = Boolean.FALSE;
		nodoRaiz = insertar(nodoRaiz, datos, h);
		cantidadNodos++;
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.AAVL;
	}

	private NodoArbol<T> insertar(NodoArbol<T> nodo, T datos, Boolean h) throws Exception {
		NodoArbol<T> izq;
		if (nodo == null) {
			nodo = new NodoArbol<T>(datos);
			h = Boolean.TRUE;
		} else if (datos.compareTo(nodo.getDatos()) < 0) {
			NodoArbol<T> nodoIzq;
			nodoIzq = insertar(nodo.getIzquierdo(), datos, h);
			nodo.setIzquierdo(nodoIzq);
			// vuelvo por los nodos del camino de búsqueda
			if (h) {
				// decrementa el factor de equilibrio por aumentar la altura de
				// rama izquierda
				switch (nodo.getFactorEquilibrio()) {

				case 1:
					nodo.setFactorEquilibrio(0);
					h = Boolean.FALSE;
					break;
				case 0:
					nodo.setFactorEquilibrio(-1);
					break;
				case -1:
					// rotación izquierda
					izq = nodo.getIzquierdo();
					if (izq.getFactorEquilibrio() == -1) {
						nodo = rotacionIzqSimple(nodo, izq);
					} else {
						nodo = rotacionIzqDoble(nodo, izq);
					}
					h = Boolean.FALSE;
				}
			}
		} else if (datos.compareTo(nodo.getDatos()) > 0) {
			NodoArbol<T> nodoDer;
			nodoDer = insertar(nodo.getDerecho(), datos, h);
			nodo.setDerecho(nodoDer);
			// vuelta por los nodos del camino de búsqueda
			if (h) {
				// incrementa el factor de equilibrio por aumentar la altura de
				// rama izquierda
				switch (nodo.getFactorEquilibrio()) {

				case 1:
					// rotación derecha
					izq = nodo.getDerecho();
					if (izq.getFactorEquilibrio() == 1) {
						nodo = rotacionDerSimple(nodo, izq);
					} else {
						nodo = rotacionDerDoble(nodo, izq);
					}
					h = Boolean.FALSE;
					break;
				case 0:
					nodo.setFactorEquilibrio(1);
					break;
				case -1:
					nodo.setFactorEquilibrio(0);
					h = Boolean.FALSE;
				}
			}
		} else {
			throw new Exception("No debe haber claves repetidas");
		}

		return nodo;
	}

	/**
	 * rotacion Izquierda Simple o rotación izquierda izquierda
	 * 
	 * @param nodo
	 * @param nodoIzq
	 * @return
	 */
	private NodoArbol<T> rotacionIzqSimple(NodoArbol<T> nodo, NodoArbol<T> nodoIzq) {
		nodo.setIzquierdo(nodoIzq.getDerecho());
		nodoIzq.setDerecho(nodo);
		// se actualizan los factores de equilibrio
		if (nodoIzq.getFactorEquilibrio() == -1) {
			// inserción cumplida
			nodo.setFactorEquilibrio(0);
			nodoIzq.setFactorEquilibrio(0);
		} else {
			nodo.setFactorEquilibrio(-1);
			nodoIzq.setFactorEquilibrio(1);
		}
		return nodoIzq;
	}

	/**
	 * rotacion derecha simple o rotación derecha derecha
	 *
	 * @param nodo
	 * @param nodoDer
	 * @return
	 */
	private NodoArbol<T> rotacionDerSimple(NodoArbol<T> nodo, NodoArbol<T> nodoDer) {
		nodo.setDerecho(nodoDer.getIzquierdo());
		nodoDer.setIzquierdo(nodo);
		// se actualizan los factores de equilibrio
		if (nodoDer.getFactorEquilibrio() == 1) {
			// inserción cumplida
			nodo.setFactorEquilibrio(0);
			nodoDer.setFactorEquilibrio(0);
		} else {
			nodo.setFactorEquilibrio(1);
			nodoDer.setFactorEquilibrio(-1);
		}
		return nodoDer;
	}

	/**
	 * rotación izquierda doble o rotación izquierda derecha
	 * 
	 * @param nodo
	 * @param nodo2
	 * @return
	 */
	private NodoArbol<T> rotacionIzqDoble(NodoArbol<T> nodo, NodoArbol<T> nodo2) {
		NodoArbol<T> nodo2Der;

		nodo2Der = nodo2.getDerecho();
		nodo.setIzquierdo(nodo2Der.getDerecho());
		nodo2Der.setDerecho(nodo);
		nodo2.setDerecho(nodo2Der.getIzquierdo());
		nodo2Der.setIzquierdo(nodo2);
		// se actualizan los factores de equilibrio
		if (nodo2Der.getFactorEquilibrio() == 1) {
			nodo2.setFactorEquilibrio(-1);
		} else {
			nodo2.setFactorEquilibrio(0);
		}
		if (nodo2Der.getFactorEquilibrio() == -1) {
			nodo.setFactorEquilibrio(1);
		} else {
			nodo.setFactorEquilibrio(0);
		}
		nodo2Der.setFactorEquilibrio(0);
		return nodo2Der;
	}

	/**
	 * rotación derecha doble o rotación derecha izquierda
	 * 
	 * @param nodo
	 * @param nodo2
	 * @return
	 */
	private NodoArbol<T> rotacionDerDoble(NodoArbol<T> nodo, NodoArbol<T> nodo2) {
		NodoArbol<T> nodo2Izq;
		nodo2Izq = nodo2.getIzquierdo();

		nodo.setDerecho(nodo2Izq.getIzquierdo());
		nodo2Izq.setIzquierdo(nodo);
		nodo2.setIzquierdo(nodo2Izq.getDerecho());
		nodo2Izq.setDerecho(nodo2);
		// se actualizan los factores de equilibrio
		if (nodo2Izq.getFactorEquilibrio() == 1) {
			nodo.setFactorEquilibrio(-1);
		} else {
			nodo.setFactorEquilibrio(0);
		}
		if (nodo2Izq.getFactorEquilibrio() == -1) {
			nodo2.setFactorEquilibrio(1);
		} else {
			nodo2.setFactorEquilibrio(0);
		}
		nodo2Izq.setFactorEquilibrio(0);
		return nodo2Izq;
	}

}
