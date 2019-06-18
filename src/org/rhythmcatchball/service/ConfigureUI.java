package org.rhythmcatchball.service;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.*;
import java.util.ArrayList;

import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.core.GameSound;
import org.rhythmcatchball.core.Key;
import org.rhythmcatchball.core.UserConfig;

public class ConfigureUI extends Panel implements KeyListener {
	private class KeySetInfo {
		TextField keyName;
		int[] keySet;
		Key inputKey;
		
		KeySetInfo(TextField keyName, int[] keySet, Key inputKey) {
			this.keyName = keyName;
			this.keySet = keySet;
			this.inputKey = inputKey;
		}
		
		String getText() {
			int keyCode = keySet[inputKey.getIndex()];
			return KeyEvent.getKeyText(keyCode);
		}
		
		void update() {
			keyName.setEditable(false);
			keyName.setText(getText());
		}
		
		void changeKey(int keyCode) {
			keySet[inputKey.getIndex()] = keyCode;
			update();
		}
	}
	
	
    private Panel optionPanel;
    private Panel controlPanel;
	
    private UserConfig userConfigCopy;
    private Label volumeText;
    
	private Button proceed;
	private Button goBack;
	private Button applyRes;
	
	private Container update;
	
	private boolean listenerAdded;
	
	private ArrayList<KeySetInfo> keySetList;
	private KeySetInfo focusKeySet;
	
	public ConfigureUI(Container update, UserConfig userConfig)
	{
		userConfigCopy = new UserConfig();
		userConfigCopy.copy(userConfig);
    	this.setLayout(new BorderLayout());
    	this.update = update;

    	optionPanel = new Panel();
    	optionPanel.setLayout(new GridLayout(8, 2));

    	optionPanel.add(addOptionMusicVolume());
    	optionPanel.add(addOptionResolution("Resoulution"));

		keySetList = new ArrayList<>();
    	int[] key1Set = userConfigCopy.getKey1Set();
    	int[] key2Set = userConfigCopy.getKey2Set();

    	optionPanel.add(addOptionText("Player 1 Control"));
    	optionPanel.add(addOptionText("Player 2 Control"));
    	optionPanel.add(addOptionKeySetting(key1Set, Key.LOW));
    	optionPanel.add(addOptionKeySetting(key2Set, Key.LOW));
    	optionPanel.add(addOptionKeySetting(key1Set, Key.MIDDLE));
    	optionPanel.add(addOptionKeySetting(key2Set, Key.MIDDLE));
    	optionPanel.add(addOptionKeySetting(key1Set, Key.HIGH));
    	optionPanel.add(addOptionKeySetting(key2Set, Key.HIGH));
    	optionPanel.add(addOptionKeySetting(key1Set, Key.RECEIVE));
    	optionPanel.add(addOptionKeySetting(key2Set, Key.RECEIVE));
    	
		proceed = new Button("Save Settings");
		proceed.addActionListener( 
			(ActionEvent e) -> {
				userConfig.copy(userConfigCopy);
				updateKeySet();
				GameManager.getref().updateResolution();
			}
		);
		
		goBack = new Button("Return to Title");
		goBack.addActionListener( 
			(ActionEvent e) -> {
				userConfigCopy.copy(userConfig);
				updateKeySet();
				GameManager.getref().removeKeyListener(this);
				listenerAdded = false;
			}
		);
		
		applyRes = new Button("Return to Title");
		applyRes.addActionListener( 
				(ActionEvent e) -> {
					
				}
			);

    	controlPanel = new Panel();
		controlPanel.add(proceed);
		controlPanel.add(goBack);

		this.add(optionPanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
		
		listenerAdded = false;
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

		minus.addActionListener(
			(ActionEvent e) -> volumeChanged(-0.1f)
		);
		plus.addActionListener(
			(ActionEvent e) -> volumeChanged(0.1f)
		);
		
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
		GameSound.play("snd_beat", curVol);
		volumeText.setText(vtext);
		update.setVisible(true);
	}
	
	private Panel addOptionKeySetting(int[] keySet, Key inputKey) {
		Panel option = new Panel(new BorderLayout(10, 20));
		Label inputText = new Label(inputKey.toString());

		option.add(inputText, BorderLayout.WEST);
		
		if (keySet != null) {
			TextField keyName = new TextField();
			KeySetInfo keySetInfo = new KeySetInfo(keyName, keySet, inputKey);
			keySetInfo.update();
			keySetList.add(keySetInfo);
	
			Button change = new Button("Change");
			change.addActionListener(
				(ActionEvent e) -> {
					updateKeySet();
					if (!listenerAdded) {
						GameManager.getref().addKeyListener(this);
						listenerAdded = true;
					}
					focusKeySet = keySetInfo;
					keyName.setText("Press a key");
					keyName.setEditable(true);
					GameManager.getref().requestFocus();
				}
			);
			
			option.add(keyName, BorderLayout.CENTER);
			option.add(change, BorderLayout.EAST);
		}
		
		return option;
	}

	private Panel addOptionText(String text) {
		Panel option = new Panel();
		Label inputText = new Label(text);
		option.add(inputText);
		return option;
	}
	
	
	private Panel addOptionResolution(String text) {
		Panel option = new Panel();
		Label inputText = new Label(text);
		Choice resMenu = new Choice();
		
		String[] resList = {
				"640x360",
				"800x450",
				"854x480",
				"960x540",
				"1280x720",
				"1366x768",
				"1440x810",
				"1600x900",
				"1920x1080"
		};
		String current = userConfigCopy.getResWidth()+"x"+userConfigCopy.getResHeight();
		String res;
		for(int i = 0; i < resList.length; i++) {
			res = resList[i];
			resMenu.add(res);
			if (res.equals(current)) {
				resMenu.select(i);
			}
		}
		
		int width_min = 320;
		int height_min = 180;
		resMenu.addItemListener(
			(ItemEvent e) -> {
				String[] resInfo = resMenu.getSelectedItem().split("x");
				int width = Math.max(width_min, Integer.parseInt(resInfo[0]));
				int height = Math.max(height_min, Integer.parseInt(resInfo[1]));
				userConfigCopy.setResolusion(width, height);
			}
		);
		
		option.add(inputText);
		option.add(resMenu);
		
		return option;
	}
	
	private void keyChanged(int[] keySet, int index) {

	}
	
	private float clamp(float x, float min, float max) {
		return Math.min(max, Math.max(x, min));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (focusKeySet != null) {
			focusKeySet.changeKey(e.getKeyCode());
			focusKeySet = null;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	private void updateKeySet() {
		for(KeySetInfo info : keySetList) {
			info.update();
		}
	}
}
