package com.fishswarm;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Aquarium extends Frame implements Runnable, ActionListener {
	MediaTracker tracker;
	Image aquariumImage, memoryImage;
	Graphics memoryGraphics;
	Button buttonHide, buttonShow, buttonDel, buttonAdd;
	Choice choice; 
	int lastFishIndex = 0;
	private static int NUMBER_DELETED_FISH = 2;

	Image[] fishImages = new Image[2];
	List<Integer> delIndex = new LinkedList<Integer>();
	boolean runOK = true;

	Thread thread;

	int numberFish = 9;

	int sleepTime = 110;

	Vector fishes = new Vector();

	Aquarium() {
		setTitle("The Aquarium");

		tracker = new MediaTracker(this);

		fishImages[0] = Toolkit.getDefaultToolkit().getImage("fish1.gif");

		tracker.addImage(fishImages[0], 0);

		fishImages[1] = Toolkit.getDefaultToolkit().getImage("fish2.gif");
		tracker.addImage(fishImages[1], 0);

		aquariumImage = Toolkit.getDefaultToolkit().getImage("bubbles.gif");
		tracker.addImage(aquariumImage, 0);

		try {
			tracker.waitForID(0);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		buttonHide = new Button("Hide");
		buttonHide.setBounds(10, aquariumImage.getHeight(this) + 10, 40, 20);
		add(buttonHide);
		buttonHide.addActionListener(this);
		

		buttonShow = new Button("Show");
		buttonShow.setBounds(80, aquariumImage.getHeight(this) + 35, 40, 20);
		add(buttonShow);
		buttonShow.addActionListener(this);
		
		buttonDel = new Button("Del");
		buttonDel.setBounds(125, aquariumImage.getHeight(this) + 35, 40, 20);
		add(buttonDel);
		buttonDel.addActionListener(this);
		
		buttonAdd = new Button("Add");
		buttonAdd.setBounds(190, aquariumImage.getHeight(this) + 10, 40, 20);
		add(buttonAdd);
		buttonAdd.addActionListener(this);

		choice = new Choice();
		choice.setBounds(80, aquariumImage.getHeight(this) + 10, 100, 40);
		add(choice);
		
		
		setSize(aquariumImage.getWidth(this),
				aquariumImage.getHeight(this) + 60);
		setLayout(null);
		setResizable(false);

		setVisible(true);

		memoryImage = createImage(getSize().width, getSize().height);
		memoryGraphics = memoryImage.getGraphics();

		thread = new Thread(this);
		thread.start();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				runOK = false;

				System.exit(0);
			}
		});
	}

	public void run() {
		Rectangle edges = new Rectangle(0 + getInsets().left,
				0 + getInsets().top, getSize().width
						- (getInsets().left + getInsets().right),
				getSize().height - (getInsets().top + getInsets().bottom) - 60);

		for (int loopIndex = 0; loopIndex < numberFish; loopIndex++) {
			Fish fish = new Fish(loopIndex + 1, fishImages[0], fishImages[1],
					edges, this);
			lastFishIndex = loopIndex + 1;
			choice.add(fish.id + "");
			fishes.add(fish);

			try {
				Thread.sleep(20);
			} catch (Exception exp) {
				System.out.println(exp.getMessage());
			}
		}

		Fish fish;
		while (runOK) {
			for (int loopIndex = 0; loopIndex < fishes.size(); loopIndex++) {
				fish = (Fish) fishes.elementAt(loopIndex);
				fish.swim();
			}

			try {
				Thread.sleep(sleepTime);
			} catch (Exception exp) {
				System.out.println(exp.getMessage());
			}
			repaint();

		}
	}

	public void update(Graphics g) {
		memoryGraphics.drawImage(aquariumImage, 0, 0, this);
		for (int loopIndex = 0; loopIndex < fishes.size(); loopIndex++) {
			((Fish) fishes.elementAt(loopIndex)).drawFishImage(memoryGraphics);
		}
		g.drawImage(memoryImage, 0, 0, this);
	}

	public static void main(String[] args) {
		Aquarium a = new Aquarium();
		// ControlPanel p = new ControlPanel(a);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(buttonHide)) {
			System.out.println("hide");
			System.out.println(fishes.size());
			
			int temp = fishes.size() / 2;
			System.out.println(temp);
			
			if (fishes.size() > 1) {
				for (int i = 0; i < temp ; i++) {
					fishes.remove(fishes.size()-1);
					choice.remove(fishes.size());
				}
			} else {
				JOptionPane.showMessageDialog(this, "Can't delete last Fish");
			}

		} else if (e.getSource().equals(buttonShow)){
			System.out.println(fishes.indexOf(new Fish(Integer.parseInt(choice.getSelectedItem()))));
			int fishIndex = fishes.indexOf(new Fish(Integer.parseInt(choice.getSelectedItem())));
			Fish fish = (Fish) fishes.get(fishIndex);
			String s = JOptionPane.showInputDialog(this, "Fish Task: " , fish.task);
			fish.task = s;
		}else if (e.getSource().equals(buttonDel)){
			int fishIndex = fishes.indexOf(new Fish(Integer.parseInt(choice.getSelectedItem())));
			if (fishes.size() > 1) {
				delIndex.add(((Fish)fishes.get(fishIndex)).id);
				choice.remove(fishIndex);
				fishes.remove(fishIndex);				
			}else {
				JOptionPane.showMessageDialog(this, "Can't delete last Fish");
			}
		}else if (e.getSource().equals(buttonAdd)){
			Rectangle edges = new Rectangle(0 + getInsets().left,
					0 + getInsets().top, getSize().width
							- (getInsets().left + getInsets().right),
					getSize().height - (getInsets().top + getInsets().bottom) - 60);
			Fish fish;
			if (delIndex.size() > 0) {
				 Collections.sort(delIndex);
				 fish = new Fish(delIndex.get(0), fishImages[0], fishImages[1],
						edges, this);
				 delIndex.remove(0);
			}else {
				lastFishIndex++;
				fish = new Fish(lastFishIndex, fishImages[0], fishImages[1],
						edges, this);				
			}
			fishes.add(fish);
			choice.add(fish.id+"");
		}
		
		
	}

}
