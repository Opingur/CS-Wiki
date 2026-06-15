package Walrus;

public class IntList {
    public int item;
    public IntList next;        

    public IntList(int f, IntList r) {
        item = f;
        next = r;
    }
    public static void main(String[] args) {
        IntList L = new IntList(15, null);
        L = new IntList(10, L);
        L = new IntList(5, L);

        System.out.println(L.item);          
        System.out.println(L.next.item);     
        System.out.println(L.next.next.item);
    } 
    public int size() {
        if (next == null){
            return 1;
        }
        return 1 + this.next.size();
    }
    // count the elements
    public int iterativeSize() {
        IntList p = this;
        int total = 0;
        while (p != null) {
            total++;
            p = p.next;
        }
        return total;
    }
    //get specific element in loop
    public int get (int i) {
        IntList p = this;
        int index = 0;
        while (p != null && index != i) {
            index++;
            p = p.next;
        }
        if (p != null) {
            return p.item;
        } else {
            return 0;
        }
    }
    //get specific element in recurision
    public int get_recurse (int j) {
        if (j == 0) {
            return item;
        } else {
            return next.get_recurse (j - 1);
        }
    }
}
