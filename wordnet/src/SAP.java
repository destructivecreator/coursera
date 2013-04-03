

import java.util.ArrayDeque;
import java.util.Deque; 
 
public class SAP {

    private Digraph G; 
    
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }
        
    private boolean isValid(int v) {
        return (v >= 0 && v <= this.G.V() - 1);
    }
    
    private boolean isValid(Iterable<Integer> v, Iterable<Integer> w) {

        for (Integer integer : w) {
            if (!isValid(integer)) {
                return false;
            }
        }

        for (Integer integer : v) {
            if (!isValid(integer)) {
                return false;
            }
        }

        return true;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        if (!((v >= 0 && v <= this.G.V() - 1) && (w >= 0 && w <= this.G.V() - 1))) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        int ancestor = ancestor(v, w);
        int pathLength;
        if (ancestor == - 1) {
            pathLength = -1;
        } else {
            pathLength = bfsV.distTo(ancestor) + bfsW.distTo(ancestor);
        }


        return pathLength;
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        if (!((v >= 0 && v <= this.G.V() - 1) && (w >= 0 && w <= this.G.V() - 1))) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int shortesAncestor = -1;
        int shortesPath = Integer.MAX_VALUE;
        Deque<Integer> ancestors = new ArrayDeque<Integer>();

        for (int i = 0; i < this.G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                ancestors.push(i);
            }
        }

        for (Integer integer : ancestors) {
            if ((bfsV.distTo(integer) + bfsW.distTo(integer)) < shortesPath) {
                shortesPath = (bfsV.distTo(integer) + bfsW.distTo(integer));
                shortesAncestor = integer;
            }
        }
        return shortesAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        if (!isValid(v, w)) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        int ancestor = ancestor(v, w);
        int pathLength;
        if (ancestor == - 1) {
            pathLength = -1;
        } else {
            pathLength = bfsV.distTo(ancestor) + bfsW.distTo(ancestor); //we start from -2 to exclude the edge vertices
        }


        return pathLength;

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int shortestPath = Integer.MAX_VALUE;
        Deque<Integer> ancestors = new ArrayDeque<Integer>();
        int ancestor = -1;

        if (!isValid(v, w)) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        for (int i = 0; i < this.G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                ancestors.push(i);
            }
        }

        for (Integer integer : ancestors) {
            if ((bfsV.distTo(integer) + bfsW.distTo(integer)) < shortestPath) {
                shortestPath = (bfsV.distTo(integer) + bfsW.distTo(integer));
                ancestor = integer;
            }
        }
        return ancestor;

    }
 
    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        


    }
}
