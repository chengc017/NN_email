package com.elka.nn.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FilterTxt extends FileFilter {

	@Override
	public boolean accept(File pathname) {
		String name = pathname.getName().toLowerCase();
		return name.endsWith(".txt") || pathname.isDirectory();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
