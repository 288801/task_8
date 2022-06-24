package gui;

import graph.Graph;
import task.Task;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FrameMain extends JFrame {
    private GraphPanel graphPanel;

//    public static boolean[] result;
//    public static boolean isUsed;
//
//
//    public static int bfs(Graph graph, Graph.Node node) {
//        node.distance = 0;
//        for (Graph.Node n : graph.getNodes()) {
//            n.setVisited(false);
//        }
//        Queue<Graph.Node> queue = new LinkedList<>();
//        int maxDistance = 0;
//        node.setVisited(true);
//        queue.add(node);
//        while (queue.size() > 0) {
//            Graph.Node current = queue.poll();
//            for (Graph.Node other: current.neighbours) {
//                if (other.isVisited())
//                    continue;
//                other.distance = current.distance + 1;
//                if(other.distance > maxDistance){
//                    maxDistance = other.distance;
//                }
//                other.setVisited(true);
//                queue.add(other);
//            }
//        }
//        for(Graph.Node n : graph.getNodes()){
//            if(!n.isVisited()){
//                maxDistance = 1000000;
//            }
//        }
//        return maxDistance;
//    }
//
//    public static boolean isTrue(Graph graph, int n){
//        int max = 0;
//        for(Graph.Node node : Graph.nodes){
//            int distance = bfs(graph, node);
//            if(distance > max){
//                max = distance;
//            }
//        }
//        if(max > n){
//            return false;
//        } else{
//            return true;
//        }
//    }
//
//    public static Graph convert(Graph graph, boolean[] en){
//        Graph g1 = graph;
//        for(int i = graph.getNodes().size(); i < graph.getNodes().size() + graph.getEdges().size(); i++){
//            if(en[i]){
//                g1.getEdges().remove(i-graph.getNodes().size());
//            }
//        }
//        for(int i = 0; i < graph.getNodes().size(); i++){
//            if(en[i]){
//                for (Graph.Node node: g1.getNodes()) {
//                    node.neighbours.remove(g1.getNodes().get(i));
//                }
//                g1.getNodes().remove(i);
//            }
//        }
//        return g1;
//    }
//
//    public static void enumeration1(boolean[] en, int c, int n, Graph graph){
//        if(c < en.length){
//            enumeration1(en, c+1, n, graph);
//            for(int j = en.length-1; j > c; j-- ){
//                en[j] = false;
//            }
//            en[c] = true;
//            enumeration1(en, c+1, n, graph);
//        } else{
//            if(!isTrue(convert(graph, en), n) && !isUsed){
//                isUsed = true;
//                result = Arrays.copyOfRange(en, 0, en.length);
//            }
//        }
//    }

//    public static void copyGraph(Graph graph, Graph newGraph){
//        for(Graph.Node node : graph.getNodes()){
//            graph.addNode(20, 20, 13);
//            newGraph.addNode(node.getX(), node.getY(), node.getValue());
//        }
//        for(Graph.Edge edge : graph.getEdges()){
//            newGraph.connectNodes(edge.getFirstNode(), edge.getSecondNode());
//        }
//    }

    public FrameMain() {
        setTitle("Graph Demo");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        graphPanel = new GraphPanel(500, 500);
        TextField textField = new TextField( "                                                             Результат ", 2);
        TextArea textArea = new TextArea(2, 2);
        add(textArea);
        add(textField);
        add(graphPanel);

        Graph graph = new Graph(() -> graphPanel.repaint());
        graph.addNode(20, 20, 13);
        graph.addNode(200, 20, 15);
        graph.addNode(200, 140, 20);
        graph.addNode(200, 340, 4);
        graph.addNode(100, 340, 17);

        graph.connectNodes(1,2);

        graphPanel.initGraph(graph);

        JButton buttonSolve = new JButton("solve");
        buttonSolve.addActionListener(e -> {
            try {
//                Graph.Node startNode = null;
//                for(Graph.Node node: graphPanel.getGraph().getNodes()){
//                    if(node.getValue() == Integer.parseInt(textArea1.getText())){
//                        startNode = node;
//                    }
//                }
//                String str = textArea.getText();
//                double phi = Double.parseDouble(str);
//                boolean robot = Task.solution(graphPanel.getGraph(), startNode, phi);
//                if(robot){
//                    textField.setText("YES");
////                    textField.append("YES");
//                }else {
//                    textField.setText("NO");
////                    textField.append("NO");
//                }
//                boolean[] l = new boolean[graph.getNodes().size()+graph.getEdges().size()];
//                boolean[] x = Task.enumeration(graph, n, l, graph.getNodes().size()+graph.getEdges().size());
//                for(boolean b : x){
//                    if(b){
//                        textField.setText("1");
//                    }else{
//                        textField.setText("0");
//                    }
//                }
                int n = Integer.parseInt(textArea.getText());
                boolean[] en = new boolean[graph.nodes.size() + graph.edges.size()];
                Task.enumeration1(en, 0, n, graph);

                graphPanel.removeAll();
                graphPanel.initGraph(Task.convert(graph,Task.result));
                for (int i = 0; i < Task.result.length; i++) {
                    if (Task.result[i]) {
                        System.out.println(1);
                    } else {
                        System.out.println(0);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonSolve.setPreferredSize(new Dimension(100, 40));
        buttonSolve.setSize(100, 30);
        add(buttonSolve);

//        JButton buttonReset = new JButton("Reset");
//        buttonSolve.setPreferredSize(new Dimension(100, 40));
//        buttonReset.addActionListener(e -> {
//            graphPanel.resetNodes();
//        });
//        add(buttonReset);

        pack();
        setVisible(true);
    }
}
