package org.rhythmcatchball.service;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OnlineUI {
    private Frame mainFrame;
    private Label headerLabel;
    private Label statusLabel;
    private Panel roomPanel;
    private Panel textPanel;
    private boolean isMKroom = false;
    private boolean isJNroom = false;
    private Panel jnpanel;
    private Panel mkpanel;
    String port;
    String ip;
    
    public OnlineUI() {
        prepareGUI();
    }
 
    public static void main(String[] args) {
        OnlineUI mainControl = new OnlineUI();
        mainControl.RoomButton();
        
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
 
        headerLabel = new Label();
        headerLabel.setAlignment(Label.CENTER);
        headerLabel.setText("Select Mode");
 
        statusLabel = new Label();
        statusLabel.setText("click button");
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350, 100);
 
        roomPanel = new Panel();
        roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        textPanel = new Panel();
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
 
        mainFrame.add(headerLabel);
        mainFrame.add(roomPanel);
        mainFrame.add(textPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }
 
    private void RoomButton() {
        Button mkRoomButton = new Button("MAKE ROOM");
        Button joinRoomButton = new Button("JOIN ROOM");
 
        mkRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("mkRoomButton Button clicked.");
                if(isMKroom == false){
                    if(isJNroom == true){
                       jnpanel.setVisible(false);
                       isJNroom = false;   
                    }
                    MakeRoom();
                }
                else{
                    if(isJNroom == true)
                        statusLabel.setText("error");
                }
            }
        });
 
        joinRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("joinRoomButton Button clicked.");
                if(isJNroom == false){
                    if(isMKroom == true){
                       mkpanel.setVisible(false);
                       isMKroom = false;   
                    }
                    JoinRoom();
                }
                else{
                    if(isMKroom == true)
                        statusLabel.setText("error");
                }
            }
        });
        
        roomPanel.add(mkRoomButton);
        roomPanel.add(joinRoomButton);
 
        mainFrame.setVisible(true); 
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
            	statusLabel.setText("IP : " + ip + " Port : " + port);
            	Client client = new Client(ip, port);
            }
        });
        
        jnpanel.add(ip_l);
        jnpanel.add(ip_t);
        jnpanel.add(port_l);
        jnpanel.add(port_t);
        jnpanel.add(enter);
        
        textPanel.add(jnpanel);
        
        //jnpanel.setVisible(false);
        isJNroom = true;
        mainFrame.setVisible(true);        
    }

    public void MakeRoom(){
        mkpanel = new Panel();
        Button enter = new Button("ENTER");
        
        Label port_l = new Label("Port Num");
        TextField port_t = new TextField("",10);
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	port = port_t.getText();
            	statusLabel.setText("Port : " + port);
            	Server server = new Server(port);
            }
        });
        
        mkpanel.add(port_l);
        mkpanel.add(port_t);
        mkpanel.add(enter);
        
        textPanel.add(mkpanel);
        //mkpanel.setVisible(false);
        isMKroom = true;
        mainFrame.setVisible(true);        
    }
}



