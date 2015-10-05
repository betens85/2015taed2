package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoArbol;

/**
 * @author grupo02
 *
 */
public class ArbolBinarioDeBusqueda<T extends Comparable<T>> implements IEstructuraDeDatos<T> {
	
	protected NodoArbol<T> nodoRaiz;

	@Override
	public T buscar(T datos) {
		NodoArbol<T> resultado = buscarNodo(nodoRaiz, datos);
		return resultado != null ? resultado.getDatos() : null;
	}

	@Override
	public void insertar(T datos) {
		if (estaVacia()) {
            this.nodoRaiz = new NodoArbol<>(datos);
        } else {
            insertar(datos, this.nodoRaiz);
        }
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.ABB;
	}
	
	@Override
	public boolean estaVacia() {
		return nodoRaiz == null;
	}
	
	public NodoArbol<T> getNodoRaiz() {
		return nodoRaiz;
	}
	
    private void insertar(T datos, NodoArbol<T> nodo) {
        if (datos.compareTo(nodo.getDatos()) > 0) {
            if (nodo.getDerecho() != null) {
                insertar(datos, nodo.getDerecho());
            } else {
                nodo.setDerecho(new NodoArbol<>(datos));
            }
        } else {
            if (nodo.getIzquierdo() != null) {
                insertar(datos, nodo.getIzquierdo());
            } else {
                nodo.setIzquierdo(new NodoArbol<T>(datos));
            }
        }
    }
    
    public String mostrarInOrden() {
        StringBuilder sb = new StringBuilder();
        mostrarInOrden(nodoRaiz, sb);
        return sb.toString();
    }

