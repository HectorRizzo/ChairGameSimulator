
package TDA;

/**
 *
 * @author Jocelyn Chicaiza
 */
public class LCDE<E> {

    private int size = 0;
    private NodeList<E> fin;

    public LCDE() {
        fin = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E addFirst(E content) {
        NodeList<E> nuevo = new NodeList(content);
        if (isEmpty()) {
            fin = nuevo;
            fin.setNext(fin);
            fin.setPrevious(fin);
            size++;
            return fin.getContent();
        } else {
            NodeList<E> aux = fin.getNext();
            nuevo.setNext(aux);
            aux.setPrevious(nuevo);
            fin.setNext(nuevo);
            nuevo.setPrevious(fin);
            size++;
            return aux.getContent();
        }
    }

    public E addLast(E content) {
        NodeList<E> nuevo = new NodeList(content);
        if (isEmpty()) {
            fin = nuevo;
            fin.setNext(fin);
            fin.setPrevious(fin);
            size++;
            return fin.getContent();
        } else {
            NodeList<E> aux = fin;
            nuevo.setNext(aux.getPrevious());
            aux.getPrevious().setPrevious(nuevo);
            aux.setNext(nuevo);
            nuevo.setPrevious(aux);

            fin = nuevo;
            size++;
            return aux.getContent();
        }

    }

    public void add(int index, E content) {

        NodeList nuevo = new NodeList(content);
        NodeList<E> n = fin; //Nodo viajero
        if (index<size()) {
            
            for (int i = 0; i < index; i++) {
                n = n.getNext();
            }
            NodeList aux = n;
            NodeList nextAux = n.getNext();
            aux.setNext(nuevo);
            nuevo.setPrevious(aux);
            nuevo.setNext(nextAux);
            nextAux.setPrevious(nuevo);
            size++;

        }

    }

    public int size() {
        return size;
    }

    public E remove(int index) {

        if (isEmpty()) {
            return null;
        } else {

            NodeList<E> n = fin.getNext();
            if (index<size()) {
                for (int i = 0; i < index; i++) {
                    n = n.getNext();
                }

            }
            E content = n.getContent();
            n.getPrevious().setNext(n.getNext());
            n.getNext().setPrevious(n.getPrevious());
            n=null;
            return content;

        }
    }
}
