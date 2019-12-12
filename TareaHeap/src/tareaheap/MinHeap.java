/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaheap;

/**
 *
 * @author CD
 * @param <T>
 */
public interface MinHeap<T>{
    public void insert(T elem, Comparable prioridad);
    public T getMin();
    public T eliminateMin();
}
