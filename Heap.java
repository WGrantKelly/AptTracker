package P3;

/***************************************************************************************************************

*  the following code is made as an expansion of Sedgewick and Wayne's Heap.java code specified for apartments
*  For additional documentation, see <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
*  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
*  Author: Grant Kelly

****************************************************************************************************************/

public class Heap {

    // This class should not be instantiated.
    private Heap() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param pq the array to be sorted
     */
    public static Apartment[] sort(Apartment[] pq, String prioritizedElem) {

        int n = getSize(pq);
        Apartment[] auxhiliary = new Apartment[n];
        int count = -1;
        int auxhiliaryCount = -1;
        for(Apartment a : pq){
            count++;
            if(a!=null){
                auxhiliaryCount++;
                auxhiliary[auxhiliaryCount] = a;
                pq[count]=null; 
            }
        }
        if(n>1){
            for (int k = n/2; k >= 1; k--)
                sink(auxhiliary, k, n, prioritizedElem);
            while (n > 1) {
                exch(auxhiliary, 1, n--);
                sink(auxhiliary, 1, n, prioritizedElem);
            }
        }
        count = 0;
        for(Apartment a : auxhiliary){
                pq[count] = a;
                count++;
        }
        return pq;
    }

   /***************************************************************************
    * Helper functions to restore the heap invariant.
    ***************************************************************************/

    private static void sink(Apartment[] pq, int k, int n, String prioritizedElem) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(pq, j, j+1, prioritizedElem)) j++;
            if (!less(pq, k, j, prioritizedElem)) break;
            exch(pq, k, j);
            k = j;
        }
    }

   /***************************************************************************
    * Helper functions for comparisons and swaps.
    * Indices are "off-by-one" to support 1-based indexing.
    ***************************************************************************/
    private static boolean less(Apartment[] pq, int i, int j, String prioritizedElem) {
        if(prioritizedElem.equals("footage")){
            return((pq[i-1].getSqFootage())>(pq[j-1].getSqFootage()));
        }
        else if(prioritizedElem.equals("price")){
            return((pq[i-1].getPrice())<(pq[j-1].getPrice()));
        }
        else{
            System.exit(0);
        }
        //return pq[i-1].compareTo(pq[j-1]) < 0;
        return false;
    }

    private static void exch(Object[] pq, int i, int j) {
        Object swap = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = swap;
    }

    // print array to console
    public static void show(Apartment[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
    
    public static int getSize(Apartment[] a){
        int count = 0;
        for(Apartment b : a){
            if(b!=null){
                count++;
            }
        }
        return count;
    }
}
