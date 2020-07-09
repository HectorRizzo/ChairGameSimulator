/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author i7
 */
public class ListDoubleC<E> {

    int size = 0;
    NodeList<E> ultime;

    public ListDoubleC() {
        this.ultime = null;
    }
    
    
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean addFirst(E content) {
         NodeList<E> nuevo = new NodeList(content);
        if(content==null){
            return false;
        } else if (isEmpty()) {
            ultime = nuevo;
            ultime.setNext(ultime);
            ultime.setPrevious(ultime);
            size++;
            return true;
        } else {
            NodeList<E> aux = ultime.getNext();
            nuevo.setNext(aux);
            aux.setPrevious(nuevo);
            ultime.setNext(nuevo);
            nuevo.setPrevious(ultime);
            size++;
            return true;
        }
    }

    public int clear() {
        if (isEmpty()) {
            return -1;
        }
        NodeList<E> temp = ultime.getNext();
        NodeList<E> nextNode;
        //recorre los nodos mientras no llegue al ultimo y los pone en null
        for (int i = 0; i < size - 1; i++) {
            nextNode = temp.getNext();
            temp.setNext(null);
            temp.setPrevious(null);
            temp.setContent(null);
            temp = nextNode;
        }
        ultime = null;               //fija al ultimo nodo en null
        size = 0;
        return 0;
    }

    public E removeLast() {

        if (isEmpty()) {
            return null;
        } else {
           NodeList <E> copy = ultime;
           ultime.getPrevious().setNext(ultime.getNext());
           ultime.getNext().setPrevious(ultime.getPrevious());
           ultime=copy.getPrevious();
           copy.setNext(null);
           copy.setPrevious(null);
           size--;
           return copy.getContent();
        }

    }

    public E remove(int index) {

        if (isEmpty()) {
            return null;
        } else {
            NodeList<E> n = ultime.getNext();
            if (index < size) {
                for (int i = 0; i < index; i++) {
                    n = n.getNext();
                }

            }
            E content = n.getContent();
            n.getPrevious().setNext(n.getNext());
            n.getNext().setPrevious(n.getPrevious());
            n.setContent(null);
            n = null;
            size--;
            return content;

        }
    }

    public boolean addLast(E content) {
       NodeList<E> newLast = new NodeList(content);
        if(content==null){
            return false;
        } else if (isEmpty()) {
            ultime = newLast;
            ultime.setNext(ultime);
            ultime.setPrevious(ultime);
            size++;
            return true;
        } else {
            NodeList<E> aux = ultime;
            newLast.setNext(aux.getNext());
            newLast.getNext().setPrevious(newLast);
            aux.setNext(newLast);
            newLast.setPrevious(aux);
            ultime = newLast;
            size++;
            return true;
        }
    }

    public E removefirst() {

        if (isEmpty()) {
            return null;
        } else {
            NodeList<E> referencia = ultime.getNext();
            ultime.setNext(ultime.getNext().getNext());
            ultime.getNext().getNext().setPrevious(ultime);
            referencia.setNext(null);
            referencia.setPrevious(null);
           
            size--;
            return referencia.getContent();
        }
    }

    public E set(int indice, E nuevo) {
        NodeList<E> previous = new NodeList(null);
        NodeList<E> referency = ultime.getNext();
        if (nuevo == null) {
            System.out.println("Dato incorrecto.");
            return null;
        }
        if (indice < size) {
            for (int count = 0; count < indice; count++) {
                previous = referency.getNext();
                referency.getNext().setContent(nuevo);
            }
            return previous.getContent();
        } else {
            return null;
        }
    }

    public E get(int indice) {
        int indexer = 0;
        if (indice > size - 1) {
            return null;
        } else {
            for (NodeList<E> n = ultime.getNext(); n != null; n = n.getNext()) {

                if (indexer == indice) {
                    return n.getContent();
                }
                indexer++;
            }
        }
        return null;

    }

    @Override
    public String toString() {
        //comprueba que la lista no esté vacía
        if (isEmpty()) {
            return "[ null ]";
        }
        //si solo tiene un elemento devuelve solo el string de éste
        if (size == 1) {
            return ultime.getContent().toString();
        }
        String chain = "";
        NodeList aux = ultime.getNext();
        //System.out.println("fin "+fin.getNext().getContent().toString());
        //recorre los nodos mientras no llegue al nodo final y agrega el contenido a la cadena
        for (int i = 0; i < size - 1; i++) {
            chain += aux.getContent() + " ";
            aux = aux.getNext();
        }
        chain += ultime.getContent().toString();            //se agrega el contenido del nodo final
        return chain;
    }

    public int size() {
        return size;
    }
    /*
    public ListIterator<E> Iterator(){
          ListIterator <E> it=  new ListIterator(){
              NodeList <E> n1=ultime.getNext();
              NodeList <E> n2 = ultime;
              int count=0;
              @Override
              public boolean Limit() {
                  return count!=size;
              }

              @Override
              public E next() {
                   E content = n1.getContent();
                   n1=n1.getNext();
                   count++;
                   return content;
              }

              @Override
              public E previous() {
                  E content = n2.getContent();
                   n2=n2.getPrevious();
                   count++;
                   return content;
              }

          };
          return it;
    }

     */
}
