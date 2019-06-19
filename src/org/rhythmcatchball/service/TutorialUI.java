package org.rhythmcatchball.service;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TutorialUI extends Panel {
    private Panel imagePanel;
    private Panel controlPanel;
	
	private Button proceed;
	private Button goBack;
	
	public TutorialUI() 
	{
		BufferedImage img = null;
		Image dimg = null;

		this.setLayout(new BorderLayout());

    	imagePanel = new Panel();
    	imagePanel.setLayout(new GridLayout(2, 2));
    	
    	ImageIcon image;
    	JLabel labelimage = null;
    	
    	image = new ImageIcon("sprites/start.gif");
    	labelimage = new JLabel(image);
    	imagePanel.add(labelimage);
    	
    	image = new ImageIcon("sprites/throw.gif");
    	labelimage = new JLabel(image);
    	imagePanel.add(labelimage);
    	
    	image = new ImageIcon("sprites/receive.gif");
    	labelimage = new JLabel(image);
    	imagePanel.add(labelimage);
    	
    	image = new ImageIcon("sprites/end.gif");
    	labelimage = new JLabel(image);
    	imagePanel.add(labelimage);
    	
		proceed = new Button("Game Start");
		goBack = new Button("Return to Title");

    	controlPanel = new Panel();
		controlPanel.add(proceed);
		controlPanel.add(goBack);

		this.add(imagePanel, BorderLayout.CENTER);
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

