package org.ues21.taed2.principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ues21.taed2.estructura.Nodo.NodoArbol;

class GraficadorArbol {

    public static <T extends Comparable<?>> void printNode(NodoArbol<T> root) {
        int nivelMaximo = GraficadorArbol.maximoNivel(root);

        imprimirNodoInterno(Collections.singletonList(root), 1, nivelMaximo);
    }

    private static <T extends Comparable<?>> void imprimirNodoInterno(List<NodoArbol<T>> nodos, int nivel, int nivelMaximo) {
        if (nodos.isEmpty() || GraficadorArbol.sonTodosNulos(nodos))
            return;

        int piso = nivelMaximo - nivel;
        int lineasLimite = (int) Math.pow(2, (Math.max(piso - 1, 0)));
        int primerosEspacios = (int) Math.pow(2, (piso)) - 1;
        int espaciosIntermedios = (int) Math.pow(2, (piso + 1)) - 1;

        GraficadorArbol.imprimirEspaciosEnBlanco(primerosEspacios);

        List<NodoArbol<T>> nodosNuevos = new ArrayList<NodoArbol<T>>();
        for (NodoArbol<T> node : nodos) {
            if (node != null) {
                System.out.print(node.getDatos());
                nodosNuevos.add(node.getIzquierdo());
                nodosNuevos.add(node.getDerecho());
            } else {
                nodosNuevos.add(null);
                nodosNuevos.add(null);
                System.out.print(" ");
            }

            GraficadorArbol.imprimirEspaciosEnBlanco(espaciosIntermedios);
        }
        System.out.println("");

        for (int i = 1; i <= lineasLimite; i++) {
            for (int j = 0; j < nodos.size(); j++) {
                GraficadorArbol.imprimirEspaciosEnBlanco(primerosEspacios - i);
                if (nodos.get(j) == null) {
                    GraficadorArbol.imprimirEspaciosEnBlanco(lineasLimite + lineasLimite + i + 1);
                    continue;
                }

                if (nodos.get(j).getIzquierdo() != null)
                    System.out.print("/");
                else
                    GraficadorArbol.imprimirEspaciosEnBlanco(1);

                GraficadorArbol.imprimirEspaciosEnBlanco(i + i - 1);

                if (nodos.get(j).getDerecho() != null)
                    System.out.print("\\");
                else
                    GraficadorArbol.imprimirEspaciosEnBlanco(1);

                GraficadorArbol.imprimirEspaciosEnBlanco(lineasLimite + lineasLimite - i);
            }

            System.out.println("");
        }

        imprimirNodoInterno(nodosNuevos, nivel + 1, nivelMaximo);
    }

    private static void imprimirEspaciosEnBlanco(int cantidad) {
        for (int i = 0; i < cantidad; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maximoNivel(NodoArbol<T> nodo) {
        if (nodo == null)
            return 0;

        return Math.max(GraficadorArbol.maximoNivel(nodo.getIzquierdo()), GraficadorArbol.maximoNivel(nodo.getDerecho())) + 1;
    }

    private static <T> boolean sonTodosNulos(List<T> lista) {
        for (Object object : lista) {
            if (object != null)
                return false;
        }

        return true;
    }
}