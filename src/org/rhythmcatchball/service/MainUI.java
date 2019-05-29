package org.rhythmcatchball.service;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//가로 : 768 세로: 480

import javax.swing.JFrame;

public class MainUI extends JFrame{
    private Frame mainFrame;
    private Label headerLabel;
    private Label statusLabel;
    private Panel controlPanel;
    private Panel textPanel;
    private Panel mainControlPanel;
    OnlineUI onlineUI;
 
    public MainUI() {
        prepareGUI();
    }
    
    public static void main(String[] args) {
    	MainUI mainButtonControl = new MainUI();  	
        mainButtonControl.showButton();
    }
 
    private void prepareGUI() {
        mainFrame = new Frame("Rhythm Catchball");
        mainFrame.setSize(768, 480);
        mainFrame.setLayout(new GridLayout(4, 1));
        
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        onlineUI = new OnlineUI();
        
        headerLabel = new Label();
        headerLabel.setAlignment(Label.CENTER);
        headerLabel.setText("메인화면");
 
        statusLabel = new Label();
        statusLabel.setText("click button");
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350, 100);
 
        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        textPanel = new Panel();
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
 
        mainControlPanel = new Panel();
        mainControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(textPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }
 
    private void showButton() {
    	
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
 
        onlinePlay.addActionListener(OnlineActionListener());
 
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
        
        mainControlPanel.add(onePlay);
        mainControlPanel.add(twoPlay);
        mainControlPanel.add(onlinePlay);
        mainControlPanel.add(preference);
        mainControlPanel.add(close);
        
        controlPanel.add(mainControlPanel);
 
        mainFrame.setVisible(true);
 
    }
    
    public ActionListener OnlineActionListener() {
		return new ActionListener()     
        {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("onlinePlay Button clicked.");
            	controlPanel.remove(mainControlPanel);
            	
            	onlineUI.RoomButton(controlPanel, textPanel);
                
            	mainFrame.setVisible(true);
            }
        };
	}
    
}