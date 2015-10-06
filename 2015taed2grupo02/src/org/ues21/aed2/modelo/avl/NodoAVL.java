/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ues21.aed2.modelo.avl;

import org.ues21.aed2.modelo.NodoArbol;

/**
 *
 * @author agustin
 * @param <T>
 */
public class NodoAVL<T> extends NodoArbol<T>{
    
    /**
     * El atributo equi hace referencia al factor de equilibrio del nodo
     */
    private int equi; // equi = hd - hi --> equi = 1 (inserte a derecha) si equi = -1 (inserte a izquierda)

    
    public NodoAVL(T info){
        super(info);
    }

    public NodoAVL(NodoAVL izquierdo, T info, int equi, NodoAVL derecho) {
        super(izquierdo, info, derecho);
        this.equi = equi;
    }


    /**
     * @return the equi
     */
    public int getEqui() {
        return equi;
    }

    /**
     * @param equi the equi to set
     */
    public void setEqui(int equi) {
        this.equi = equi;
    }
    
}
