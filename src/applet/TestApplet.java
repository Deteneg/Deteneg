package applet;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import test.Test;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;

public class TestApplet extends JApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestApplet() {
	}

	String s;
	TextArea frase;
	JButton boton;
	TextArea letra;
	Panel norte, centro, sur;

	public static void main(String[] args) {
		JFrame f = new JFrame("Deteneg: Negation Annotator");

		// To create an instance of TestApplet
		TestApplet ta = new TestApplet();

		// To add the instance in the frame
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add("Center", ta);

		// To initialize the variables with the width and height of the tag
		// <applet>
		// Fullscreen
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		size.setSize(size.getWidth()/2, size.getHeight()/2);
		f.setSize(size);		

		// To call init()
		ta.init();

		// To make visible the frame
		 f.show();

		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	public void init() {
		s = "";
		frase = new TextArea();
		frase.setSelectionStart(1);
		frase.setSelectionEnd(1);
		frase.setCaretPosition(1);
		frase.setColumns(1);
		frase.setRows(1);
		frase.setFont(new Font("Dialog", Font.PLAIN, 16));
		boton = new JButton("Annotate");
		boton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// To tell the button what it has to do when it is pressed
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Test t = new Test();
				try {
					List<String> visualization = t.test(frase.getText());
					String resp = "" ;
					for(String s : visualization){
						resp = "   " + resp + "\n\n" + s;
					}					
					letra.setText(resp);
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
		lab.setFont(new Font("Dialog", Font.BOLD, 18));
		norte.add(lab);
		centro.add(frase);

		// To add the button to the dashboard
		centro.add(boton);

		// To create the "letra" label
		letra = new TextArea("");
		letra.setEditable(false);
		letra.setFont(new Font("Dialog", Font.BOLD, 18));
		letra.setForeground(Color.BLACK);
		letra.setSelectionEnd(10);
		letra.setSelectionStart(10);
		letra.setColumns(0);

		// To add "letra" tag to center dashboard
		centro.add(letra);

		// To print result
		System.out.println(letra);
		centro.add(letra);

		// To add north dashboard to north, south dashboard to south and center
		// dashboard to center
		getContentPane().add("North", norte);
		getContentPane().add("Center", centro);

	}
	

}
