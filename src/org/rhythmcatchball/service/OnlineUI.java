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
    private Panel controlPanel;			//roomPanel과 textPanel이 올라갈 panel
    
    //방만들기와 방 들어가기할 때 텍스트가 두개 동시에 뜨지 않도록 체크하는 변수
    private boolean isMKroom = false;
    private boolean isJNroom = false;
    
    private Panel jnpanel;
    private Panel mkpanel;
    
    //port와 ip 저장하는 변수
    String port;
    String ip;
    
    public OnlineUI() {
        prepareGUI();		//패널 미리 만들어 놓음
        //RoomButton();		// 버튼 만드는 함수
    }
 
 
    private void prepareGUI() {        
    	this.setLayout(new GridLayout(4, 1));
    	
    	controlPanel = new Panel();
    	controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
        roomPanel = new Panel();
        roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        textPanel = new Panel();
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
 
        controlPanel.add(roomPanel);
        controlPanel.add(textPanel);
        this.add(controlPanel);
        this.setVisible(true);
    }
 
    public void RoomButton(Panel up_panel, Panel down_panel) {
        Button mkRoomButton = new Button("MAKE ROOM");
        Button joinRoomButton = new Button("JOIN ROOM");
 
        mkRoomButton.addActionListener(MakeRoomActionListener(down_panel));
        joinRoomButton.addActionListener(JoinRoomActionListener(down_panel));
        
        roomPanel.add(mkRoomButton);
        roomPanel.add(joinRoomButton);
        //controlPanel.add(roomPanel);
        //controlPanel.add(textPanel);
        
        up_panel.add(roomPanel);
        down_panel.add(textPanel);
        
        this.setVisible(true);
    }
    
    public void JoinRoom(Panel down_panel){
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
        
        down_panel.add(jnpanel);
        down_panel.setVisible(true);
        isJNroom = true;    
    }

    public void MakeRoom(Panel down_panel){
        mkpanel = new Panel();
        Button enter = new Button("ENTER");
        
        Label port_l = new Label("Port Num");
        TextField port_t = new TextField("",10);
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	port = port_t.getText();
            }
        });
        
        mkpanel.add(port_l);
        mkpanel.add(port_t);
        mkpanel.add(enter);
        
        down_panel.add(mkpanel);
        down_panel.setVisible(true);
        isMKroom = true;
    }
    
    public ActionListener MakeRoomActionListener(Panel down_panel) {
    	return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isMKroom == false){
                    if(isJNroom == true){
                       jnpanel.setVisible(false);
                       isJNroom = false;   
                    }
                    MakeRoom(down_panel);
                }
                else{
//                    if(isJNroom == true)
//                        statusLabel.setText("error");
                }
            }
        };
    }
    
    public ActionListener JoinRoomActionListener(Panel down_panel) {
    	return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //statusLabel.setText("joinRoomButton Button clicked.");
                if(isJNroom == false){
                    if(isMKroom == true){
                       mkpanel.setVisible(false);
                       isMKroom = false;   
                    }
                    JoinRoom(down_panel);
                }
                else{
//                    if(isMKroom == true)
//                        statusLabel.setText("error");
                }
            }
        };
    }
}



