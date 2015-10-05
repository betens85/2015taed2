package org.ues21.taed2.estructura;

/**
 * @author grupo02
 *
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioDeBusqueda<T> {

	@Override
	public void insertar(T datos) {
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.AAVL;
	}
}
