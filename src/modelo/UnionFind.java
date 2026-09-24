package modelo;

public class UnionFind {
    private int[] padre;

    public UnionFind(int n) {
        padre = new int[n];
        for (int i = 0; i < n; i++) {
            padre[i] = i;
        }
    }

    public int find(int i) {
        if (padre[i] == i) {
            return i;
        }
        return padre[i] = find(padre[i]); // Compresión de caminos
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI != rootJ) {
            padre[rootI] = rootJ;
            return true;
        }
        return false;
    }
}