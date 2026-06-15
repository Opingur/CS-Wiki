package SLList;

public class SLList {
    private IntNode sentinel;
    private int size;
    private IntNode last;

    public SLList() {
        sentinel = new IntNode(52, null);
        last = sentinel;
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntNode(52,null);
        //create a sentinel node
        sentinel.next = new IntNode(x, null);
        last = sentinel.next;
        size = 1;
    }

    public int getFirst() {
        return sentinel.next.item;
    }

    public void addFirst(int x) {
        size++;
        sentinel.next = new IntNode(x, sentinel.next);
    }

    public void addLast(int x) {
        size++;
        last.next = new IntNode(x, null);
        last = last.next;
    }

    public int size() {
        return size;
    }
}
