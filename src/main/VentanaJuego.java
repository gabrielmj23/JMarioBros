package main;

import javax.swing.JFrame;

/**
 *
 * @author Gabriel
 */
public class VentanaJuego {

    private JFrame frame;
    
    public VentanaJuego(PanelJuego panel) {
        frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
