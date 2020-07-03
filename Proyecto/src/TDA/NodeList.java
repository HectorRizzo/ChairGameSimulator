package TDA;



public class NodeList<E> {
    private E content;
    private NodeList<E> next;
    private NodeList<E> previous;

    public NodeList(E content) {
        this.content = content;
        this.next = null;
    }

    public NodeList(E content, NodeList<E> next, NodeList<E> previous) {
        this.content = content;
        this.next = next;
        this.previous = previous;
    }

    public E getContent() {
        return content;
    }

    public NodeList<E> getNext() {
        return next;
    }

    public NodeList<E> getPrevious() {
        return previous;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public void setNext(NodeList<E> next) {
        this.next = next;
    }

    public void setPrevious(NodeList<E> previous) {
        this.previous = previous;
    }
    
    
}