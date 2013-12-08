package com.dispatcher;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class AddItem extends JDialog implements ActionListener {

	private JComboBox domainList;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JTextField itemName;
	private JScrollPane jScrollPane1;
	private JTextArea itemText;
	private JButton saveItem;
	DefaultComboBoxModel domainModel;
	List list;

	public AddItem(JFrame parent) {
		super(parent, "Add Item", true);
		jLabel1 = new JLabel();
		domainList = new JComboBox();
		jLabel2 = new JLabel();
		jScrollPane1 = new JScrollPane();
		itemText = new JTextArea();
		saveItem = new JButton();
		domainModel = new DefaultComboBoxModel();

		itemName = new JTextField();
		jLabel3 = new JLabel();

		preparedata();

		getContentPane().setLayout(null);

		jLabel1.setText("Domain :");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(10, 50, 50, 10);

		getContentPane().add(domainList);
		domainList.setBounds(80, 50, 130, 20);
		domainList.setModel(domainModel);

		jLabel2.setText("Value :");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(10, 70, 100, 14);

		itemText.setColumns(20);
		itemText.setRows(5);
		jScrollPane1.setViewportView(itemText);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(20, 90, 210, 96);

		setBounds(100, 100, 300, 300);
		getContentPane().setLayout(null);

		// setLocationByPlatform(true);
		setTitle("Add Item");

		itemName.setText("");
		getContentPane().add(itemName);
		itemName.setBounds(80, 20, 130, 20);

		jLabel3.setText("Name :");
		getContentPane().add(jLabel3);
		jLabel3.setBounds(10, 20, 60, 14);

		saveItem.setText("save");
		getContentPane().add(saveItem);
		saveItem.setBounds(90, 190, 100, 23);
		saveItem.addActionListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	void preparedata() {
		File taskFile = new File(Util.path + "\\Fields");
		domainModel.removeAllElements();
		for (int i = 0; i < taskFile.listFiles().length; i++) {
			System.out.println(taskFile.listFiles()[i]);
			if (taskFile.listFiles()[i].isDirectory()) {
				domainModel.addElement(taskFile.listFiles()[i].getName());
			}
		}

	}

	public static void main(String args[]) {
		// new AddItem().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(saveItem)) {
			File taskFile = new File(Util.path + "\\Fields\\"
					+ domainList.getSelectedItem().toString());
			list = Util.allFiles(Util.path + "\\Fields\\"
					+ domainList.getSelectedItem().toString());

			File f = new File(Util.path + "\\Item\\" + itemName.getText()
					+ ".txt");
			FileWriter fstream;
			try {
				String data = itemText.getText();
				String ss[] = data.split(",");
				if (ss.length > Util.maxNumberOfField) {
					JOptionPane.showMessageDialog(this, "Max Number Of Field in Item must be <= " + Util.maxNumberOfField);
					return;
				}

				for (int i = 0; i < ss.length; i++) {
					if (!list.contains(ss[i].trim())) {
						JOptionPane.showMessageDialog(this, ss[i]
								+ "  not found");
						return;
					}
				}
				data = data + System.getProperty("line.separator")
						+ domainList.getSelectedItem().toString();
				fstream = new FileWriter(f);
				BufferedWriter out = new BufferedWriter(fstream);
				f.createNewFile();
				out.write(data);
				// Close the output stream
				out.close();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			dispose();

		}

	}

}
