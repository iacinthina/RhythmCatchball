package org.rhythmcatchball.service;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.*;

import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.core.UserConfig;

public class ConfigureUI extends Panel {
    private Panel optionPanel;
    private Panel controlPanel;
	
    private UserConfig userConfigCopy;
    private Label volumeText;
    
	private Button proceed;
	private Button goBack;
	
	private Container update;
	
	public ConfigureUI(Container update, UserConfig userConfig)
	{
		userConfigCopy = userConfig;
    	this.setLayout(new BorderLayout());
    	this.update = update;

    	optionPanel = new Panel();
    	optionPanel.setLayout(new GridLayout(4, 2));

    	optionPanel.add(addOptionMusicVolume());
    	
		proceed = new Button("Save Settings");
		goBack = new Button("Return to Title");

    	controlPanel = new Panel();
		controlPanel.add(proceed);
		controlPanel.add(goBack);

		this.add(optionPanel, BorderLayout.CENTER);
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
	
	private Panel addOptionMusicVolume() {
		Panel option = new Panel();
		Button minus = new Button("-");
		Button plus = new Button("+");
		volumeText = new Label();

		minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	volumeChanged(-0.1f);
            }
		});
		plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	volumeChanged(0.1f);
            }
		});
		
		volumeChanged(0);
		
		option.add(volumeText);
		option.add(minus);
		option.add(plus);
		
		return option;
	}
	
	private void volumeChanged(float diff) {
		String vtext = "Volume : ";
		float curVol = clamp(userConfigCopy.getVolume()+diff, 0, 1);
		userConfigCopy.setVolume(curVol);
		vtext = vtext + Math.round(curVol*100);
		
		/*for(float f = 0; f < 1; f += 0.1f) {
			vtext = vtext + ((f <= curVol)?"O":"-");
		}*/
		volumeText.setText(vtext);
		update.setVisible(true);
	}
	
	private float clamp(float x, float min, float max) {
		return Math.min(max, Math.max(x, min));
	}
}
