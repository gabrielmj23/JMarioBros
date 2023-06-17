package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
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
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                panel.getJuego().ventanaPerdida();
            }

        });
    }
}
