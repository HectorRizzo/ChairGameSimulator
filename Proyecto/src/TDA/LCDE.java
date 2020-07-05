
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
     public E get(int indice) {
        int indexer = 0;
        if (indice >size - 1) {
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
        if(index==0){
            addFirst(content);
        }
        else if (!(index>size)) {
            
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
        
        if(isEmpty()){
            return null;
        }else{
        NodeList<E> referencia =  fin.getNext();
        fin.setNext(fin.getNext().getNext());
        fin.getNext().setPrevious(null);
        fin.getNext().setNext(null);
        size--;
        return referencia.getContent();
        }
    }
    public E removeLast(){
        
         if(isEmpty()){
            return null;
        }else{
        NodeList<E> referencia = fin;
        fin.getPrevious().setNext(fin.getNext());
        fin.getNext().setPrevious(fin.getPrevious());
        fin=fin.getPrevious();
        fin.setNext(null);
        fin.setPrevious(null);
        size--;
        return referencia.getContent();
         }
        
    }
    /*
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
*/
}
