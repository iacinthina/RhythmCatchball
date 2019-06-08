package org.rhythmcatchball.service;

import java.awt.Button;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineUI extends Panel{
    private Label statusLabel;
    private String defaultTooltip;
    
    private Panel textPanel;
    private Panel jnpanel;
    private Panel mkpanel;
    private Container textPanelEntry;
    private String port;
    private String ip;

	private Button host;
	private Button join;
	private Button goBack;
	private Button proceed;
	private Panel proceedPanel;

    private Container update;
    
    public OnlineUI(Container update) {
    	port = "";
    	ip = "";
    	defaultTooltip = "Make your game or join to other";
    	this.update = update;
    	
        host = new Button("Make Room");
        join = new Button("Join Room");
        goBack = new Button("Return to Title");
        proceed = new Button("Game Start");

    	proceedPanel = new Panel();
    	proceedPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	proceedPanel.add(proceed);
        
    	
        prepareGUI();
        setMakeRoom();
        setJoinRoom();
        RoomButton();
    }
 
 
    private void prepareGUI() {
    	Panel controlPanel;
    	this.setLayout(new GridLayout(3, 1));
    	
    	statusLabel = new Label();
        statusLabel.setText(defaultTooltip);
        statusLabel.setAlignment(Label.CENTER);
    	
    	controlPanel = new Panel();
    	controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
        textPanel = new Panel();
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
 
        controlPanel.add(host);
        controlPanel.add(join);
        controlPanel.add(goBack);

        this.add(statusLabel);
        this.add(textPanel);
        this.add(controlPanel);
        this.setVisible(true);
    }
 
    public void RoomButton() {
        host.addActionListener((ActionEvent e) -> {setTextPanel(mkpanel);
        											update.setVisible(true);});
        join.addActionListener((ActionEvent e) -> {setTextPanel(jnpanel);
													update.setVisible(true);});
    }
    
    private void setJoinRoom(){
        jnpanel = new Panel();
        Button enter = new Button("ENTER");
        
        Label portLable = new Label("Port Num");
        Label ipLabel = new Label("IP Address");
        TextField portTextField = new TextField(port,10);
        TextField ipTextField = new TextField(ip,15);
        
        enter.addActionListener((ActionEvent e) -> {
            ip = ipTextField.getText();
        	port = portTextField.getText();
        	setTextPanel(proceedPanel);
        	statusLabel.setText("IP : " + ip + " Port : " + port);
        	update.setVisible(true);
        });
        
        jnpanel.add(ipLabel);
        jnpanel.add(ipTextField);
        jnpanel.add(portLable);
        jnpanel.add(portTextField);
        jnpanel.add(enter);
    }

    private void setMakeRoom(){
        mkpanel = new Panel();
        Button enter = new Button("ENTER");
        
        Label portLabel = new Label("Port Num");
        TextField portTextfield = new TextField(port,10);

        enter.addActionListener((ActionEvent e) -> {
    		port = portTextfield.getText();
        	setTextPanel(proceedPanel);
        	statusLabel.setText("Port : " + port);
        	update.setVisible(true);
    });
        
        mkpanel.add(portLabel);
        mkpanel.add(portTextfield);
        mkpanel.add(enter);
    }

    private void setTextPanel(Panel change) {
    	if (textPanelEntry != null)
    		textPanel.remove(textPanelEntry);
    	textPanelEntry = change;
    	textPanel.add(textPanelEntry);
    	setVisible(true);
    }
    
    public void setActionListener(String button, ActionListener actionListener) {
    	if (actionListener == null) return;
    	switch(button) {
    	case "host":
    		host.addActionListener(actionListener);
    		break;
    	case "join":
    		join.addActionListener(actionListener);
    		break;
    	case "goBack":
    		goBack.addActionListener(actionListener);
    		break;
    	case "proceed":
    		proceed.addActionListener(actionListener);
    		break;
    	default:
    		//no target
    		break;
    	}
    }
}