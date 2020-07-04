/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDA;

import java.util.Iterator;


/**
 *
 * @author i7
 */
public  class LCDE <E> {
  
    int size=0;
    NodeList <E> fin;
    
    public E get(int indice) {
        int indexer = 0;
        if (indice > this.size - 1) {
            return null;
        } else {
            for (NodeList<E> n = fin.getNext(); n != null; n = n.getNext()) {
                if (indexer == indice) {
                    return n.getContent();
                }
                indexer++;
            }
        }
        return null;

    }
    public E set(int indice, E nuevo){
        NodeList <E> anterior = new NodeList(null);
        NodeList <E> referencia = fin.getNext();
        if(nuevo==null){
            System.out.println("Dato incorrecto.");
            return null;
        }
        if(indice<size){
            for( int contador=0; contador<indice; contador++){
                anterior=referencia.getNext();
                referencia.getNext().setContent(nuevo);
            }
            return anterior.getContent();
        }else{
            return null;
        }
    }
    public E removefirst(){
        /*
        if(isEmpty()){
            return null;
        }*/
        NodeList<E> referencia =  fin.getNext();
        fin.setNext(fin.getNext().getNext());
        fin.getNext().setPrevious(null);
        fin.getNext().setNext(null);
        size--;
        return referencia.getContent();
    }
    public E removeLast(){
        /*
         if(isEmpty()){
            return null;
        }*/
        NodeList<E> referencia = fin;
        fin.getPrevious().setNext(fin.getNext());
        fin.getNext().setPrevious(fin.getPrevious());
        fin=fin.getPrevious();
        fin.setNext(null);
        fin.setPrevious(null);
        size--;
        return referencia.getContent();
        
    }
    public ListIterator<E> Iterator(){
          ListIterator <E> it=  new ListIterator(){
              NodeList <E> n1=fin.getNext();
              NodeList <E> n2 = fin;
              @Override
              public boolean hasNext() {
                  return n1 != null;
              }

              @Override
              public boolean hasPrevious() {
                  return n2 !=null;
              }

              @Override
              public E next() {
                   E content = n1.getContent();
                   n1=n1.getNext();
                   return content;
              }

              @Override
              public E previous() {
                  E content = n2.getContent();
                   n2=n2.getPrevious();
                   return content;
              }
              
          };
          return it;
    }
    }
