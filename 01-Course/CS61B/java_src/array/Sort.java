package array;

public class Sort {
    /** Sorts strings destructively. */
    public static int findSmallest(String[] s, int start) {
        String smallest = s[start];
        int smallestIndex = 0;
        for (int i = start; i < s.length; i++) {
            if (s[i].compareTo(smallest) < 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    public static void swap(String[] s, int a, int b) {
        if (a == b) return;
        String temp = s[a];
        s[a] = s[b];
        s[b] = temp;
    }

    public static void sort(String[] s) {
        for (int i = 0; i < s.length; i++) {
            int small = findSmallest(s, i);
            swap(s, i, small);
        }
    }
}