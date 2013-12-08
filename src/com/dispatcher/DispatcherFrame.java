package com.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class DispatcherFrame extends JFrame implements ActionListener {

	private JButton addCase;
	private JButton addKeyword;
	private JList caseList;
	private JButton deleteCase;
	private JButton deleteKeyword;
	private JButton ipCase;
	private JButton load;
	private JScrollPane jScrollPane2;
	// private JTextField path;
	private JButton updateCase;
	private JButton renameKeyword;
	DefaultListModel caseModel;
	DefaultComboBoxModel domainModel, keywordModel;
	private JComboBox domainComboBox;
	private JComboBox keywordComboBox;
	// private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JButton addDomain;
	private JButton deleteDomain;
	private JMenu jMenu1;
	private JMenuBar jMenuBar1;
	private JMenuItem setting;
	List<Item> items ;
	private JButton visualizeAll;

	public DispatcherFrame() {
		initComponents();
	}

	private void initComponents() {

		domainModel = new DefaultComboBoxModel();
		keywordModel = new DefaultComboBoxModel();
		caseModel = new DefaultListModel();

		// jLabel1 = new JLabel();
		// path = new JTextField();
		load = new JButton();
		jPanel1 = new JPanel();
		addKeyword = new JButton();
		renameKeyword = new JButton();
		deleteKeyword = new JButton();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		addDomain = new JButton();
		deleteDomain = new JButton();
		domainComboBox = new JComboBox();
		keywordComboBox = new JComboBox();
		jPanel2 = new JPanel();
		jScrollPane2 = new JScrollPane();
		caseList = new JList();
		addCase = new JButton();
		updateCase = new JButton();
		deleteCase = new JButton();
		ipCase = new JButton();
		jMenuBar1 = new JMenuBar();
		jMenu1 = new JMenu();
		setting = new JMenuItem();
		visualizeAll = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setResizable(false);
		setBounds(100, 100, 500, 500);
		getContentPane().setLayout(null);

		// jLabel1.setText(" Path :");
		// getContentPane().add(jLabel1);
		// jLabel1.setBounds(30, 20, 39, 14);
		// getContentPane().add(path);
		// path.setBounds(70, 20, 290, 20);
		//
		load.setText("Load");
		getContentPane().add(load);
		load.setBounds(150, 20, 150, 23);

		jPanel2.setBorder(BorderFactory.createTitledBorder("Items"));
		jPanel2.setLayout(null);

		jScrollPane2.setViewportView(caseList);

		jPanel2.add(jScrollPane2);
		jScrollPane2.setBounds(20, 20, 190, 120);

		addCase.setText("ADD");
		jPanel2.add(addCase);
		addCase.setBounds(250, 20, 140, 23);

		updateCase.setText("Update");
		jPanel2.add(updateCase);
		updateCase.setBounds(250, 50, 140, 23);

		deleteCase.setText("Delete");
		jPanel2.add(deleteCase);
		deleteCase.setBounds(250, 80, 140, 23);

		ipCase.setText("Visualize Field");
		jPanel2.add(ipCase);
		ipCase.setBounds(250, 110, 140, 23);
		
		visualizeAll.setText("Visualize All");
		jPanel2.add(visualizeAll);
		visualizeAll.setBounds(250, 140, 140, 23);

		getContentPane().add(jPanel2);
		jPanel2.setBounds(20, 240, 400, 190);

		jPanel1.setBorder(BorderFactory.createTitledBorder("Fields"));
		jPanel1.setLayout(null);

		addKeyword.setText("ADD");
		jPanel1.add(addKeyword);
		addKeyword.setBounds(90, 110, 80, 23);

		renameKeyword.setText("Update");
		jPanel1.add(renameKeyword);
		renameKeyword.setBounds(180, 110, 80, 23);

		deleteKeyword.setText("Delete");
		jPanel1.add(deleteKeyword);
		deleteKeyword.setBounds(270, 110, 80, 23);

		jLabel2.setText("Keyword :");
		jPanel1.add(jLabel2);
		jLabel2.setBounds(20, 80, 60, 14);

		jLabel3.setText(" Domain :");
		jPanel1.add(jLabel3);
		jLabel3.setBounds(20, 20, 80, 14);

		addDomain.setText("ADD");
		jPanel1.add(addDomain);
		addDomain.setBounds(90, 50, 80, 23);

		deleteDomain.setText("Delete");
		jPanel1.add(deleteDomain);
		deleteDomain.setBounds(180, 50, 80, 23);

		jPanel1.add(domainComboBox);
		domainComboBox.setBounds(85, 20, 140, 20);

		jPanel1.add(keywordComboBox);
		keywordComboBox.setBounds(85, 80, 140, 20);

		getContentPane().add(jPanel1);
		jPanel1.setBounds(20, 60, 400, 162);

		jMenu1.setText("File");

		setting.setText("Setting");
		setting.addActionListener(this);
		jMenu1.add(setting);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		load.addActionListener(this);
		addKeyword.addActionListener(this);
		deleteKeyword.addActionListener(this);
		renameKeyword.addActionListener(this);
		addCase.addActionListener(this);
		updateCase.addActionListener(this);
		deleteCase.addActionListener(this);
		ipCase.addActionListener(this);
		visualizeAll.addActionListener(this);
		addDomain.addActionListener(this);
		deleteDomain.addActionListener(this);
		domainComboBox.addActionListener(this);
		domainComboBox.setModel(domainModel);
		keywordComboBox.setModel(keywordModel);
		caseList.setModel(caseModel);
		setLocationByPlatform(true);
		setTitle("Dispatcher");
		setSize(500, 500);

		// pack();
	}

	List<String> allTask = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		File taskFile = null, caseFile = null;
		Util.loadSetting();
		if (Util.path.equals("")) {
			JOptionPane.showMessageDialog(this, "please enter setting");
			new Setting().setVisible(true);
			return;
		} else {
			try {
				taskFile = new File(Util.path + "\\Fields");
				caseFile = new File(Util.path + "\\Item");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "can't open file");
				return;
			}
		}
		if (e.getSource().equals(load)) {
			try {
				allTask = new ArrayList<String>();
				allFiles(Util.path + "\\Fields");
				for (String s : allTask) {
					System.out.println("allTask " + s);
				}
				domainModel.removeAllElements();
				System.out.println("domainModel " + taskFile.getAbsolutePath());
				for (int i = 0; i < taskFile.listFiles().length; i++) {
					System.out.println(taskFile.listFiles()[i]);
					if (taskFile.listFiles()[i].isDirectory()) {
						domainModel.addElement(taskFile.listFiles()[i]
								.getName());
					}
					// String task = taskFile.listFiles()[i].getName();
					// domainModel.addElement(task.substring(0,
					// task.lastIndexOf('.')));
				}

				caseModel.removeAllElements();
				for (int i = 0; i < caseFile.listFiles().length; i++) {
					System.out.println(caseFile.listFiles()[i]);
					String task = caseFile.listFiles()[i].getName();
					caseModel.addElement(task.substring(0,
							task.lastIndexOf('.')));
				}

			} catch (Exception e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error");
				return;
			}

		} else if (e.getSource().equals(addDomain)) {
			String taskName = JOptionPane.showInputDialog(this,
					"Enter Domain Name:");
			if (taskName.equals("")) {
				JOptionPane.showMessageDialog(this, "Task Name can't be empty");
				return;
			} else {
				File f = new File(taskFile.getAbsolutePath() + "\\" + taskName);
				try {
					f.mkdir();
					domainModel.addElement(taskName);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		} else if (e.getSource().equals(deleteDomain)) {
			System.out.println(domainComboBox.getSelectedItem().toString());
			File f = new File(taskFile.getAbsolutePath() + "\\"
					+ domainComboBox.getSelectedItem().toString());
			try {
				delete(f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			domainModel.removeElementAt(domainComboBox.getSelectedIndex());
		} else if (e.getSource().equals(domainComboBox)) {
			// JComboBox cb = (JComboBox) e.getSource();
			String domainName = (String) domainComboBox.getSelectedItem();
			// JOptionPane.showMessageDialog(this, domainName);
			File f = new File(taskFile.getAbsolutePath() + "\\" + domainName);
			keywordModel.removeAllElements();
			System.out.println("domainComboBox" + f.getAbsolutePath());
			for (int i = 0; i < f.listFiles().length; i++) {
				System.out.println(f.listFiles()[i]);
				String keywordName = f.listFiles()[i].getName();
				keywordModel.addElement(keywordName.substring(0,
						keywordName.lastIndexOf('.')));

			}

		} else if (e.getSource().equals(addKeyword)) {
			String keywordName = JOptionPane.showInputDialog(this,
					"Enter Keyword Name:");
			if (keywordName.equals("")) {
				JOptionPane.showMessageDialog(this, "Task Name can't be empty");
			} else {
				File f = new File(taskFile.getAbsolutePath() + "\\"
						+ domainComboBox.getSelectedItem().toString() + "\\"
						+ keywordName + ".txt");
				try {
					f.createNewFile();
					keywordModel.addElement(keywordName);
					allTask.add(keywordName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (e.getSource().equals(deleteKeyword)) {
			try {
				System.out
						.println(keywordComboBox.getSelectedItem().toString());
				File f = new File(taskFile.getAbsolutePath() + "\\"
						+ domainComboBox.getSelectedItem().toString() + "\\"
						+ keywordComboBox.getSelectedItem().toString() + ".txt");
				f.delete();
				allTask.remove(keywordComboBox.getSelectedItem().toString());
				keywordModel
						.removeElementAt(keywordComboBox.getSelectedIndex());

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Please select Task ");
			}
		} else if (e.getSource().equals(renameKeyword)) {
			String newTaskName = "";
			try {
				newTaskName = JOptionPane.showInputDialog(this,
						"Enter New Task Name:", keywordComboBox
								.getSelectedItem().toString());
				if (newTaskName.equals("")) {
					JOptionPane.showMessageDialog(this, "Enter new Name");
				}
			} catch (Exception e2) {
				return;
			}
			System.out.println(keywordComboBox.getSelectedItem().toString());
			File f = new File(taskFile.getAbsolutePath() + "\\"
					+ domainComboBox.getSelectedItem().toString() + "\\"
					+ keywordComboBox.getSelectedItem().toString() + ".txt");
			File f2 = new File(taskFile.getAbsolutePath() + "\\"
					+ domainComboBox.getSelectedItem().toString() + "\\"
					+ newTaskName + ".txt");
			f.renameTo(f2);
			allTask.remove(keywordComboBox.getSelectedItem().toString());
			allTask.add(newTaskName);
			keywordModel.removeElementAt(keywordComboBox.getSelectedIndex());
			keywordModel.addElement(newTaskName);

		} else if (e.getSource().equals(addCase)) {
			new AddItem(this).setVisible(true);
			System.out.println("addCase");
			loadItems();
			// String caseName = JOptionPane.showInputDialog(this,
			// "Enter Case Name:");
			// if (caseName.equals("")) {
			// JOptionPane.showMessageDialog(this, "Case Name can't be empty");
			// } else {
			// File f = new File(caseFile.getAbsolutePath() + "\\" + caseName
			// + ".txt");
			// try {
			// f.createNewFile();
			// caseModel.addElement(caseName);
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			//
			// }
		} else if (e.getSource().equals(deleteCase)) {
			try {
				System.out.println(caseList.getSelectedValue().toString());
				File f = new File(caseFile.getAbsolutePath() + "\\"
						+ caseList.getSelectedValue().toString() + ".txt");
				f.delete();
				caseModel.removeElementAt(caseList.getSelectedIndex());

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Please select Case ");
			}
		} else if (e.getSource().equals(updateCase)) {
			try {
				new EditItem(this, caseFile.getAbsolutePath() + "\\"
						+ caseList.getSelectedValue().toString() + ".txt").setVisible(true);				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Please select Case ");
			}

			// File file = new File(caseFile.getAbsolutePath() + "\\"
			// + caseList.getSelectedValue().toString() + ".txt");
			// StringBuilder contents = new StringBuilder();
			// BufferedReader reader = null;
			// FileWriter fstream;
			// try {
			// reader = new BufferedReader(new FileReader(file));
			// String text = null;
			//
			// // repeat until all lines is read
			// while ((text = reader.readLine()) != null) {
			// contents.append(text).append(
			// System.getProperty("line.separator"));
			// }
			// } catch (IOException ex) {
			// ex.printStackTrace();
			// } finally {
			// try {
			// if (reader != null) {
			// reader.close();
			// }
			// } catch (IOException ex) {
			// ex.printStackTrace();
			// }
			// }
			// String data = "";
			// try {
			// data = JOptionPane.showInputDialog(this, "Data", contents);
			// String ss[] = data.split(",");
			// for (int i = 0; i < ss.length; i++) {
			// if (!allTask.contains(ss[i].trim())) {
			// JOptionPane
			// .showMessageDialog(this, ss[i] + "not found");
			// return;
			// }
			// }
			// try {
			// fstream = new FileWriter(caseFile.getAbsolutePath() + "\\"
			// + caseList.getSelectedValue().toString() + ".txt");
			// BufferedWriter out = new BufferedWriter(fstream);
			// out.write(data);
			// // Close the output stream
			// out.close();
			// } catch (Exception e1) {
			// return;
			// }
			// } catch (Exception e2) {
			// return;
			// }

		} else if (e.getSource().equals(ipCase)) {
			
				// Random random = new Random();
				// JOptionPane.showMessageDialog(this, "ip : "
				// + showRandomInteger(1000, 9999, random));
				
				
				new VisualizeFields(loadAllItems(), domainComboBox.getSelectedItem().toString()).setVisible(true);

			
		} else if (e.getSource().equals(setting)) {
			new Setting().setVisible(true);
		}else if (e.getSource().equals(visualizeAll)) {
			new VisualizeAll(Arrays.asList(caseFile.listFiles())).setVisible(true);
		}
	}

	private static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				System.out.println("Directory is deleted : "
						+ file.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}

	public void allFiles(String s) {

		File root = new File(s);
		System.out.println(root.getAbsolutePath());
		File[] list = root.listFiles();

		for (File f : list) {
			if (f.isDirectory()) {
				allFiles(f.getAbsolutePath());
				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
				allTask.add(f.getName().substring(0,
						f.getName().lastIndexOf('.')));
				System.out.println("File:" + f.getAbsoluteFile());
			}
		}
	}

	public void loadItems() {
		System.out.println("loadItems");
		File caseFile = new File(Util.path + "\\Item");
		caseModel.removeAllElements();
		for (int i = 0; i < caseFile.listFiles().length; i++) {
			System.out.println(caseFile.listFiles()[i]);
			String task = caseFile.listFiles()[i].getName();
			caseModel.addElement(task.substring(0, task.lastIndexOf('.')));
		}
	}
	
	public List<Item> loadAllItems() {
		File caseFile = new File(Util.path + "\\Item");
		List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < caseFile.listFiles().length; i++) {
			StringBuilder contents = new StringBuilder();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(caseFile.listFiles()[i]));
				String text = null;

				// repeat until all lines is read
				while ((text = reader.readLine()) != null) {
					contents.append(text).append(
							System.getProperty("line.separator"));
				}
				
				Item item = new Item(caseFile.listFiles()[i].getName().substring(0,
						caseFile.listFiles()[i].getName().lastIndexOf('.')), contents.toString().split(System.getProperty("line.separator"))[0], contents.toString().split(System.getProperty("line.separator"))[1]);
				items.add(item);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return items;
	}

	public static void main(String args[]) {

		new DispatcherFrame().setVisible(true);

	}
}
