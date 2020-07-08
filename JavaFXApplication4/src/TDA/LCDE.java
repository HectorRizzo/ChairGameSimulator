package TDA;
/**
 *
 * @author Jocelyn Chicaiza
 */
public class LCDE<E> {

    private int size = 0;
    private NodeList<E> fin;                //unico nodo

    public LCDE() {
        fin = null;
    }

    public boolean addFirst(E content) {
        NodeList<E> nuevo = new NodeList(content);
        if(content==null){
            return false;
        } else if (isEmpty()) {
            fin = nuevo;
            fin.setNext(fin);
            fin.setPrevious(fin);
            size++;
            return true;
        } else {
            NodeList<E> aux = fin.getNext();
            nuevo.setNext(aux);
            aux.setPrevious(nuevo);
            fin.setNext(nuevo);
            nuevo.setPrevious(fin);
            size++;
            return true;
        }
    }

    public boolean addLast(E content) {
        NodeList<E> nuevo = new NodeList(content);
        if(content==null){
            return false;
        } else if (isEmpty()) {
            fin = nuevo;
            fin.setNext(fin);
            fin.setPrevious(fin);
            size++;
            return true;
        } else {
            NodeList<E> aux = fin;
            nuevo.setNext(aux.getNext());
            nuevo.getNext().setPrevious(nuevo);
            aux.setNext(nuevo);
            nuevo.setPrevious(aux);
            fin = nuevo;
            size++;
            return true;
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
            n.setContent(null);
            n=null;
            size--;
            return content;

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

            if (isEmpty()) {
            return null;
        } else {
            NodeList<E> referencia = fin.getNext();
            fin.setNext(fin.getNext().getNext());
            fin.getNext().getNext().setPrevious(fin);
            referencia.setNext(null);
            referencia.setPrevious(null);
           
            size--;
            return referencia.getContent();
        }
        }

    public E removeLast() {

        if (isEmpty()) {
            return null;
        } else {
            NodeList<E> copy = fin;
            fin.getPrevious().setNext(fin.getNext());
            fin.getNext().setPrevious(fin.getPrevious());
            fin = copy.getPrevious();
            copy.setNext(null);
            copy.setPrevious(null);
            size--;
            return copy.getContent();
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


    //retorna el tamaño de la lista
    public int size(){

        return size;
    }

    //verifica si la lista está vacía
    public boolean isEmpty(){
        return size <= 0;
    }

    //"Elimina" todos los elementos del linked
    public int clear() {
        if(isEmpty()){
            return -1;
        }
        NodeList <E> temp=fin.getNext();
        NodeList <E> nextNode;
        //recorre los nodos mientras no llegue al ultimo y los pone en null
        for(int i=0; i<size-1;i++){
            nextNode=temp.getNext();
            temp.setNext(null);
            temp.setPrevious(null);
            temp.setContent(null);
            temp=nextNode;
        }
        fin=null;               //fija al ultimo nodo en null
        size=0;
        return 0;
    }

    //retorna la lista en tipo string
    public String toString(){
        //comprueba que la lista no esté vacía
        if(isEmpty()){
            return "[ null ]";
        }
        //si solo tiene un elemento devuelve solo el string de éste
        if(size==1){
            return fin.getContent().toString();
        }
        String cadena="";
        NodeList aux=fin.getNext();
        //System.out.println("fin "+fin.getNext().getContent().toString());
        //recorre los nodos mientras no llegue al nodo final y agrega el contenido a la cadena
        for(int i= 0; i<size-1;i++){
            cadena+=aux.getContent().toString()+" ";
            aux=aux.getNext();
        }
        cadena+=fin.getContent().toString();            //se agrega el contenido del nodo final
        return cadena;
    }
}
