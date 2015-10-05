package org.ues21.taed2.principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ues21.taed2.estructura.Nodo.NodoArbol;

class GraficadorArbol {

    public static <T extends Comparable<?>> void printNode(NodoArbol<T> root) {
        int maxLevel = GraficadorArbol.maxNivel(root);

        imprimirNodoInterno(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void imprimirNodoInterno(List<NodoArbol<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || GraficadorArbol.sonTodosNulos(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        GraficadorArbol.imprimirEspaciosEnBlanco(firstSpaces);

        List<NodoArbol<T>> newNodes = new ArrayList<NodoArbol<T>>();
        for (NodoArbol<T> node : nodes) {
            if (node != null) {
                System.out.print(node.getDatos());
                newNodes.add(node.getIzquierdo());
                newNodes.add(node.getDerecho());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            GraficadorArbol.imprimirEspaciosEnBlanco(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                GraficadorArbol.imprimirEspaciosEnBlanco(firstSpaces - i);
                if (nodes.get(j) == null) {
                    GraficadorArbol.imprimirEspaciosEnBlanco(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getIzquierdo() != null)
                    System.out.print("/");
                else
                    GraficadorArbol.imprimirEspaciosEnBlanco(1);

                GraficadorArbol.imprimirEspaciosEnBlanco(i + i - 1);

                if (nodes.get(j).getDerecho() != null)
                    System.out.print("\\");
                else
                    GraficadorArbol.imprimirEspaciosEnBlanco(1);

                GraficadorArbol.imprimirEspaciosEnBlanco(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        imprimirNodoInterno(newNodes, level + 1, maxLevel);
    }

    private static void imprimirEspaciosEnBlanco(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxNivel(NodoArbol<T> node) {
        if (node == null)
            return 0;

        return Math.max(GraficadorArbol.maxNivel(node.getIzquierdo()), GraficadorArbol.maxNivel(node.getDerecho())) + 1;
    }

    private static <T> boolean sonTodosNulos(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}