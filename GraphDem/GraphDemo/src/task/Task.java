package task;

import graph.Graph;
import gui.EdgeComponent;

import javax.sound.midi.Soundbank;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Task {
    public static boolean solution(Graph graph, Graph.Node thisNode, double phi){
        int x = thisNode.getX();
        int y = thisNode.getY();
        thisNode.setColor(Color.green);
        thisNode.setVisited(true);
        for(Graph.Node node: graph.getNodes()){
            if(phi != Math.PI/2 && phi != -Math.PI/2) {
                if (node!=thisNode && !node.isVisited() && node.getY() - y == Math.tan(phi) * (node.getX() - x)) {
                    solution(graph, node, phi - Math.PI / 2);
                    break;
                }
            } else{
                if (node!=thisNode && !node.isVisited() && node.getX() == x) {
                    solution(graph, node, phi - Math.PI / 2);
                    break;
                }
            }
        }

        for(Graph.Node node: graph.getNodes()){
            if (!node.isVisited()){
                return false;
            }
        }
        return true;
    }


    public static Graph sol(Graph graph, int n) throws CloneNotSupportedException {
//        if(isTrue(graph, n)) {
//            for (int i = 1; i <= graph.nodes.size() + graph.edges.size(); i++) {
//                boolean[] en = new boolean[graph.nodes.size() + graph.edges.size()];
//                Graph g1 = enumeration(graph, n, en, i);
//                if (!isTrue(g1, n)) {
//                    graph = g1;
//                    break;
//                }
//            }
//        }
//        for(Graph.Node no : g1.getNodes()){
//            System.out.println(no.getValue());
//        }
//        System.out.println(" ");
//        for(Graph.Edge ed : g1.getEdges()){
//            System.out.println(ed.getFirstNode().getValue());
//            System.out.println(ed.getSecondNode().getValue());
//        }
        boolean[] en = new boolean[graph.nodes.size() + graph.edges.size()];
        enumeration1(en, 0, n, graph);
        return graph;
    }


    public static Graph enumeration(Graph graph, int n, boolean[] en, int c) throws CloneNotSupportedException {
        if(c <= 0 && !isTrue(convert(graph, en), n)){
            Graph g1 = convert(graph, en);
            for (Graph.Node no : g1.getNodes()) {
                System.out.println(no.getValue());
            }
            System.out.println(" ");
            for (Graph.Edge ed : g1.getEdges()) {
                System.out.println(ed.getFirstNode().getValue());
                System.out.println(ed.getSecondNode().getValue());
            }
            System.out.println(" ");
            return g1;
        }else {
            for (int i = 0; i < en.length; i++) {
                if (!en[i]) {
                    en[i] = true;
                    if(c-1 > 0) {
                        enumeration(graph, n, en, c - 1);
                    }
                }
            }
        }
        return convert(graph, en);
    }

    public static boolean[] result;
    public static boolean isUsed;


    public static int bfs(Graph graph, Graph.Node node) {
        node.distance = 0;
        for (Graph.Node n : graph.getNodes()) {
            n.setVisited(false);
        }
        Queue<Graph.Node> queue = new LinkedList<>();
        int maxDistance = 0;
        node.setVisited(true);
        queue.add(node);
        while (queue.size() > 0) {
            Graph.Node current = queue.poll();
            for (Graph.Node other: current.neighbours) {
                if (other.isVisited())
                    continue;
                other.distance = current.distance + 1;
                if(other.distance > maxDistance){
                    maxDistance = other.distance;
                }
                other.setVisited(true);
                queue.add(other);
            }
        }
        for(Graph.Node n : graph.getNodes()){
            if(!n.isVisited()){
                maxDistance = 1000000;
            }
        }
        return maxDistance;
    }

    public static boolean isTrue(Graph graph, int n){
        int max = 0;
        for(Graph.Node node : graph.getNodes()){
            int distance = bfs(graph, node);
            if(distance > max){
                max = distance;
            }
        }
        if(max > n){
            return false;
        } else{
            return true;
        }
    }

    public static Graph convert(Graph graph, boolean[] en) throws CloneNotSupportedException {
        Graph g1 = (Graph) graph.clone();
        int e = g1.getEdges().size();
        int n = g1.getNodes().size();
        int delIndex1 = 0;
        int delIndex2 = 0;
        for(int i = 0; i < n; i++){
            if(en[i]){
                for (Graph.Node node: g1.getNodes()) {
                    node.neighbours.remove(g1.getNodes().get(i-delIndex1));
                }
                g1.getNodes().remove(i-delIndex1);
                delIndex1++;
            }
        }
        for(int i = n; i < e+n; i++){
            if(en[i]){
                g1.getEdges().remove(i-n-delIndex2);
                delIndex2++;
            }
        }
        return g1;
    }

    public static void enumeration1(boolean[] en, int c, int n, Graph graph) throws CloneNotSupportedException {
        if(c < en.length){
            enumeration1(en, c+1, n, graph);
            for(int j = en.length-1; j > c; j-- ){
                en[j] = false;
            }
            en[c] = true;
            enumeration1(en, c+1, n, graph);
        } else{
            if(!isUsed && !isTrue(convert(graph, en), n)){
                isUsed = true;
                result = Arrays.copyOfRange(en, 0, en.length);
            }
        }
    }
}
