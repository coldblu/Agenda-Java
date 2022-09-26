import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

	 
	public TelaPrincipal() {
		super("Minha Agenda");
		JLabel label = new JLabel("Minha Agenda");
		getContentPane().add(new TelaAgenda());
		// Configurações da janela
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocation(300, 300);
		pack();        
		setSize(800, 720);
		setVisible(true);

	}
}