

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
}