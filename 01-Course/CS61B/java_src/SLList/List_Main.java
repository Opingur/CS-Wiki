package SLList;

public class List_Main {
    public static void main(String[] args) {
        SLList list = new SLList(1);
        list.addLast(6);
        list.addFirst(0);
        list.addLast(15);
        list.addFirst(11);
        System.out.println(list.size());
    }
}
