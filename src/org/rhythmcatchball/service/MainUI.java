package org.rhythmcatchball.service;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//가로 : 768 세로: 480


/**
 * MainUI.java
 * @author pc1
 * @date   2019. 5. 29.
 * 메인 화면의 틀만 잡아놓기
 */
public class MainUI extends Panel {
    private Label headerLabel;
    private Label statusLabel;
    private Panel controlPanel;
 
    private Button onePlay;
    private Button twoPlay;
    private Button onlinePlay;
    private Button preference;
    private Button close;
    
    private String defaultTooltip;
    
    public MainUI() {
    	defaultTooltip = "Click the button";
        prepareGUI();
        showButton();
    }
    
    private void prepareGUI() {
    	this.setLayout(new GridLayout(3, 1));
    	
        headerLabel = new Label();
        headerLabel.setAlignment(Label.CENTER);
        headerLabel.setText("Rhythm Catchball");
 
        statusLabel = new Label();
        statusLabel.setText(defaultTooltip);
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350, 100);
        
        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        this.add(headerLabel);
        this.add(controlPanel);
        this.add(statusLabel);
    }
 
    private void showButton() {
        onePlay = new Button("1p GAME PLAY");
        twoPlay = new Button("2p GAME PLAY");
        onlinePlay = new Button("Online GAME PLAY");
        preference = new Button("PREFERENCE");
        close = new Button("CLOSE");

        controlPanel.add(onePlay);
        controlPanel.add(twoPlay);
        controlPanel.add(onlinePlay);
        controlPanel.add(preference);
        controlPanel.add(close);

        setToolTip(onePlay, "Single play with computer");
        setToolTip(twoPlay, "Local multi play with friend");
        setToolTip(onlinePlay, "Online multi play");
        setToolTip(preference, "Settings");
        setToolTip(close, "Exit this game");
    }
    
    public void setActionListener(String button, ActionListener actionListener) {
    	if (actionListener == null) return;
    	switch(button) {
    	case "onePlay":
    		onePlay.addActionListener(actionListener);
    		break;
    	case "twoPlay":
    		twoPlay.addActionListener(actionListener);
    		break;
    	case "onlinePlay":
    		onlinePlay.addActionListener(actionListener);
    		break;
    	case "preference":
    		preference.addActionListener(actionListener);
    		break;
    	case "close":
    		close.addActionListener(actionListener);
    		break;
    	default:
			break;
    	}
    }
    
    public void setToolTip(Button button, String Message) {
    	button.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
                statusLabel.setText(Message);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				statusLabel.setText(defaultTooltip);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
    }
}