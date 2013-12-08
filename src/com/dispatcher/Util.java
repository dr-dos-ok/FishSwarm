package com.dispatcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static String path = "";
	public static String deaultField = "Math";
	public static int maxNumberOfField = 10;

	public static void loadSetting() {
		File file = new File("C:\\Setting.txt");
		StringBuilder contents = new StringBuilder();
		BufferedReader reader = null;
		if (!file.exists()) {
			return;
		}

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}
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

		System.out.println(contents.toString().split(
				System.getProperty("line.separator")).length);
		if (contents.toString().split(System.getProperty("line.separator")).length == 3) {
			String[] values = contents.toString().split(
					System.getProperty("line.separator"));
			path = values[0];
			deaultField = values[1];
			maxNumberOfField = new Integer(values[2]);
		}

	}

	public static void saveSetting(String path, String field, String max) {
		File file = new File("C:\\Setting.txt");
		FileWriter fstream;
		String data = path + System.getProperty("line.separator") + field
				+ System.getProperty("line.separator") + max;

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(data);
			// Close the output stream
			out.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
	public static List<String> allFiles(String s) {
		List<String> allFileName = new ArrayList<String>();
		File root = new File(s);
		System.out.println(root.getAbsolutePath());
		File[] list = root.listFiles();

		for (File f : list) {
			if (f.isDirectory()) {
				allFiles(f.getAbsolutePath());
				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
				allFileName.add(f.getName().substring(0,
						f.getName().lastIndexOf('.')));
				System.out.println("File:" + f.getAbsoluteFile());
			}
		}
		return allFileName;
	}

}
