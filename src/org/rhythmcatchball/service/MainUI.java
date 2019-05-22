package org.rhythmcatchball.service;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//媛�濡� : 768 �꽭濡�: 480

public class MainUI extends Panel {
    private Label headerLabel;
    private Label statusLabel;
    private Panel controlPanel;
 
    public MainUI() {
        prepareGUI();
    }
 
    private void prepareGUI() {
    	this.setLayout(new GridLayout(3, 1));
 
        headerLabel = new Label();
        headerLabel.setAlignment(Label.CENTER);
        headerLabel.setText("리듬캐치볼");
 
        statusLabel = new Label();
        statusLabel.setText("click button");
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350, 100);
 
        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
 
        this.add(headerLabel);
        this.add(controlPanel);
        this.add(statusLabel);
    }
 
    public void showButton() {
 
        Button onePlay = new Button("1p GAME PLAY");
        Button twoPlay = new Button("2p GAME PLAY");
        Button onlinePlay = new Button("Online GAME PLAY");
        Button preference = new Button("PREFERENCE");
        Button close = new Button("CLOSE");
 
        onePlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("onePlay Button clicked.");
            }
        });
 
        twoPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("twoPlay Button clicked.");
            }
        });
 
        onlinePlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("onlinePlay Button clicked.");
            }
        });
 
        preference.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("preference Button clicked.");
            }
        });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("close Button clicked.");
            }
        });
        
        controlPanel.add(onePlay);
        controlPanel.add(twoPlay);
        controlPanel.add(onlinePlay);
        controlPanel.add(preference);
        controlPanel.add(close);

 
    }
}