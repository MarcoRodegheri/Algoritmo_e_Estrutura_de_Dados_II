package edu.rodegheri;

import java.util.Random;

public class Quicksort {
    private int[] data;

    public Quicksort(int[] data) {
        this.data = data;
    }

    public void sort() {
        sort(0, data.length - 1);
    }

    public void sort(int p, int r) {
        if (p < r) {
            int q = partition(p, r);
            sort(p, q - 1);
            sort(q + 1, r);
        }
    }

    private int partition(int p, int r) {
        int q = p;
        for (int j = p; j < r; j++) {
            if (data[j] <= data[r]) {
                swap(j, q);
                q++;
            }
        }
        swap(r, q);
        return q;
    }

    private void swap(int p1, int p2) {
        int tmp = data[p1];
        data[p1] = data[p2];
        data[p2] = tmp;
    }

    public static void main(String args[]) {
        Random r = new Random();
        int MAX = 10000;
        int[] data = new int[MAX];
        for (int i = 0; i < data.length; i++)
            data[i] = r.nextInt(data.length * 10);

        Quicksort qs = new Quicksort(data);
        long start = System.nanoTime();
        qs.sort();
        long end = System.nanoTime();
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
            System.out.println();
        }
        System.out.printf("Tempo para ordenar %d elementos: %d ns\n", MAX, end - start);
    }
}
