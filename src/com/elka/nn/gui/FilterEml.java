package com.elka.nn.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FilterEml extends FileFilter {

	@Override
	public boolean accept(File f) {
		String name = f.getName().toLowerCase();
		return name.endsWith(".eml") || f.isDirectory();
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
