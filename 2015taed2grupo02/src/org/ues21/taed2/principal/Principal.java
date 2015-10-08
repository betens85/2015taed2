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

	private static final String CONSULTA_CSV = "consulta.csv";
	private static final String CARGA_CSV = "datos.csv";
	private static Map<TipoEstructura, Metricas> mapaMetricas = new HashMap<>();
	private static Map<TipoEstructura, IEstructuraDeDatos<Registro>> estructurasMap = new HashMap<>();
	private static String pathDirectorio;
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
				resolverOpcionMenuPrincipal(opcion, scanner);
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

	private static void mostrarMenuPrincipal() {
		System.out.println(" ");
		System.out.println("========================================================");
		System.out.println("|               MENU PRINCIPAL                         |");
		System.out.println("========================================================");
		System.out.println(" Path directorio: " + ((pathDirectorio == null || pathDirectorio.isEmpty()) ? "No cargado" : pathDirectorio));
		System.out.println("|                                                      |");
		System.out.println("|                                                      |");
		System.out.println("| 1. Cargar Path de directorio                         |");
		System.out.println("| 2. Cargar archivo CSV                                |");
		System.out.println("| 3. Buscar Valor en estructuras                       |");
		System.out.println("| 4. Consulta automatica de CSV                        |");
		System.out.println("| 5. Mostrar Reporte                                   |");
		System.out.println("| 6. Mostrar Arboles                                   |");
		System.out.println("| 0. SALIR                                             |");
		System.out.print("\nINGRESE UNA OPCION: ");
		System.out.println();
	}

	public static void resolverOpcionMenuPrincipal(int opcion, Scanner scanner) {
		switch (opcion) {
		case 1:
			menuPrincipalOpcion1(scanner);
			break;
		case 2:
			menuPrincipalOpcion2(scanner);
			break;
		case 3:
			menuPrincipalOpcion3(scanner);
			break;
		case 4:
			menuPrincipalOpcion4(scanner);
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

	private static void menuPrincipalOpcion1(Scanner scanner) {
		mostrarTituloSeparador("CARGA DE PATH DIRECTORIO");
		System.out.print("\nIngrese el path del directorio: ");
		do {
			pathDirectorio = scanner.nextLine();
		} while (pathDirectorio.trim().isEmpty());
		pathDirectorioCargado = true;
	}
	
	private static void menuPrincipalOpcion2(Scanner scanner) {
		if (pathDirectorioCargado) {
			String fullPathArchivo = pathDirectorio + File.separator + CARGA_CSV;
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

	private static void menuPrincipalOpcion3(Scanner scanner) {
		if (estructurasCargadas) {
			mostrarTituloSeparador("BUSCAR VALOR EN ESTRUCTURAS");
			System.out.println("\n\t A - Lista Doble Enlazada");
			System.out.println("\t B - Tabla Hash");
			System.out.println("\t C - Arbol Binario de Busqueda");
			System.out.println("\t D - Arbol AVL\n");
			System.out.print("\nSeleccione una estructura de datos: \n");
			String seleccionEstructuraDatos = null;
			do{
				seleccionEstructuraDatos = scanner.nextLine();
			}while(seleccionEstructuraDatos.trim().isEmpty());
			
			TipoEstructura tipoEstructura = resolverTipoEstructuraDatos(seleccionEstructuraDatos);
			
			System.out.println();
			System.out.print("Ingrese el codigo de busqueda: ");
			Integer codigoBuscado =  null;
			
			do{
				codigoBuscado = scanner.nextInt();
			}while(codigoBuscado == null);
			
			IEstructuraDeDatos<Registro> estructuraDeDatos = estructurasMap.get(tipoEstructura);
			Registro registroResultado = estructuraDeDatos.buscar(new Registro(codigoBuscado, ""));
			System.out.println();
			mostrarTituloSeparador("BUSQUEDA DE VALOR: " + codigoBuscado + "\t\tTipo ED: " + tipoEstructura);
			System.out.println("\t\t" + "Resultado ==>" + "\tCodigo: " + registroResultado.getCodigo() + "\t Nombre: "
					+ registroResultado.getNombreCompleto());
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}

	private static TipoEstructura resolverTipoEstructuraDatos(String seleccionEstructuraDatos) {
		TipoEstructura tipoEstructura = null;
		switch (seleccionEstructuraDatos) {
		case "A":
			tipoEstructura = TipoEstructura.LISTA_DOB_ENLAZADA;
			break;
		case "B":
			tipoEstructura = TipoEstructura.TABLA_HASH;
			break;
		case "C":
			tipoEstructura = TipoEstructura.ABB;
			break;
		case "D":
			tipoEstructura = TipoEstructura.AAVL;
			break;
		default:
			break;
		}
		return tipoEstructura;
	}

	private static void menuPrincipalOpcion4(Scanner scanner) {
		if (estructurasCargadas) {
			String fullPathArchivo = pathDirectorio + File.separator + CONSULTA_CSV;
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
			abb.imprimirPorNiveles();
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
