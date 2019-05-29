package org.rhythmcatchball.service;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineUI extends Panel{
    private Panel roomPanel;
    private Panel textPanel;
    private Panel controlPanel;
    
    
    private boolean isMKroom = false;
    private boolean isJNroom = false;
    private Panel jnpanel;
    private Panel mkpanel;
    String port;
    String ip;

	private Button host;
	private Button join;
	private Button goBack;
	private Button proceed;
    
    public OnlineUI() {
        host = new Button("Make Room");
        join = new Button("Join Room");
        goBack = new Button("Return");
        proceed = new Button("PREFERENCE");
        
        prepareGUI();
    }
 
 
    private void prepareGUI() {
    	this.setLayout(new GridLayout(3, 1));
    	
    	controlPanel = new Panel();
    	controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
        roomPanel = new Panel();
        roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        textPanel = new Panel();
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
 
        controlPanel.add(host);
        controlPanel.add(join);
        controlPanel.add(goBack);

        this.add(textPanel);
        this.add(controlPanel);
        this.add(roomPanel);
        this.setVisible(true);
    }
 
    public void RoomButton(Panel panel) {
        Button mkRoomButton = new Button("MAKE ROOM");
        Button joinRoomButton = new Button("JOIN ROOM");
 
        mkRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //statusLabel.setText("mkRoomButton Button clicked.");
                if(isMKroom == false){
                    if(isJNroom == true){
                       jnpanel.setVisible(false);
                       isJNroom = false;   
                    }
                    MakeRoom();
                }
                else{
//                    if(isJNroom == true)
//                        statusLabel.setText("error");
                }
            }
        });
 
        joinRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //statusLabel.setText("joinRoomButton Button clicked.");
                if(isJNroom == false){
                    if(isMKroom == true){
                       mkpanel.setVisible(false);
                       isMKroom = false;   
                    }
                    JoinRoom();
                }
                else{
//                    if(isMKroom == true)
//                        statusLabel.setText("error");
                }
            }
        });
        
        roomPanel.add(mkRoomButton);
        roomPanel.add(joinRoomButton);
        controlPanel.add(roomPanel);
        
        panel.add(roomPanel);
        this.setVisible(true);
    }
    
    public void JoinRoom(){
        jnpanel = new Panel();
        Button enter = new Button("ENTER");
        
        Label port_l = new Label("Port Num");
        Label ip_l = new Label("IP Address");
        TextField port_t = new TextField("",10);
        TextField ip_t = new TextField("",15);
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ip = ip_t.getText();
            	port = port_t.getText();
            	//statusLabel.setText("IP : " + ip + " Port : " + port);
            }
        });
        
        jnpanel.add(ip_l);
        jnpanel.add(ip_t);
        jnpanel.add(port_l);
        jnpanel.add(port_t);
        jnpanel.add(enter);
        
        textPanel.add(jnpanel);
        this.setVisible(true);
        isJNroom = true;    
    }

    public void MakeRoom(){
        mkpanel = new Panel();
        Button enter = new Button("ENTER");
        
        Label port_l = new Label("Port Num");
        TextField port_t = new TextField("",10);
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	port = port_t.getText();
            	//statusLabel.setText("Port : " + port);
            	//Server server = new Server(port);
            }
        });
        
        mkpanel.add(port_l);
        mkpanel.add(port_t);
        mkpanel.add(enter);
        
        textPanel.add(mkpanel);
        this.setVisible(true);
        isMKroom = true;
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
    	}
    }
}



