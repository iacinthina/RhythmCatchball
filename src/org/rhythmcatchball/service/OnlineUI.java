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

import org.rhythmcatchball.core.GameManager;

public class OnlineUI extends Panel {
	private Label statusLabel;
	private String defaultTooltip;
	private Connection connection;

	private Panel textPanel;
	private Panel jnpanel;
	private Panel mkpanel;
	private Container textPanelEntry;
	private String port;
	private String ip;

	private Panel controlPanel;
	private Button host;
	private Button join;
	private Button goBack;
	private Button cancel;
	private Panel cancelPanel;

	private Container update;

	private Thread t;

	public OnlineUI(Container update) {
		port = "";
		ip = "";
		defaultTooltip = "Make your game or join to other";
		this.update = update;
		t = null;

		host = new Button("Make Room");
		join = new Button("Join Room");
		goBack = new Button("Return to Title");
		cancel = new Button("Cancel Connection");

		connection = new Connection();

		prepareGUI();
		setMakeRoom();
		setJoinRoom();
		setCancelation();
		roomButtons();
	}

	private void prepareGUI() {
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

		Button checkMyIP = new Button("What is my IP?(Be careful with this button)");
		checkMyIP.addActionListener((ActionEvent e) -> {
			statusLabel.setText("Your IP is: " + connection.checkMyIP());
			update.setVisible(true);
		});
		controlPanel.add(checkMyIP);

		this.add(statusLabel);
		this.add(textPanel);
		this.add(controlPanel);
		this.setVisible(true);
	}

	private void setTextPanel(Panel change) {
		if (textPanelEntry != null)
			textPanel.remove(textPanelEntry);
		textPanelEntry = change;
		if (change != null)
			textPanel.add(textPanelEntry);
		update.setVisible(true);
	}

	private void roomButtons() {
		host.addActionListener((ActionEvent e) -> {
			resetConnection();
			setTextPanel(mkpanel);
			update.setVisible(true);
		});
		join.addActionListener((ActionEvent e) -> {
			resetConnection();
			setTextPanel(jnpanel);
			update.setVisible(true);
		});
	}

	private void enableButtons() {
		controlPanel.setVisible(true);
		update.setVisible(true);
	}

	private void disableButtons() {
		controlPanel.setVisible(false);
		update.setVisible(true);
	}

	private void waitJoinRoom() {
		t = new Thread() {
			@Override
			public void run() {
				boolean connected = connection.asClient(ip, port);
				if (isInterrupted()) {
					return;
				}
				if (connected) {
					statusLabel.setText("Connection Success");
					GameManager.getref().initOnlineMulti(connection);
				} else {
					statusLabel.setText("Connection Failed... " + connection.getErrMsg());
				}
			}
		};
		t.start();
	}

	private void waitMakeRoom() {
		t = new Thread() {
			@Override
			public void run() {
				boolean connected = connection.asServerWait();
				if (isInterrupted()) {
					return;
				}
				if (connected) {
					statusLabel.setText("Connection Success");
					GameManager.getref().initOnlineMulti(connection);
				} else {
					statusLabel.setText("Connection Failed... " + connection.getErrMsg());
				}
			}
		};
		t.start();
	}

	private void setJoinRoom() {
		jnpanel = new Panel();
		Button enter = new Button("ENTER");

		Label portLable = new Label("Port Num");
		Label ipLabel = new Label("IP Address");
		TextField portTextField = new TextField(port, 10);
		TextField ipTextField = new TextField(ip, 15);

		enter.addActionListener((ActionEvent e) -> {
			ip = ipTextField.getText();
			port = portTextField.getText();
			statusLabel.setText("Waiting for Connection to [" + ip + "]...");

			setTextPanel(cancelPanel);
			disableButtons();
			waitJoinRoom();

			update.setVisible(true);
		});

		jnpanel.add(ipLabel);
		jnpanel.add(ipTextField);
		jnpanel.add(portLable);
		jnpanel.add(portTextField);
		jnpanel.add(enter);
	}

	private void setMakeRoom() {
		mkpanel = new Panel();
		Button enter = new Button("ENTER");

		Label portLabel = new Label("Port Num");
		TextField portTextfield = new TextField(port, 10);

		enter.addActionListener((ActionEvent e) -> {
			port = portTextfield.getText();
			statusLabel.setText("Waiting for Host Creation...");
			if (connection.asServer(port)) {
				statusLabel.setText("Waiting for Connection from client...");
				setTextPanel(cancelPanel);
				disableButtons();
				waitMakeRoom();
			} else {
				statusLabel.setText("Host Creation Failed... " + connection.getErrMsg());
			}
			update.setVisible(true);
		});

		mkpanel.add(portLabel);
		mkpanel.add(portTextfield);
		mkpanel.add(enter);
	}

	private void setCancelation() {
		cancelPanel = new Panel();
		cancelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		cancelPanel.add(cancel);
		cancel.addActionListener((ActionEvent e) -> {
			setTextPanel(null);
			enableButtons();
			resetConnection();
			statusLabel.setText("Connection Canceled");
			if (t != null) {
				t.interrupt();
			}
		});
	}

	private void resetConnection() {
		if (connection.isServer())
			connection.closeServer();
		connection.closeClient();
	}

	public void setActionListener(String button, ActionListener actionListener) {
		if (actionListener == null)
			return;
		switch (button) {
		case "host":
			host.addActionListener(actionListener);
			break;
		case "join":
			join.addActionListener(actionListener);
			break;
		case "goBack":
			goBack.addActionListener(actionListener);
			break;
		case "cancel":
			cancel.addActionListener(actionListener);
			break;
		default:
			// no target
			break;
		}
	}

}