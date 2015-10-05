package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoAVL;

/**
 * Clase que representa estructura y comportamiento de un arbol AVL
 * @author grupo02
 *
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioDeBusqueda<T> {
	
	private NodoAVL<T> nodoRaiz;
	private boolean h;
	
	@Override
	public void insertar(T datos) {
		h = false;
		nodoRaiz = insertar(nodoRaiz, datos);
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.AAVL;
	}

	private NodoAVL<T> insertar(NodoAVL<T> nodo, T datos) {
		NodoAVL<T> p1, p2;

		if (nodo == null) {
			nodo = new NodoAVL<T>(datos);
			h = true;
		} else if (datos.compareTo(nodo.getDatos()) < 0) {
			nodo.setIzquierdo(insertar((NodoAVL<T>) nodo.getIzquierdo(), datos));
			if (h) {
				// Crecio la rama izquierda

				switch (nodo.getFactorEquilibrio()) {
				case 0:
					nodo.setFactorEquilibrio(-1);
					break;
				case 1:
					nodo.setFactorEquilibrio(0);
					h = false;
					break;
				case -1:
					// Reequilibrar
					p1 = (NodoAVL<T>) nodo.getIzquierdo();
					if (p1.getFactorEquilibrio() == -1) {
						// Rotación izquierda simple
						nodo.setIzquierdo(p1.getDerecho());
						p1.setDerecho(nodo);
						nodo.setFactorEquilibrio(0);
						nodo = p1;
					} else {
						// Rotación izquierda doble
						p2 = (NodoAVL<T>) p1.getDerecho();
						p1.setDerecho(p2.getIzquierdo());
						p2.setIzquierdo(p1);
						nodo.setIzquierdo(p2.getDerecho());
						p2.setDerecho(nodo);
						nodo = p2;
						// p1.dcho = p2.izdo;
						// p2.izdo = p1;
						// p.izq = p2.dcho;
						// p2.dcho = p;
						// p = p2
					}
					nodo.setFactorEquilibrio(0);
					h = false;
					break;
				} // Fin switch

			} // fin if(h == true)
		} // fin if(x < (int) p.getInfo())
		else if (datos.compareTo(nodo.getDatos()) > 0) {
			nodo.setDerecho(insertar((NodoAVL<T>) nodo.getDerecho(), datos));
			if (h == true) {
				// Crecio la rama derecha

				switch (nodo.getFactorEquilibrio()) {
				case 0:
					nodo.setFactorEquilibrio(1);
					break;
				case 1:
					// Reequilibrar
					p1 = (NodoAVL<T>) nodo.getDerecho();
					if (p1.getFactorEquilibrio() == 1) {
						// Rotación derecha simple
						nodo.setDerecho(p1.getIzquierdo());
						p1.setIzquierdo(nodo);
						nodo.setFactorEquilibrio(0);
						nodo = p1;
					} else {
						// Rotación derecha doble

						p2 = (NodoAVL<T>) p1.getIzquierdo();
						p1.setIzquierdo(p2.getDerecho());
						p2.setDerecho(p1);
						nodo.setDerecho(p2.getIzquierdo());
						p2.setIzquierdo(nodo);
						nodo = p2;

					}
					nodo.setFactorEquilibrio(0);
					h = false;
					break;
				case -1:
					nodo.setFactorEquilibrio(0);
					h = false;
					break;
				}
			}
		} else {
			// La clave ya existe no hago nada
			h = false;
		}

		return nodo;
	}
}
