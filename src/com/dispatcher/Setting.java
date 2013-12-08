package com.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Setting extends JFrame implements ActionListener{

	private JTextField defaultField;
	private JButton save;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JTextField maxField;
	private JTextField pathField;

	public Setting() {
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel();
		save = new JButton();
		pathField = new JTextField();
		defaultField = new JTextField();
		maxField = new JTextField();

		getContentPane().setLayout(null);

		jLabel1.setText("Path:");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(20, 50, 48, 14);

		jLabel2.setText("Default Field:");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(20, 90, 100, 14);

		jLabel3.setText("Max no. Keyword:");
		getContentPane().add(jLabel3);
		jLabel3.setBounds(20, 130, 100, 14);

		save.setText("Save");
		getContentPane().add(save);
		save.setBounds(180, 180, 144, 23);
		getContentPane().add(pathField);
		pathField.setBounds(130, 50, 249, 20);
		getContentPane().add(defaultField);
		defaultField.setBounds(130, 90, 249, 20);
		getContentPane().add(maxField);
		maxField.setBounds(130, 130, 251, 20);

		jLabel4.setText("Setting File in c:\\");
		getContentPane().add(jLabel4);
		jLabel4.setBounds(10, 10, 150, 14);

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		setLocationByPlatform(true);
		setTitle("Setting");
		
		save.addActionListener(this);
		
		Util.loadSetting();
		pathField.setText(Util.path);
		defaultField.setText(Util.deaultField);
		maxField.setText(Util.maxNumberOfField + "");
	}

	public static void main(String args[]) {
		new Setting().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(save)) {
			Util.saveSetting(pathField.getText(), defaultField.getText(), maxField.getText());
			dispose();
			
		}
		
	}
}
