
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
