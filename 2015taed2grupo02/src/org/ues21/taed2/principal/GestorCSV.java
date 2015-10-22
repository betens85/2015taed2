package org.ues21.taed2.principal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.ues21.taed2.estructura.IEstructuraDeDatos;

/**
 * Clase que permite la carga de un archivo csv en una estructura de datos o una
 * consulta automatica
 * 
 * @author grupo02
 *
 */
public final class GestorCSV {

	private GestorCSV() {
	}

	/**
	 * Metodo que permite la carga de un archivo csv en una estructura de datos
	 * 
	 * @param estructuraDeDatos
	 *            la estructura de datos a ser cargada
	 * @param fullPathArchivo
	 *            el path completo al archivo que sera cargado
	 * @return el tiempo en milisegundos de toda la carga
	 */
	public static Long cargar(IEstructuraDeDatos<Registro> estructuraDeDatos, String fullPathArchivo) {
		Long tiempoTotalCarga = null;
		Long tiempoInicio = System.currentTimeMillis();
		Path file = Paths.get(fullPathArchivo);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			String[] campos;
			while (scanner.hasNextLine()) {
				campos = scanner.nextLine().split(",");
				estructuraDeDatos.insertar(new Registro(new Integer(campos[0]), campos[1]));
			}
			System.out.println("\t" + estructuraDeDatos.getTipoEstructura() + " =====> carga exitosa");
		}
		catch (IOException ex) {
			System.err.println("Ocurrio una excepcion al procesar el archivo: " + ex.getMessage());
		}
		catch (Exception ex) {
			System.err.println("Ocurrio una excepcion al cargar las estructuras de datos: " + ex.getMessage());
		}finally {
			if (scanner != null) {
				scanner.close();
			}

			tiempoTotalCarga = System.currentTimeMillis() - tiempoInicio;
		}
		return tiempoTotalCarga;
	}

	/**
	 * Metodo que permite la consulta automatica de un archivo csv en una
	 * estructura de datos
	 * 
	 * @param estructuraDeDatos
	 *            la estructura de datos a ser consultada
	 * @param fullPathArchivo
	 *            el path completo al archivo a partir del cual se generaran
	 *            consultas
	 * @return el tiempo en milisegundos de toda la consulta
	 */
	public static Long consultar(IEstructuraDeDatos<Registro> estructuraDeDatos, String fullPathArchivo) {
		Long tiempoTotalConsulta = null;
		Long tiempoInicio = System.currentTimeMillis();
		Path file = Paths.get(fullPathArchivo);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			String[] campos;
			StringBuilder sbResultados = new StringBuilder();
			
			while (scanner.hasNextLine()) {
				campos = scanner.nextLine().split(",");
				Registro filtro = new Registro(new Integer(campos[0]), null);
				Registro resultado = estructuraDeDatos.buscar(filtro);
				if (resultado != null) {
					sbResultados.append("\n\tCodigo: " + resultado.getCodigo() + " - Resultado: " + resultado.getNombreCompleto());
				}
				else{
					sbResultados.append("\n\tCodigo: " + filtro.getCodigo() + " - Resultado: NO ENCONTRADO");
				}
			}
			
			if (sbResultados.length() > 0) {
				System.out.println();
				System.out.println(estructuraDeDatos.getTipoEstructura());
				System.out.println(sbResultados);
			}
		} catch (IOException ex) {
			System.err.println("Ocurrio una excepcion al procesar el archivo: " + ex.getMessage());
		} finally {
			if (scanner != null) {
				scanner.close();
			}

			tiempoTotalConsulta = System.currentTimeMillis() - tiempoInicio;
		}
		return tiempoTotalConsulta;
	}
	
	/**
	 * Representa a un registro leido desde un archivo cvs con dos campos
	 * @author grupo02
	 *
	 */
	public static class Registro implements Comparable<Registro>, Codeable {
		private Integer codigo;
		private String nombreCompleto;
		
		public Registro(Integer codigo, String nombreCompleto) {
			this.codigo = codigo;
			this.nombreCompleto = nombreCompleto;
		}
		public Integer getCodigo() {
			return codigo;
		}
		public String getNombreCompleto() {
			return nombreCompleto;
		}
		public void setNombreCompleto(String nombreCompleto) {
			this.nombreCompleto = nombreCompleto;
		}
		
		@Override
		public String toString() {
			return codigo.toString();
		}
		@Override
		public int compareTo(Registro r) {
			return codigo.compareTo(r.getCodigo());
		}
	}
	
	public static interface Codeable {
		Integer getCodigo();
	}
}
