package org.ues21.taed2.estructura;

/**
 * @author grupo02
 *
 */
public class TablaHash<T> implements IEstructuraDeDatos<T> {

	@Override
	public T buscar(Integer codigoBusqueda) {
		return null;
	}

	@Override
	public void insertar(T datos) {
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.TABLA_HASH;
	}
}