    private void mostrarInOrden(NodoArbol<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            mostrarInOrden(nodo.getIzquierdo(), sb);
            sb.append(nodo.getDatos());
            sb.append("  ");
            mostrarInOrden(nodo.getDerecho(), sb);
        }
    }
    
    public String mostrarPreOrden() {
    	StringBuilder sb = new StringBuilder();
        mostrarPreOrden(nodoRaiz, sb);
        return sb.toString();
    }

    private void mostrarPreOrden(NodoArbol<T> nodo, StringBuilder sb) {
        if (nodo != null) {
        	sb.append(nodo.getDatos());
            sb.append("  ");
            mostrarPreOrden(nodo.getIzquierdo(), sb);
            mostrarPreOrden(nodo.getDerecho(), sb);
        }
    }

    public String mostrarPostOrden() {
        StringBuilder sb = new StringBuilder();
        mostrarPostOrden(nodoRaiz, sb);
        return sb.toString();
    }

    private void mostrarPostOrden(NodoArbol<T> nodo, StringBuilder sb) {
        if (nodo != null) {
        	mostrarPostOrden(nodo.getIzquierdo(), sb);
        	mostrarPostOrden(nodo.getDerecho(), sb);
        	sb.append(nodo.getDatos());
            sb.append("  ");
        }
    }

    /**
     * Metodo que permite contar las hojas un arbol completo a partir de la raiz
     * @return la cantidad de hojas del arbol partiendo de la raiz
     */
    public int contarHojasDeArbol() {
        if (estaVacia()) {
            return 0;
        }

        return contarHojasDeArbol(nodoRaiz);
    }

    /**
     * Metodo recursivo que permite contar las hojas de un arbol
     * @param nodo a partir del cual se buscaran las hojas para ser contadas.
     * @return la cantidad de hojas
     */
    private int contarHojasDeArbol(NodoArbol<T> nodo) {
        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
            return 1;
        } else {
            int contadorIzquierdo = (nodo.getIzquierdo() != null ? contarHojasDeArbol(nodo.getIzquierdo()) : 0);
            int contadorDerecho = (nodo.getDerecho() != null ? contarHojasDeArbol(nodo.getDerecho()) : 0);
            return contadorIzquierdo + contadorDerecho;
        }
    }

    /**
     * Permite buscar el mayor nodo de arbol a partir de la raiz
     * @return el mayor nodo del arbol
     */
    public NodoArbol<T> buscarMayorNodoDeArbol() {
        NodoArbol<T> resultado = null;

        if (!estaVacia()) {
            if (nodoRaiz.getDerecho() != null) {
                resultado = buscarMayorNodoDeArbol(nodoRaiz.getDerecho());
            } else {
                resultado = nodoRaiz;
            }
        }

        return resultado;
    }

    /**
     * Metodo recursivo que permite encontrar el mayor nodo para un arbol
     * @param nodo el nodo raiz del arbol
     * @return el mayor nodo del arbol
     */
    private NodoArbol<T> buscarMayorNodoDeArbol(NodoArbol<T> nodo) {
        NodoArbol<T> resultado = null;
        if (nodo.getDerecho() != null) {
            //Recorremos nodos derechos (mayores) hasta que no exista derecho
            resultado = buscarMayorNodoDeArbol(nodo.getDerecho());
        } else { // el nodo actual no tiene mayor (nodo derecho) entonces es el mayor nodo del arbol
            resultado = nodo;
        }
        return resultado;
    }

    /**
     * Permite calcular la altura de un arbol a partir de su raiz
     * @return la altura del arbol
     */
    public int calcularAlturaDeArbol() {
        return calcularAltura(nodoRaiz);
    }

    /**
     * Metodo recursivo que permite calcular la altura de un arbol
     * @param nodo el nodo actual
     * @return la altura del arbol
     */
    private int calcularAltura(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        } else {
            int alturaIzquierda = calcularAltura(nodo.getIzquierdo());
            int alturaDerecha = calcularAltura(nodo.getDerecho());
            int alturaMaxima = Math.max(alturaIzquierda, alturaDerecha);
            return 1 + alturaMaxima;
        }
    }

    /**
     * Metodo que busca un nodo a partir de la raiz del arbol
     * @param datos los datos que son buscados en el arbol.
     * @return el nodo buscado
     */

    public NodoArbol<T> buscarNodo(T datos) {
        return buscarNodo(nodoRaiz, datos);
    }

    /**
     * Metodo recursivo de busqueda de uno nodo
     * @param nodo el nodo que referencia a un arbol
     * @param datos los datos que son buscados en el arbol.
     * @return el nodo buscado
     */
    private NodoArbol<T> buscarNodo(NodoArbol<T> nodo, T datos) {
        if (nodo != null) {
            //sin son iguales retorno el nodo buscado
            if (datos.compareTo(nodo.getDatos()) == 0) {
                return nodo;
            }

            //si es menor voy por los menores descendientes
            if (datos.compareTo(nodo.getDatos()) < 0) {
                nodo = buscarNodo(nodo.getIzquierdo(), datos);
            } else { //si es mayor voy por los mayores descendientes
                nodo = buscarNodo(nodo.getDerecho(), datos);
            }
        }

        return nodo;
    }

    /**
     * Metodo que retorna el mayor nodo de los menores descendientes para un nodo buscado a partir de datos pasados por
     * parametros
     * @param datos los datos que permite identificar el subarbol
     * @return el nodo de mayor valor dentro de los menores descendientes
     */
    public NodoArbol<T> obtenerMayorDeMenoresDescendientes(T datos){
        //buscamos el nodo a partir de datos
        NodoArbol<T> nodo = buscarNodo(nodoRaiz, datos);
        //buscamos el mayor nodo de subarbol, tomand como raiz el hijo izquierdo (todos menores descendientes)
        NodoArbol<T> resultado = buscarMayorNodoDeArbol(nodo.getIzquierdo());

        return resultado;
    }


    /**
     * Devuelve un listado de los valores de nodos junto a su correspodiente nivel en el que se encuentran
     * @return listado de nodos con niveles
     */
    public String mostrarNodoConNivelDesdeRaiz() {
        StringBuilder sb = new StringBuilder();
        mostrarNodoConNivel(nodoRaiz, sb);

        return sb.toString();
    }

    public void mostrarNodoConNivel(NodoArbol<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            mostrarNodoConNivel(nodo.getIzquierdo(), sb);
            sb.append(nodo.getDatos());
            sb.append("(" + obtenerAlturaDeNodo(nodoRaiz, nodo, 1) + ")");
            sb.append("  ");
            mostrarNodoConNivel(nodo.getDerecho(), sb);
        }
    }

    /**
     * Metodo recursivo que permite encontrar el nivel para un nodo pasado como parametro.
     * @param nodo el nodo que se comparara
     * @param nodoBuscado el nodo buscado
     * @param altura la altura acumulada
     * @return el nivel donde se encuentra el nodo buscado
     */
    public int obtenerAlturaDeNodo(NodoArbol<T> nodo, NodoArbol<T> nodoBuscado, int altura) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.getDatos().compareTo(nodoBuscado.getDatos()) == 0) {
            return altura;
        }

        //Chequea si el nodo buscado esta en el subarbol izquierdo
        int nivel = obtenerAlturaDeNodo(nodo.getIzquierdo(), nodoBuscado, altura + 1);
        if (nivel != 0) {
            return nivel;
        }

        //Chequea si el nodo buscado esta en el subarbol derecho
        return obtenerAlturaDeNodo(nodo.getDerecho(), nodoBuscado, altura + 1);
    }
}


