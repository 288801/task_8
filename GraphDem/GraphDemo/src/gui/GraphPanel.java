package gui;
import graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

public class GraphPanel extends JPanel implements MouseListener {
    private int WIDTH;
    private int HEIGHT;

    private final int OFFSET_X = 7;
    private final int OFFSET_Y = -5;

    private Graph graph;
    private final int NODE_SIZE = 50;

    private List<NodeComponent> connectNodes = new LinkedList<>();

    public GraphPanel(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        setLayout(null);

        addMouseListener(this);

        setPreferredSize(new Dimension(width, height));
    }

    public void initGraph(Graph graph) {
        this.graph = graph;
        for (Graph.Node node: graph.getNodes()) {
            add(new NodeComponent(this, node, NODE_SIZE, node.getX(), node.getY(), node.getValue()));
        }
        repaint();
    }

    @Override
    public void paint(Graphics graphics) {
        if (graph == null) {
            return;
        }

        Graphics2D g = (Graphics2D) graphics;
        super.paint(g);
//        for (Graph.Node node: graph.getNodes()) {
//            for (Graph.Node neighbour: node.neighbours) {
//                g.drawLine(node.getX() + NODE_SIZE / 2, node.getY() + NODE_SIZE / 2, neighbour.getX() + NODE_SIZE / 2, neighbour.getY() + NODE_SIZE / 2);
//            }
//        }
        for(Graph.Edge edge : graph.getEdges()){
            if(graph.getNodes().contains(edge.getFirstNode()) && graph.getNodes().contains(edge.getSecondNode())) {
                g.drawLine(edge.getFirstNode().getX() + NODE_SIZE / 2, edge.getFirstNode().getY() + NODE_SIZE / 2, edge.getSecondNode().getX() + NODE_SIZE / 2, edge.getSecondNode().getY() + NODE_SIZE / 2);
            }
        }
    }


    public void performConnection(NodeComponent node) {
        if (connectNodes.size() > 1) {
            System.err.println("Connection queue overflow");
            return;
        }
        connectNodes.add(node);
        if (connectNodes.size() == 2) {
            graph.connectNodes(connectNodes.get(0).node, connectNodes.get(1).node);
            resetNodes();
            repaint();
        }
    }

    public void resetNodes() {
        for (Component c: getComponents()) {
            if (c instanceof NodeComponent) {
                ((NodeComponent) c).reset();
            }
        }
        connectNodes.clear();
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component c = getComponentAt(e.getPoint());
        if (c != this) {
            return;
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            Graph.Node newNode = graph.addNode(e.getX() - NODE_SIZE / 2, e.getY() - NODE_SIZE / 2, 0);
            add(new NodeComponent(this, newNode, NODE_SIZE, newNode.getX(), newNode.getY()));
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (!connectNodes.isEmpty()) {
            resetNodes();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
