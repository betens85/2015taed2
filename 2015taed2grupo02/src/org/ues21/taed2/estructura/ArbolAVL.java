package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoArbol;

/**
 * Clase que representa estructura y comportamiento de un arbol AVL
 * @author grupo02
 *
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioDeBusqueda<T> {
	
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

	private NodoArbol<T> insertar(NodoArbol<T> p, T datos) {
		NodoArbol<T> p1, p2;

		if (p == null) {
			p = new NodoArbol<T>(datos, 0, null, null);
			h = true;
		} else if (datos.compareTo(p.getDatos()) < 0) {
			p.setIzquierdo(insertar((NodoArbol<T>) p.getIzquierdo(), datos));
			if (h) {
				// Crecio la rama izquierda

				switch (p.getFactorEquilibrio()) {
				case 0:
					p.setFactorEquilibrio(-1);
					break;
				case 1:
					p.setFactorEquilibrio(0);
					h = false;
					break;
				case -1:
					// Reequilibrar
					p1 = (NodoArbol<T>) p.getIzquierdo();
					if (p1.getFactorEquilibrio() == -1) {
						// Rotación izquierda simple
						p.setIzquierdo(p1.getDerecho());
						p1.setDerecho(p);
						p.setFactorEquilibrio(0);
						p = p1;
					} else {
						// Rotación izquierda doble
						p2 = (NodoArbol<T>) p1.getDerecho();
						p1.setDerecho(p2.getIzquierdo());
						p2.setIzquierdo(p1);
						p.setIzquierdo(p2.getDerecho());
						p2.setDerecho(p);
						p = p2;
						// p1.dcho = p2.izdo;
						// p2.izdo = p1;
						// p.izq = p2.dcho;
						// p2.dcho = p;
						// p = p2
					}
					p.setFactorEquilibrio(0);
					h = false;
					break;
				} // Fin switch

			} // fin if(h == true)
		} // fin if(x < (int) p.getInfo())
		else if (datos.compareTo(p.getDatos()) > 0) {
			p.setDerecho(insertar((NodoArbol<T>) p.getDerecho(), datos));
			if (h == true) {
				// Crecio la rama derecha

				switch (p.getFactorEquilibrio()) {
				case 0:
					p.setFactorEquilibrio(1);
					break;
				case 1:
					// Reequilibrar
					p1 = (NodoArbol<T>) p.getDerecho();
					if (p1.getFactorEquilibrio() == 1) {
						// Rotación derecha simple
						p.setDerecho(p1.getIzquierdo());
						p1.setIzquierdo(p);
						p.setFactorEquilibrio(0);
						p = p1;
					} else {
						// Rotación derecha doble

						p2 = (NodoArbol<T>) p1.getIzquierdo();
						p1.setIzquierdo(p2.getDerecho());
						p2.setDerecho(p1);
						p.setDerecho(p2.getIzquierdo());
						p2.setIzquierdo(p);
						p = p2;

					}
					p.setFactorEquilibrio(0);
					h = false;
					break;
				case -1:
					p.setFactorEquilibrio(0);
					h = false;
					break;
				}
			}
		} else {
			// La clave ya existe no hago nada
			h = false;
		}

		return p;
	}
}
