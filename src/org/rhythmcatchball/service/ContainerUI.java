package org.rhythmcatchball.service;

import javax.swing.*;
import java.awt.*;

public class ContainerUI extends JFrame{
	ContainerUI() {
		setTitle("Rhythm Catchball");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(3, 1));
		
		//contentPane.add(new JButton("OK"));

		setSize(768, 480);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ContainerUI();
	}

}