

public class LCDE <E> {
    int size=0;
    NodeList <E> fin;                   //unico nodo

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
            return "null";

        }
        //si solo tiene un elemento devuelve solo el string de éste
        if(size==1){
            return fin.getContent().toString();
        }
        String cadena="";
        NodeList aux=fin.getNext();
        //recorre los nodos mientras no llegue al nodo final y agrega el contenido a la cadena
        for(int i= 0; i<size-1;i++){
                cadena+=aux.getContent().toString()+" ";
                aux=aux.getNext();
        }
        cadena+=fin.getContent().toString();            //se agrega el contenido del nodo final
        return cadena;
    }

}
