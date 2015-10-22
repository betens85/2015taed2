package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoArbol;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa estructura y comportamiento de un arbol AVL
 * 
 * @author grupo02
 *
 */
public class ArbolAVL<T extends Comparable<T> & Codeable> extends ArbolBinarioDeBusqueda<T> {

	@Override
	public void insertar(T datos) throws Exception {
		Boolean h = Boolean.FALSE;
		nodoRaiz = insertar(nodoRaiz, datos, h);
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.AAVL;
	}

	private NodoArbol<T> insertar(NodoArbol<T> p, T datos, Boolean h) throws Exception {
		NodoArbol<T> p1;
		if (p == null) {
			p = new NodoArbol<T>(datos);
			h = Boolean.TRUE;
		} else if (datos.compareTo(p.getDatos()) < 0) {
			NodoArbol<T> iz;
			iz = insertar(p.getIzquierdo(), datos, h);
			p.setIzquierdo(iz);
			// vuelvo por los nodos del camino de búsqueda
			if (h) {
				// decrementa el factor de equilibrio por aumentar la altura de
				// rama izquierda
				switch (p.getFactorEquilibrio()) {

				case 1:
					p.setFactorEquilibrio(0);
					h = Boolean.FALSE;
					break;
				case 0:
					p.setFactorEquilibrio(-1);
					break;
				case -1:
					// rotación izquierda
					p1 = p.getIzquierdo();
					if (p1.getFactorEquilibrio() == -1) {
						p = rotacionIzqSimple(p, p1);
					} else {
						p = rotacionIzqDoble(p, p1);
					}
					h = Boolean.FALSE;
				}
			}
		} else if (datos.compareTo(p.getDatos()) > 0) {
			NodoArbol<T> dr;
			dr = insertar(p.getDerecho(), datos, h);
			p.setDerecho(dr);
			// vuelta por los nodos del camino de búsqueda
			if (h) {
				// incrementa el factor de equilibrio por aumentar la altura de
				// rama izquierda
				switch (p.getFactorEquilibrio()) {

				case 1:
					// rotación derecha
					p1 = p.getDerecho();
					if (p1.getFactorEquilibrio() == 1) {
						p = rotacionDerSimple(p, p1);
					} else {
						p = rotacionDerDoble(p, p1);
					}
					h = Boolean.FALSE;
					break;
				case 0:
					p.setFactorEquilibrio(1);
					break;
				case -1:
					p.setFactorEquilibrio(0);
					h = Boolean.FALSE;
				}
			}
		} else {
			throw new Exception("No debe haber claves repetidas");
		}

		return p;
	}

	/**
	 * rotacion Izquierda Simple o rotación izquierda izquierda
	 * 
	 * @param p
	 * @param p1
	 * @return
	 */
	private NodoArbol<T> rotacionIzqSimple(NodoArbol<T> p, NodoArbol<T> p1) {
		p.setIzquierdo(p1.getDerecho());
		p1.setDerecho(p);
		// se actualizan los factores de equilibrio
		if (p1.getFactorEquilibrio() == -1) {
			// inserción cumplida
			p.setFactorEquilibrio(0);
			p1.setFactorEquilibrio(0);
		} else {
			p.setFactorEquilibrio(-1);
			p1.setFactorEquilibrio(1);
		}
		return p1;
	}

	/**
	 * rotacion derecha simple o rotación derecha derecha
	 *
	 * @param p
	 * @param p1
	 * @return
	 */
	private NodoArbol<T> rotacionDerSimple(NodoArbol<T> p, NodoArbol<T> p1) {
		p.setDerecho(p1.getIzquierdo());
		p1.setIzquierdo(p);
		// se actualizan los factores de equilibrio
		if (p1.getFactorEquilibrio() == 1) {
			// inserción cumplida
			p.setFactorEquilibrio(0);
			p1.setFactorEquilibrio(0);
		} else {
			p.setFactorEquilibrio(1);
			p1.setFactorEquilibrio(-1);
		}
		return p1;
	}

	/**
	 * rotación izquierda doble o rotación izquierda derecha
	 * 
	 * @param p
	 * @param p1
	 * @return
	 */
	private NodoArbol<T> rotacionIzqDoble(NodoArbol<T> p, NodoArbol<T> p1) {
		NodoArbol<T> p2;

		p2 = p1.getDerecho();
		p.setIzquierdo(p2.getDerecho());
		p2.setDerecho(p);
		p1.setDerecho(p2.getIzquierdo());
		p2.setIzquierdo(p1);
		// se actualizan los factores de equilibrio
		if (p2.getFactorEquilibrio() == 1) {
			p1.setFactorEquilibrio(-1);
		} else {
			p1.setFactorEquilibrio(0);
		}
		if (p2.getFactorEquilibrio() == -1) {
			p.setFactorEquilibrio(1);
		} else {
			p.setFactorEquilibrio(0);
		}
		p2.setFactorEquilibrio(0);
		return p2;
	}

	/**
	 * rotación derecha doble o rotación derecha izquierda
	 * 
	 * @param p
	 * @param p1
	 * @return
	 */
	private NodoArbol<T> rotacionDerDoble(NodoArbol<T> p, NodoArbol<T> p1) {
		NodoArbol<T> p2;
		p2 = p1.getIzquierdo();

		p.setDerecho(p2.getIzquierdo());
		p2.setIzquierdo(p);
		p1.setIzquierdo(p2.getDerecho());
		p2.setDerecho(p1);
		// se actualizan los factores de equilibrio
		if (p2.getFactorEquilibrio() == 1) {
			p.setFactorEquilibrio(-1);
		} else {
			p.setFactorEquilibrio(0);
		}
		if (p2.getFactorEquilibrio() == -1) {
			p1.setFactorEquilibrio(1);
		} else {
			p1.setFactorEquilibrio(0);
		}
		p2.setFactorEquilibrio(0);
		return p2;
	}

}
