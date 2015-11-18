/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Dijkstra {
    private HashMap<Point, Integer> pToIndex = null;
    private HashMap<Integer, Point> iToPoint = null;
    private HashMap<String, Edge> kToEdge = null;
    Double[][] A;
    
    // Only for testing purposes to demostrate how the algorithm works
    public static void main(String[] args) {
        /*ArrayList<Point> points = new ArrayList<>();
        ArrayList<Edge>  edges  = new ArrayList <>();
        
        // The algorithm doesn't care about the various point
        // attributes, it just needs the set of points to treat
        // as nodes/vertices in the graph
        Point a = new Point();
        a.name = "a";
        Point b = new Point();
        b.name = "b";
        Point c = new Point();
        c.name = "c";
        Point d = new Point();
        d.name = "d";
        Point e = new Point();
        e.name = "e";
        Point f = new Point();
        f.name = "f";
        
        points.add(a);
        points.add(b);
        points.add(c);
        points.add(d);
        points.add(e);
        points.add(f);
        
        Edge edge = new Edge();
        edge.startPoint = a;
        edge.endPoint = b;
        edge.weight = 5;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = a;
        edge.endPoint = c;
        edge.weight = 20;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = a;
        edge.endPoint = f;
        edge.weight = 100;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = b;
        edge.endPoint = e;
        edge.weight = 10;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = c;
        edge.endPoint = f;
        edge.weight = 15;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = c;
        edge.endPoint = e;
        edge.weight = 2;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = c;
        edge.endPoint = d;
        edge.weight = 70;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = d;
        edge.endPoint = f;
        edge.weight = 30;
        edges.add(edge);
        
        edge = new Edge();
        edge.startPoint = d;
        edge.endPoint = e;
        edge.weight = 100;
        edges.add(edge);
        
        Dijkstra algo = new Dijkstra(edges, points);
        edges = (ArrayList)algo.calculate(a, f);
        System.out.println("Shortest Path: ");
        for(int i = 0; i < edges.size(); ++i) {
            edge = edges.get(i);
            System.out.print(edge.startPoint.name + "-" + edge.endPoint.name);
            if(i != edges.size() - 1) System.out.print(", ");
        }*/
    }
    
    public Dijkstra(Collection<Edge> edgeSet, Collection<Point> vertexSet) {
        resetGraph(edgeSet, vertexSet);
    }
    
    public final void resetGraph(Collection<Edge> edgeSet, Collection<Point> vertexSet) {
        pToIndex = new HashMap<>();
        iToPoint = new HashMap<>();
        kToEdge  = new HashMap<>();
        
        int n = vertexSet.size();
        int i = 0;
        
        A = new Double[n][n];
        
        for(int x = 0; x < n; ++x) {
            Arrays.fill(A[x], Double.POSITIVE_INFINITY);
        }
        
        for(Point pt : vertexSet) {
            pToIndex.put(pt, i);
            iToPoint.put(i++, pt);
        }
        
        int j = 0;
        for(Edge e : edgeSet) {
            int x = pToIndex.get(e.startPoint);
            int y = pToIndex.get(e.endPoint);
            
            
            kToEdge.put(x + "-" + y, e);
            kToEdge.put(y + "-" + x, e);
            
            A[x][y] = e.weight;
            A[y][x] = e.weight;
        }
    }
    
    public Collection<Edge> calculate(Point start, Point end) {
        int source = pToIndex.get(start);
        int dest   = pToIndex.get(end);
        int n      = pToIndex.size();
        
        HashSet<Integer> visited = new HashSet<>();
        int[] prev    = new int[n];
        Double[] dist = new Double[n];
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        for(int i = 0; i < n; ++i) {
            prev[i] = -1;
            
            if(i != source) dist[i] = Double.POSITIVE_INFINITY;
            else dist[i] = 0D;
            
            pq.add(new Node(i, dist[i]));
        }
        
        while(!pq.isEmpty()) {
            int x = pq.poll().node;
            
            if(visited.contains(x)) continue;
            visited.add(x);
            
            for(int y = 0; y < n; ++y) {
                if(A[x][y] != Double.POSITIVE_INFINITY) {
                    double newDist = dist[x] + A[x][y];
                    if(newDist < dist[y]) {
                        dist[y] = newDist;
                        prev[y] = x;
                        pq.add(new Node(y, newDist));
                    }
                }
            }
        }
        
        ArrayList<Edge> route = new ArrayList<>();
        constructRoute(prev, dest, route);
        
        return route;
    }
    
    private void constructRoute(int[] prev, int dest, ArrayList<Edge> route) {
        if(prev[dest] == -1) return;
        else {
            int x = prev[dest];
            int y = dest;
            constructRoute(prev, x, route);
            route.add(kToEdge.get(x + "-" + y));
        }
    }
    
    private class Node implements Comparable {
        // No need to create getter and setter methods
        // since this is just an internal helper class
        public int node;
        public Double dist;
        
        public Node(int node, Double dist) {
            this.node = node;
            this.dist = dist;
        }

        @Override
        public int compareTo(Object o) {
            Node temp = (Node)o;
            
            if(this.dist < temp.dist) return -1;
            else if(this.dist > temp.dist) return 1;
            else return 0;
        }
    }
}



