package org.ues21.taed2.estructura;

import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Representa la interfaz de una estructura de datos
 * 
 * @author grupo02
 *
 */
public interface IEstructuraDeDatos<T extends Comparable<T> & Codeable > {

	/**
	 * Permite la busqueda de informacion a partir de un codigo de busqueda
	 * 
	 * @param datos los datos que serviran para la busqueda
	 * @return el resultado de la busqueda
	 */
	T buscar(T datos);

	/**
	 * Permite la insercion de informacion en una estructura de datos
	 * 
	 * @param datos
	 *            los datos de la entidad a ser insertada
	 */
	void insertar(T datos);

	/**
	 * Devuelve el tipo de estructura
	 * 
	 * @return el tipo de estructura
	 */
	TipoEstructura getTipoEstructura();
	
	/**
	 * Metodo para verificar si la estructura de datos esta vacia
	 * @return si esta vacia o no
	 */
	boolean estaVacia();

	/**
	 * Representa un enumeracion de los tipos de estructuras
	 * 
	 * @author grupo02
	 *
	 */
	public static enum TipoEstructura {
		LISTA_SIMPLE("Lista Simple"), LISTA_DOB_ENLAZADA("Lista Doble Enlazada"), TABLA_HASH("Tabla Hash"), ABB("Arbol Binario de Busqueda"), AAVL(
				"Arbol AVL");

		private String descripcion;

		TipoEstructura(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getDescripcion() {
			return descripcion;
		}

		@Override
		public String toString() {
			return this.descripcion;
		}
	}

	/**
	 * Representa las metricas para un tipo de estructura
	 * @author grupo02
	 *
	 */
	public static class Metricas {
		private Long tiempoInsercion = 0L;
		private Long tiempoConsulta = 0L;

		public Long getTiempoInsercion() {
			return tiempoInsercion;
		}

		public void setTiempoInsercion(Long tiempoInsercion) {
			this.tiempoInsercion = tiempoInsercion;
		}

		public Long getTiempoConsulta() {
			return tiempoConsulta;
		}

		public void setTiempoConsulta(Long tiempoConsulta) {
			this.tiempoConsulta = tiempoConsulta;
		}
	}
}
