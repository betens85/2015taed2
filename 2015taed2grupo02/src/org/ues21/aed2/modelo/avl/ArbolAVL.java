/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ues21.aed2.modelo.avl;

/**
 *
 * @author agustin
 */
public class ArbolAVL {

    private NodoAVL raiz;
    /**
     * La variable indica si el árbol crecio en altura
     */
    private boolean h;

    public ArbolAVL() {
        this.raiz = null;
    }

    public ArbolAVL(NodoAVL raiz) {
        this.raiz = raiz;
    }

    /**
     * @return the raiz
     */
    public NodoAVL getRaiz() {
        return raiz;
    }

    /**
     * @param raiz the raiz to set
     */
    public void setRaiz(NodoAVL raiz) {
        this.raiz = raiz;
    }

    /**
     * @return the h
     */
    public boolean isH() {
        return h;
    }

    /**
     * @param h the h to set
     */
    public void setH(boolean h) {
        this.h = h;
    }

    public void insertar(int x) {
        h = false;
        raiz = insertarAvl(raiz, x);
    }

    private NodoAVL insertarAvl(NodoAVL p, int x) {
        NodoAVL p1, p2;

        if (p == null) {
            p = new NodoAVL(null, x, 0, null);
            h = true;
        } else if (x < (int) p.getInfo()) {
            p.setIzquierdo(insertarAvl((NodoAVL) p.getIzquierdo(), x));
            if (h == true) {
                //Crecio la rama izquierda
                
                switch (p.getEqui()) {
                    case 0:
                        p.setEqui(-1);
                        break;
                    case 1:
                        p.setEqui(0);
                        h = false;
                        break;
                    case -1:
                        //Reequilibrar
                        p1 = (NodoAVL) p.getIzquierdo();
                        if (p1.getEqui() == -1) {
                            //Rotación izquierda simple
                            p.setIzquierdo(p1.getDerecho());
                            p1.setDerecho(p);
                            p.setEqui(0);
                            p = p1;
                        } else {
                            //Rotación izquierda doble
                            p2 = (NodoAVL) p1.getDerecho();
                            p1.setDerecho(p2.getIzquierdo());
                            p2.setIzquierdo(p1);
                            p.setIzquierdo(p2.getDerecho());
                            p2.setDerecho(p);
                            p = p2;
                            //p1.dcho = p2.izdo;
                            //p2.izdo = p1;
                            //p.izq = p2.dcho;
                            //p2.dcho = p;
                            //p = p2
                        }
                        p.setEqui(0);
                        h = false;
                        break;
                } //Fin switch

            } // fin if(h == true)
        } // fin if(x < (int) p.getInfo())
        else if (x > (int) p.getInfo()) {
            p.setDerecho(insertarAvl((NodoAVL) p.getDerecho(), x));
            if (h == true) {
                //Crecio la rama derecha

                switch (p.getEqui()) {
                    case 0:
                        p.setEqui(1);
                        break;
                    case 1:
                        //Reequilibrar
                        p1 = (NodoAVL) p.getDerecho();
                        if (p1.getEqui() == 1) {
                            //Rotación derecha simple
                            p.setDerecho(p1.getIzquierdo());
                            p1.setIzquierdo(p);
                            p.setEqui(0);
                            p = p1;
                        } else {
                            //Rotación derecha doble

                            p2 = (NodoAVL) p1.getIzquierdo();
                            p1.setIzquierdo(p2.getDerecho());
                            p2.setDerecho(p1);
                            p.setDerecho(p2.getIzquierdo());
                            p2.setIzquierdo(p);
                            p = p2;

                        }
                        p.setEqui(0);
                        h = false;
                        break;
                    case -1:
                        p.setEqui(0);
                        h = false;
                        break;
                }
            }
        } else {
            //La clave ya existe no hago nada
            h = false;
        }

        return p;
    }

}
