package hw3.hash;

import java.util.HashSet;
import java.util.Set;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

//        double scale = 1.0;
//        int N = 50;
//        int M = 10;

        double scale = 0.5;
        int N = 2000;
        int M = 100;

        HashTableDrawingUtility.setScale(scale);
//        Set<Oomage> oomies = new HashSet<Oomage>();
//        for (int i = 0; i < N; i += 1) {
//            oomies.add(SimpleOomage.randomSimpleOomage());
//        }
//        visualize(oomies, M, scale);

        Set<ComplexOomage> coomies = new HashSet<ComplexOomage>();
        for (int i = 0; i < N; i += 1) {
            coomies.add(ComplexOomage.randomComplexOomage());
        }
        visualize(coomies, M, scale);
    }

    private static int bucketHash(int hashCode, int M) {
        return (hashCode & 0x7fffffff) % M;
    }

    public static void visualize(Set<? extends Oomage> set, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);

        /* TODO: Create a visualization of the given hash table. Use
           du.xCoord and du.yCoord to figure out where to draw
           Oomages.
         */

        int[] bucketIndices = new int[M];

        for (Oomage o: set) {
            int hash = o.hashCode();
            int bucketNumber = bucketHash(hash, M);

            int xIndex = bucketIndices[bucketNumber];
            o.draw(HashTableDrawingUtility.xCoord(xIndex),
                    HashTableDrawingUtility.yCoord(bucketNumber, M), scale);

            bucketIndices[bucketNumber] += 1;
        }

        /* When done with visualizer, be sure to try 
           scale = 0.5, N = 2000, M = 100. */           
    }
} 
