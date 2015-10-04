package org.ues21.taed2.estructura;

/**
 * @author grupo02
 *
 */
public class TablaHash implements IEstructuraDeDatos {

	@Override
	public String buscar(Integer codigoBusqueda) {
		return "dummy result";
	}

	@Override
	public void insertar(Integer codigo, String texto) {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.AAVL;
	}

	/**
	 * Representa un nodo para una tabla hash
	 * 
	 * @author grupo02
	 *
	 */
	private static class NodoTH {

	}

}
