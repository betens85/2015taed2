/**
 * 
 */
package org.ues21.taed2.principal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.ues21.taed2.estructura.ArbolAVL;
import org.ues21.taed2.estructura.ArbolBinarioDeBusqueda;
import org.ues21.taed2.estructura.IEstructuraDeDatos;
import org.ues21.taed2.estructura.IEstructuraDeDatos.Metricas;
import org.ues21.taed2.estructura.IEstructuraDeDatos.TipoEstructura;
import org.ues21.taed2.estructura.ListaDobleEnlazada;
import org.ues21.taed2.estructura.TablaHash;
import org.ues21.taed2.principal.GestorCSV.Registro;

/**
 * Clase que representa el menu principal y sus operaciones
 * 
 * @author grupo02
 *
 */
public class Principal {

	private static Map<TipoEstructura, Metricas> mapaMetricas = new HashMap<>();
	private static Map<TipoEstructura, IEstructuraDeDatos<Registro>> estructurasMap = new HashMap<>();
	private static String pathDirectorio = "/home/norberto/taed2";
	private static boolean pathDirectorioCargado;
	private static boolean estructurasCargadas;

	public static void main(String[] args) {
		inicializarMetricas();
		inicializarEstructuras();
		Scanner scanner = null;
		int opcion;
		try {
			do {
				scanner = new Scanner(System.in);
				mostrarMenuPrincipal();
				opcion = scanner.nextInt();
				resolverOpcionMenuPrincipal(opcion);
			} while (opcion != 0);
		} catch (Exception ex) {
			System.err.println("Ocurriò un error al procesar la opcion seleccionada.");
			ex.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	private static void mostrarMenuPrincipal() {
		System.out.println(" ");
		System.out.println("========================================================");
		System.out.println("|               MENU PRINCIPAL                         |");
		System.out.println("========================================================");
		System.out.println("|                                                      |");
		System.out.println("| 1. Cargar Path de directorio                         |");
		System.out.println("| 2. Cargar archivo CSV                                |");
		System.out.println("| 3. Buscar Valor en estructuras                       |");
		System.out.println("| 4. Consulta automatica de CSV                        |");
		System.out.println("| 5. Mostrar Reporte                                   |");
		System.out.println("| 6. Mostrar Arboles                                   |");
		System.out.println("| 0. SALIR                                             |");
		System.out.print("\nINGRESE UNA OPCION: ");
	}

	public static void resolverOpcionMenuPrincipal(int opcion) {
		switch (opcion) {
		case 1:
			menuPrincipalOpcion1();
			break;
		case 2:
			menuPrincipalOpcion2();
			break;
		case 3:
			// TODO harcoded for testing purposal
			menuPrincipalOpcion3(TipoEstructura.ABB);
			break;
		case 4:
			menuPrincipalOpcion4();
			break;
		case 5:
			menuPrincipalOpcion5();
			break;
		case 6:
			menuPrincipalOpcion6();
			break;

		default:
			break;
		}
	}

	private static void inicializarMetricas() {
		mapaMetricas.clear();
		mapaMetricas.put(TipoEstructura.LISTA_DOB_ENLAZADA, new Metricas());
		mapaMetricas.put(TipoEstructura.TABLA_HASH, new Metricas());
		mapaMetricas.put(TipoEstructura.AAVL, new Metricas());
		mapaMetricas.put(TipoEstructura.ABB, new Metricas());
	}

	private static void inicializarEstructuras() {
		estructurasMap.clear();
		estructurasMap.put(TipoEstructura.LISTA_DOB_ENLAZADA, new ListaDobleEnlazada<Registro>());
		estructurasMap.put(TipoEstructura.TABLA_HASH, new TablaHash<Registro>());
		estructurasMap.put(TipoEstructura.ABB, new ArbolBinarioDeBusqueda<Registro>());
		estructurasMap.put(TipoEstructura.AAVL, new ArbolAVL<Registro>());
	}

	private static void menuPrincipalOpcion1() {
		// TODO preguntar por path directorio y setear en variable
		// 'pathDirectorio'
		mostrarTituloSeparador("CARGA DE PATH DIRECTORIO");
		System.out.print("\nIngrese el path del directorio: ");
		
		pathDirectorioCargado = true;
	}
	
	private static void menuPrincipalOpcion2() {
		if (pathDirectorioCargado) {
			// TODO controlar que este cargado path de directorio, sino esta
			// cargado
			// tirar excepcion o salir
			// TODO datos.csv harcoded, deberia ser cargado por la consola
			String fullPathArchivo = pathDirectorio + File.separator + "datos.csv";
			mostrarTituloSeparador("CARGA DE DATOS  - Archivo: " + fullPathArchivo);
			inicializarEstructuras();
			inicializarMetricas();

			estructurasMap.forEach((k, v) -> mapaMetricas.get(v.getTipoEstructura())
					.setTiempoInsercion(GestorCSV.cargar(v, fullPathArchivo)));

			estructurasCargadas = true;
		}else{
			System.out.println("\n** Debe cargar el path del directorio (Menu principal Opcion 1) **");
		}
	}

	private static void menuPrincipalOpcion3(TipoEstructura tipoEstructura) {
		if (estructurasCargadas) {
			// TODO hacer submenu que pregunte tipo estructura y dps el codigo a
			// buscar
			// codigo harcoded para prueba ==> 3
			int codigoBuscado = 3;
			IEstructuraDeDatos<Registro> estructuraDeDatos = estructurasMap.get(tipoEstructura);
			Registro registro = estructuraDeDatos.buscar(new Registro(codigoBuscado, null));
			System.out.println();
			mostrarTituloSeparador("BUSQUEDA DE VALOR: " + codigoBuscado);
			System.out.println("\t\t" + "Resultado: " + "Codigo: " + registro.getCodigo() + "\t Nombre: "
					+ registro.getNombreCompleto());
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}

	private static void menuPrincipalOpcion4() {
		if (estructurasCargadas) {
			// TODO chequear si el directorio fue cargado
			// TODO preguntar como se llama el archivo de consulta
			String fullPathArchivo = pathDirectorio + File.separator + "consulta.csv";
			System.out.println();
			mostrarTituloSeparador("CONSULTA AUTOMATICA - archivo: " + fullPathArchivo);
			estructurasMap.forEach((k, v) -> mapaMetricas.get(v.getTipoEstructura())
					.setTiempoConsulta(GestorCSV.consultar(v, fullPathArchivo)));
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}

	private static void separador() {
		System.out
				.println("******************************************************************************************");
	}

	private static void menuPrincipalOpcion5() {
		if (estructurasCargadas) {
			System.out.println();
			mostrarTituloSeparador("REPORTE DE METRICAS DE ESTRUCTURA ");
			System.out.println();
			System.out.println("Estructura                       Tiempo Insercion               Tiempo Consulta\n");
			System.out.println(TipoEstructura.LISTA_DOB_ENLAZADA + "\t\t\t"
					+ mapaMetricas.get(TipoEstructura.LISTA_DOB_ENLAZADA).getTiempoInsercion() + "\t\t\t\t"
					+ mapaMetricas.get(TipoEstructura.LISTA_DOB_ENLAZADA).getTiempoConsulta());
			System.out.println(TipoEstructura.TABLA_HASH + "\t\t\t\t"
					+ mapaMetricas.get(TipoEstructura.TABLA_HASH).getTiempoInsercion() + "\t\t\t\t"
					+ mapaMetricas.get(TipoEstructura.TABLA_HASH).getTiempoConsulta());
			System.out.println(TipoEstructura.ABB + "\t\t" + mapaMetricas.get(TipoEstructura.ABB).getTiempoInsercion()
					+ "\t\t\t\t" + mapaMetricas.get(TipoEstructura.ABB).getTiempoConsulta());
			System.out.println(
					TipoEstructura.AAVL + "\t\t\t\t" + mapaMetricas.get(TipoEstructura.AAVL).getTiempoInsercion()
							+ "\t\t\t\t" + mapaMetricas.get(TipoEstructura.AAVL).getTiempoConsulta());
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}

	private static void menuPrincipalOpcion6() {
		if (estructurasCargadas) {
			System.out.println();
			mostrarTituloSeparador("REPRESENTACION DE ARBOL BINARIO DE BUSQUEDA");
			ArbolBinarioDeBusqueda<Registro> abb = (ArbolBinarioDeBusqueda<Registro>) estructurasMap
					.get(TipoEstructura.ABB);
			GraficadorArbol.printNode(abb.getNodoRaiz());
			System.out.println();
			mostrarTituloSeparador("REPRESENTACION DE ARBOL AVL");
			ArbolBinarioDeBusqueda<Registro> avl = (ArbolBinarioDeBusqueda<Registro>) estructurasMap
					.get(TipoEstructura.AAVL);
			GraficadorArbol.printNode(avl.getNodoRaiz());
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}
	
	private static void mostrarTituloSeparador(String titulo) {
		System.out.println();
		separador();
		System.out.println(titulo);
		separador();
	}
}
