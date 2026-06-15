package SLList;

public class IntNode {
    IntNode prev;
    int item;
    IntNode next;
    IntNode(int x, IntNode n) {
        next = n;
        item = x;
    }
}