package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoArbol;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa estructura y comportamiento de un arbol AVL
 * @author grupo02
 *
 */
public class ArbolAVL<T extends Comparable<T> & Codeable> extends ArbolBinarioDeBusqueda<T> {
	
	private boolean crecimientoEnAltura;
	
	@Override
	public void insertar(T datos) {
		crecimientoEnAltura = false;
		nodoRaiz = insertar(nodoRaiz, datos);
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.AAVL;
	}

	private NodoArbol<T> insertar(NodoArbol<T> nodo, T datos) {
		NodoArbol<T> nodo1, nodo2;

		if (nodo == null) {
			nodo = new NodoArbol<T>(datos, 0, null, null);
			crecimientoEnAltura = true;
		} else if (datos.compareTo(nodo.getDatos()) < 0) {
			nodo.setIzquierdo(insertar(nodo.getIzquierdo(), datos));
			if (crecimientoEnAltura) {
				// Crecio la rama izquierda

				switch (nodo.getFactorEquilibrio()) {
				case 0:
					nodo.setFactorEquilibrio(-1);
					break;
				case 1:
					nodo.setFactorEquilibrio(0);
					crecimientoEnAltura = false;
					break;
				case -1:
					// Reequilibrar
					nodo1 = nodo.getIzquierdo();
					if (nodo1.getFactorEquilibrio() == -1) {
						// Rotación izquierda simple
						nodo.setIzquierdo(nodo1.getDerecho());
						nodo1.setDerecho(nodo);
						nodo.setFactorEquilibrio(0);
						nodo = nodo1;
					} else {
						// Rotación izquierda doble
						nodo2 = nodo1.getDerecho();
						nodo1.setDerecho(nodo2.getIzquierdo());
						nodo2.setIzquierdo(nodo1);
						nodo.setIzquierdo(nodo2.getDerecho());
						nodo2.setDerecho(nodo);
						nodo = nodo2;
						// nodo1.getDerecho() = nodo2.getIzquierdo();
						// nodo2.getIzquierdo() = nodo1;
						// nodo.getIzquierdo()= nodo2.getDerecho();
						// nodo2.getDerecho() = nodo;
						// nodo = nodo2;
					}
					nodo.setFactorEquilibrio(0);
					crecimientoEnAltura = false;
					break;
				} // Fin switch

			} // fin if(crecimientoEnAltura)
		} // fin if(datos.compareTo(nodo.getDatos()) < 0)
		else if (datos.compareTo(nodo.getDatos()) > 0) {
			nodo.setDerecho(insertar(nodo.getDerecho(), datos));
			if (crecimientoEnAltura) {
				// Crecio la rama derecha

				switch (nodo.getFactorEquilibrio()) {
				case 0:
					nodo.setFactorEquilibrio(1);
					break;
				case 1:
					// Reequilibrar
					nodo1 = nodo.getDerecho();
					if (nodo1.getFactorEquilibrio() == 1) {
						// Rotación derecha simple
						nodo.setDerecho(nodo1.getIzquierdo());
						nodo1.setIzquierdo(nodo);
						nodo.setFactorEquilibrio(0);
						nodo = nodo1;
					} else {
						// Rotación derecha doble

						nodo2 = nodo1.getIzquierdo();
						nodo1.setIzquierdo(nodo2.getDerecho());
						nodo2.setDerecho(nodo1);
						nodo.setDerecho(nodo2.getIzquierdo());
						nodo2.setIzquierdo(nodo);
						nodo = nodo2;

					}
					nodo.setFactorEquilibrio(0);
					crecimientoEnAltura = false;
					break;
				case -1:
					nodo.setFactorEquilibrio(0);
					crecimientoEnAltura = false;
					break;
				}
			}
		} else {
			// La clave ya existe no hago nada
			crecimientoEnAltura = false;
		}

		return nodo;
	}
}
