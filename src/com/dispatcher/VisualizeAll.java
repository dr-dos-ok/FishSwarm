package com.dispatcher;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

public class VisualizeAll extends JFrame {

	List<File> allField = null;
	List<Item> items = null;

	VisualizeAll() {
	}

	VisualizeAll(List<File> allField) {
		this.allField = allField;
		items = new ArrayList<Item>();
		for (File file : allField) {
			System.out.println("VisualizeAll " + file.getAbsolutePath());
			StringBuilder contents = new StringBuilder();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String text = null;

				// repeat until all lines is read
				while ((text = reader.readLine()) != null) {
					contents.append(text).append(
							System.getProperty("line.separator"));
				}

				Item item = new Item(file.getName().substring(0,
						file.getName().lastIndexOf('.')), contents.toString()
						.split(System.getProperty("line.separator"))[0],
						contents.toString().split(
								System.getProperty("line.separator"))[1]);
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

		Collections.sort(items, new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				return o1.getDomain().compareTo(o2.getDomain());
			}
		});

		calcLocation();
		setLocationByPlatform(true);

		setBounds(100, 100, 500, 500);
		getContentPane().setLayout(null);

		setLocationByPlatform(true);
		setTitle("Visualize All");
	}

	public void paint(Graphics g) {
		super.paint(g);
		String domain = "";
		int start = 40;
		int max = 0;
		boolean line = false;
		int x = 20;
		int valueX = 0;
		int count = 0;
		System.out.println(items.size());
		domain = items.get(0).getDomain();
		for (Item item : items) {
			if (domain.equals(item.getDomain())) {
				domain = item.getDomain();
				System.out
						.println("if (domain.equals() || domain.equals(item.getDomain())) ");
				if (max < item.getX()) {
					max = item.getX();
				}
				valueX += 20;
				x += 20;
				if (count == 0) {
					g.drawString(item.getDomain(), (start + valueX + 60), 80);
					count++;
				}
			} else {
				System.out.println("else" + max);
				start += 50 + max;
				domain = item.getDomain();
				x = 20;
				count = 0;
				line = true;
			}

			System.out.println("draw : " + item.getDomain() + "   "
					+ (item.getX() + start) + "   " + (item.getY()));
			System.out
					.println("draw 2: " + item.getDomain() + "   "
							+ ((valueX + 1) * 10 + start) + "   "
							+ (item.getY() + 100));
			g.drawOval(((valueX + 1) * 2 + start), (item.getY()) + 100, 50, 50);
			g.drawString(item.getName(), ((valueX + 1) * 2 + start),
					item.getY() + 130);
			if (line) {
				System.out.println("line" + (start + max));
				g.drawLine((valueX + 1) * 2 + start - 2, 0, (valueX + 1) * 2
						+ start - 2, 500);
				line = false;
			}
			// g.drawString("sddsfsd", 40, 80);
		}

		// g.drawOval(20, 30, 100, 100);
		// g.drawString("sddsfsd", 40, 80);

	}

	public void calcLocation() {
		for (Item item : items) {
			List<String> keyWord = new ArrayList<String>();
			File file = new File(Util.path + "\\Fields\\" + item.getDomain());
			for (int i = 0; i < file.listFiles().length; i++) {
				keyWord.add(file.listFiles()[i].getName().substring(0,
						file.listFiles()[i].getName().lastIndexOf(".")));
			}
			item.setX(calc(item.getItems(),
					keyWord.subList(0, keyWord.size() / 2)));
			item.setY(calc(item.getItems(),
					keyWord.subList(keyWord.size() / 2, keyWord.size())));

		}

	}

	public int calc(String data, List<String> keyWord) {
		Collections.sort(keyWord);
		System.out.println("keyWord " + keyWord.size());
		System.out.println(data + "   " + keyWord.toString());
		List<String> all = Arrays.asList(data.split(","));
		String binary = "";
		for (int i = 0; i < keyWord.size(); i++) {
			System.out.println(keyWord.get(i));
			if (all.contains(keyWord.get(i))) {
				binary = 1 + binary;
			} else {
				binary = 0 + binary;
			}
		}

		System.out.println("binary " + binary);
		System.out.println("decimal " + Integer.parseInt(binary, 2));
		return Integer.parseInt(binary, 2);
	}

	public static void main(String args[]) {

		new VisualizeAll().setVisible(true);

	}

}
