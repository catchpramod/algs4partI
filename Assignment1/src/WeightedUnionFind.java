/**
 * Created by pramod on 1/25/16.
 */
public class WeightedUnionFind {
    private int[] parent;
    private int[] size;

    public WeightedUnionFind(int N) {
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }

    }

    private int find(int p) {
        while (p!=parent[p] ) {
            p = parent[p];
        }
        return p;
    }

    public boolean connected(int p, int q){
        return find(p)==find(q);
    }

    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);

        if(rootP==rootQ) return;

        if(size[rootP] > size[rootQ]){
            parent[rootQ]=rootP;
            size[rootP]+=size[rootQ];
        }else {
            parent[rootP]=rootQ;
            size[rootQ]+=size[rootP];
        }

    }
}
