package tareaheap;

import java.lang.reflect.Array;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CD
 */
public class minHeapArray<T> implements MinHeap<T>{
    class Nodo<T> implements Comparable<Nodo<T>>  {
        
        T elem;
        Comparable prioridad;

        public Nodo(T elem, Comparable prioridad) {
            this.elem = elem;
            this.prioridad = prioridad;
        }
        
        @Override
        public int compareTo(Nodo<T> o) {
            return prioridad.compareTo(o.prioridad);
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append("\t(P ").append(prioridad).append(',');
            sb.append("E: ").append(elem.toString()).append(')');
            return sb.toString();
        }
        
    }
    private int cant;
    private int tam;
    private Nodo<T>[] heap;
    
    
    public minHeapArray() {
        cant = 0;
        tam = 100;
        heap = (Nodo<T>[]) Array.newInstance(Nodo.class, 100);
    }

    private void expand(){
        Nodo<T>[] newArray = (Nodo<T>[]) Array.newInstance(Nodo.class, 2*tam);
        for(int i = 1; i <= cant; ++i)
            newArray[i] = heap[i];
        heap = newArray;
        tam*=2;
    }
    
    @Override
    public void insert(T elem, Comparable prioridad) {
        Nodo<T> nuevo = new Nodo<>(elem, prioridad);
        if(2+cant >=tam)
            expand();
        
        boolean inPlace = false;
        Nodo<T> aux;
        int pos= ++cant, posFather = cant/2;
        heap[pos]=nuevo;
        while(posFather>0 && !inPlace){
            if(heap[posFather].compareTo(nuevo) > 0){
                swap(posFather, pos);
                pos = posFather;
                posFather/=2;
            }
            else 
               inPlace = true;
        }
    }
    
    public void insert(T elem) {
        if(elem == null)
            throw new NullPointerException();
        Nodo<T> nuevo = new Nodo<>(elem,(Comparable)elem);
        if(2+cant >=tam) expand();
        boolean inPlace = false;
        int pos= ++cant, posFather = cant/2;
        heap[pos]=nuevo;
        while(posFather>0 && !inPlace){
            if(heap[posFather].compareTo(nuevo) > 0){
                swap(posFather, pos);
                pos = posFather;
                posFather/=2;
            }
            else 
               inPlace = true;
        }
    }

    @Override
    public T getMin() {
        return heap[1].elem;
    }
    
    private void swap(int pos1, int pos2){
        Nodo<T> aux;
        aux = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = aux;
    }
    
    
    @Override
    public T eliminateMin() {
        if(cant == 0)
            return null;
        T resp = heap[1].elem;
        heap[1] = heap[cant];
        heap[cant] = null;
        --cant;
        
        int pos = 1;
        boolean izq = false, der = false;
        if(heap[2*pos]!= null)
            izq = heap[pos].compareTo(heap[2*pos])>0;
        if(heap[2*pos+1]!= null)
            der = heap[pos].compareTo(heap[2*pos+1])>0;
        
        while(izq || der){
            if(izq && der){
                if(heap[2*pos].compareTo(heap[2*pos+1]) > 0){
                    swap(pos,2*pos+1);
                    pos = 2*pos+1;
                }
                else{
                    swap(pos, 2*pos);
                    pos = 2*pos;
                }
            }
            else if(izq){
                swap(pos, 2*pos);
                    pos = 2*pos;
            }
            else{
                swap(pos, 2*pos+1);
                    pos = 2*pos+1;
            }
            izq= false; der =false;
            if(2*pos <= cant){
                if(heap[2*pos]!= null)
                    izq = heap[pos].compareTo(heap[2*pos])>0;
                if(heap[2*pos+1]!= null)
                    der = heap[pos].compareTo(heap[2*pos+1])>0;
            }
        }
        return resp;
    }
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int l = (int)(Math.log(cant)/Math.log(2));
        int ini, fin;
        for(int i = 0; i <= l; ++i){
            ini = (int)(Math.pow(2, i));
            if(i==l)
                fin = cant+1;
            else
                fin = (int)(Math.pow(2, i+1));
            sb.append("nivel ").append(i).append(":");
            for(int j = ini; j<fin; ++j)
                sb.append(heap[j].toString());
            sb.append('\n');
        }
        return sb.toString();
    }
    
    public static <T extends Comparable<T>> void heapSort(T[] arreglo, int ini, int fin){
        minHeapArray<T> heap = new minHeapArray<>();
        for(int i = ini; i < fin; ++i)
            heap.insert(arreglo[i]);
        for(int i = ini; i < fin; ++i)
            arreglo[i] = heap.eliminateMin();
    }
    
}
