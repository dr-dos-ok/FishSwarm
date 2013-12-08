package com.dispatcher;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.List;

import javax.swing.*;

public class VisualizeFields extends JFrame {
	List<Item> items = null;
	String domain = "";

	VisualizeFields() {

	}

	VisualizeFields(List<Item> items, String domain) {
		this();
		this.items = items;
		this.domain = domain;

		setLocationByPlatform(true);
		setResizable(false);
		int i = 0;
		for (Item it : items) {
			if (it.getDomain().equals(domain)) {
				i++;
			}
		}
		System.out.println("Items.size() " + items.size());
		int height = ((i / 4) + 1) * 160;

		setBounds(100, 100, 500, height);
		getContentPane().setLayout(null);

		setLocationByPlatform(true);
		setTitle("Visualize Fields");
	}

	public void paint(Graphics g) {
		super.paint(g);
		int i = 0;
		int start = 20;
		int y = 30;
		for (Item it : items) {
			System.out.println(i);
			if (it.getDomain().equals(domain)) {
				i++;

				if (i == 1) {
					g.drawOval(start, y, 100, 100);
					g.drawString(
							it.getName(), start + 20,
							y + 50);
				} else {

					start = start + 120;
					g.drawOval(start, y, 100, 100);
					g.drawString(
							it.getName(), start + 20,
							y + 50);
					if (i == 4) {
						i = 0;
						start = 20;
						y = y + 120;
					}
				}
			}
		}
		// g.drawOval(20, 30, 100, 100);
		// g.drawString("sddsfsd", 40, 80);
		//
		// g.drawOval(140, 30, 100, 100);
		// g.drawString("sddsfsd", 40, 80);
		//
		// g.drawOval(260, 30, 100, 100);
		// g.drawString("sddsfsd", 40, 80);
		//
		// g.drawOval(380, 30, 100, 100);
		// Draws the line

		// draws filled circle
		// g.setColor(Color.red);
		// g.fillOval(0,0,this.getWidth(), this.getHeight());
	}

	public static void main(String args[]) {

		new VisualizeFields().setVisible(true);

	}

}
