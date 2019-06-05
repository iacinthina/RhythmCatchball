package org.rhythmcatchball.service;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TutorialUI extends Panel {
    private Panel ImagePanel;
    private Panel controlPanel;
	
	private Button proceed;
	private Button goBack;
	
	public TutorialUI() 
	{
    	this.setLayout(new BorderLayout());

    	ImagePanel = new Panel();
    	ImagePanel.setLayout(new GridLayout(2, 2));
    	
    	ImageIcon image;
    	JLabel labelimage;
    	
    	image = new ImageIcon("sprites/msg_praise0.png");
    	labelimage = new JLabel(image);
    	ImagePanel.add(labelimage);
    	
    	image = new ImageIcon("sprites/msg_praise1.png");
    	labelimage = new JLabel(image);
    	ImagePanel.add(labelimage);
    	
    	image = new ImageIcon("sprites/msg_praise2.png");
    	labelimage = new JLabel(image);
    	ImagePanel.add(labelimage);
    	
    	image = new ImageIcon("sprites/msg_praise3.png");
    	labelimage = new JLabel(image);
    	ImagePanel.add(labelimage);
    	
		proceed = new Button("Game Start");
		goBack = new Button("Return to Title");

    	controlPanel = new Panel();
		controlPanel.add(proceed);
		controlPanel.add(goBack);

		this.add(ImagePanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
	}
	
	public void setActionListener(String button, ActionListener actionListener) {
    	if (actionListener == null) return;
    	switch(button) {
    	case "proceed":
    		proceed.addActionListener(actionListener);
    		break;
    	case "goBack":
    		goBack.addActionListener(actionListener);
    		break;
    	default:
			break;
    	}
    }
}

