package applet;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import test.Test;

public class TestApplet extends JApplet {

	String s;
	TextArea frase;
	JButton boton;
	Label letra;
	Panel norte, centro, sur;

	public static void main(String[] args) {
		JFrame f = new JFrame("Deteneg. Negation annotator");

		// To create an instance of TestApplet
		TestApplet ta = new TestApplet();

		// To add the instance in the frame
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add("Center", ta);

		// To initialize the variables with the width and height of the tag
		// <applet>
		// Fullscreen
		f.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		// To call init()
		ta.init();

		// To make visible the frame
		 f.show();

		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	public void init() {
		s = "";
		frase = new TextArea();
		boton = new JButton("Annotate");

		// To tell the button what it has to do when it is pressed
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Test t = new Test();
				try {
					letra.setText(t.test(frase.getText()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// To create the three dashboards
		norte = new Panel();
		centro = new Panel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

		Label lab = new Label("Introduce the sentence to annotate");
		Font myFont = new Font("", Font.BOLD, 12);
		lab.setFont(myFont);
		norte.add(lab);
		centro.add(frase);

		// To add the button to the dashboard
		centro.add(boton);

		// To create the "letra" label
		letra = new Label("                                                                    " + "    "
				+ "                                                                     "
				+ "                                                                             "
				+ "                                                                             "
				+ "                                                                             "
				+ "                                                                                ");

		// To add "letra" tag to center dashboard
		centro.add(letra);

		// To print result
		System.out.println(letra);
		centro.add(letra);

		// To add north dashboard to north, south dashboard to south and center
		// dashboard to center
		this.add("North", norte);
		this.add("Center", centro);

	}

}
