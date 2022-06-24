package gui;

import javax.swing.*;
import java.awt.*;

public class EdgeComponent extends JComponent {
    private NodeComponent nodeComponent1;
    private NodeComponent nodeComponent2;
    private Color color = Color.RED;

    public EdgeComponent(NodeComponent n1, NodeComponent n2) {
        this.nodeComponent1 = n1;
        this.nodeComponent2 = n2;

        setBounds(nodeComponent1.getX(), nodeComponent1.getY(), (int) Math.sqrt(Math.pow(nodeComponent1.getX() - nodeComponent2.getX(), 2) + Math.pow(nodeComponent1.getY() - nodeComponent2.getY(), 2)), 5);
        setBorder(BorderFactory.createLineBorder(Color.black, 3));
        setBackground(Color.green);
        setOpaque(true);
    }

}
