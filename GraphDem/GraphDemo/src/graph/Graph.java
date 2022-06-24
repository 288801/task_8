package graph;

import gui.EdgeComponent;
import gui.GraphPanel;
import gui.NodeComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph implements Cloneable {
    private GraphPanel graphPanel;
    @Override
    public Object clone() throws CloneNotSupportedException {
        Graph newGraph = new Graph(onChangeAction);
        for(Node node : nodes){
            Node n1 = new Node(node.getX(), node.getY(), node.getValue());
            newGraph.getNodes().add(n1);
        }
        for(Edge edge : edges){
            Node n1 = new Node(edge.getFirstNode().getX(), edge.getFirstNode().getY(), edge.getFirstNode().getValue());
            Node n2 = new Node(edge.getSecondNode().getX(), edge.getSecondNode().getY(), edge.getSecondNode().getValue());
            for(Node node1 : newGraph.nodes){
                for(Node node2 : newGraph.nodes){
                    if(node1.getX() == n1.getX() && node1.getY() == n1.getY() && node2.getX() == n2.getX() && node2.getY() == n2.getY()){
                        node1.neighbours.add(node2);
                        node2.neighbours.add(node1);
                        n1 = node1;
                        n2 = node2;
                    }
                }
            }
            Edge e1 = new Edge(n1, n2);
            newGraph.getEdges().add(e1);
        }
        return newGraph;
    }

    public Graphics2D g;
    public class Edge implements Cloneable{
        private Node firstNode;
        private Node secondNode;
        private Color color = Color.yellow;

        public Edge(Node firstNode, Node secondNode) {
            this.firstNode = firstNode;
            this.secondNode = secondNode;
        }

        public Node getFirstNode() {
            return firstNode;
        }

        public void setFirstNode(Node firstNode) {
            this.firstNode = firstNode;
        }

        public Node getSecondNode() {
            return secondNode;
        }

        public void setSecondNode(Node secondNode) {
            this.secondNode = secondNode;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
    public class Node implements Cloneable{
        public boolean wasRemoved;
        private int x;
        private int y;
        public int distance;
        private boolean isVisited;
        private int value;
        private Color color = Color.yellow;
        private boolean painted = false;
        public List<Node> neighbours = new LinkedList<>();

        private Color DEFAULT_COLOR = Color.yellow;

        public Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public void connect(Node node) {
            connectNodes(this, node);
            onChangeAction.run();
        }

        public void resetColor() {
            color = DEFAULT_COLOR;
        }

        public void remove() {
            removeNode(this);
        }

        public boolean isVisited() {
            return isVisited;
        }

        public void setVisited(boolean visited) {
            isVisited = visited;
        }
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public boolean isPainted() {
            return painted;
        }

        public void setPainted(boolean isPainted) {
            this.painted = isPainted;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public  List<Node> nodes = new LinkedList<>();
    public  List<Edge> edges = new LinkedList<>();
    private Runnable onChangeAction;

    public Graph(Runnable runnable) {
        onChangeAction = runnable;
    }
    public Node addNode(int x, int y, int value) {
        Node newNode = new Node(x, y, value);
        nodes.add(newNode);
        onChangeAction.run();
        return newNode;
    }

    public void removeNode(int index) {
        Node removeNode = nodes.get(index);
        for (Node node: removeNode.neighbours) {
            node.neighbours.remove(removeNode);
        }
        nodes.remove(removeNode);
        onChangeAction.run();
    }

    public void removeNode(Node removeNode) {
        for (Node node: removeNode.neighbours) {
            node.neighbours.remove(removeNode);
        }
        nodes.remove(removeNode);
        onChangeAction.run();
    }

    public void connectNodes(int index1, int index2) {
        nodes.get(index1).neighbours.add(nodes.get(index2));
        nodes.get(index2).neighbours.add(nodes.get(index1));
        Edge edge = new Edge(nodes.get(index1), nodes.get(index2));
        edges.add(edge);
        onChangeAction.run();
    }

    public void connectNodes(Node node1, Node node2) {
        node1.neighbours.add(node2);
        node2.neighbours.add(node1);
        Edge edge = new Edge(node1, node2);
        edges.add(edge);
        onChangeAction.run();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }


}
