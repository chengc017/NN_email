package com.elka.nn.mail.analyzer;

import java.io.File;

public class DirUtils {

	File actualRoot;
	String osname;
	
	public DirUtils(File rootDir) {
		actualRoot = rootDir;
		osname = System.getProperty("os.name");
	}
	
	private boolean createSPAMFolder() {
		String rootPath = actualRoot.getAbsolutePath();
		String newPath = rootPath;
		if (osname.startsWith("Windows")) {
			newPath = rootPath + "\\SPAM";
		} else if (osname.startsWith("Linux")){
			newPath = rootPath + "/SPAM";
		}
		boolean success = new File(newPath).mkdir();
		return success;
	}
	
	private boolean createGOODFolder() {
		String rootPath = actualRoot.getAbsolutePath();
		String newPath = rootPath;
		if (osname.startsWith("Windows")) {
			newPath = rootPath + "\\Pozadane";
		} else if (osname.startsWith("Linux")) {
			newPath = rootPath + "/Pozadane";
		}
		boolean success = new File(newPath).mkdir();
		return success;
	}
	
	private boolean checkIfSPAMFolderExists() {
		String rootPath = actualRoot.getAbsolutePath();
		String newPath = rootPath;
		if (osname.startsWith("Windows")) {
			newPath = rootPath + "\\SPAM";
		} else if (osname.startsWith("Linux")) {
			newPath = rootPath + "/SPAM";
		}
		File SPAMdir = new File(newPath);
		if (SPAMdir.exists())
			return true;
		else
			return false;
	}
	
	private boolean checkIfGOODFolderExists() {
		String rootPath = actualRoot.getAbsolutePath();
		String newPath = rootPath;
		if (osname.startsWith("Windows")) {
			newPath = rootPath + "\\Pozadane";
		} else if (osname.startsWith("Linux")) {
			newPath = rootPath + "/Pozadane";
		}
		File SPAMdir = new File(newPath);
		if (SPAMdir.exists())
			return true;
		else
			return false;
	}
	
	public boolean moveFileBecauseOfScore(File mail, double score) {
		String rootPath = actualRoot.getAbsolutePath();
		String SPAMDirPath = rootPath;
		String GOODDirPath = rootPath;
		String filename = mail.getName();
		if (osname.startsWith("Windows")) {
			SPAMDirPath = rootPath + "\\SPAM" + "\\" + filename;
			GOODDirPath = rootPath + "\\Pozadane" + "\\" + filename;
		} else if (osname.startsWith("Linux")) {
			SPAMDirPath = rootPath + "/SPAM" + "/" + filename;
			GOODDirPath = rootPath + "/Pozadane" + "/" + filename;
		}
		boolean good = false;
		if (checkIfSPAMFolderExists() && checkIfGOODFolderExists()) {
			if (score >= 0.51) {
				good = mail.renameTo(new File(SPAMDirPath));
			} else {
				good = mail.renameTo(new File(GOODDirPath));
			}
		}
		return good;
	}
	
	public boolean checkFolderExisting() {
		if (checkIfGOODFolderExists() && checkIfSPAMFolderExists()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void createFolders() {
		createSPAMFolder();
		createGOODFolder();
	}
	
	
	public static void main(String[] args) {
		File root = new File("C:\\Users\\Lukasz\\Desktop");
		DirUtils DU = new DirUtils(root);
//		if (!DU.checkIfSPAMFolderExists()) {
//			boolean suc = DU.createSPAMFolder();
//		}
//		if (!DU.checkIfGOODFolderExists()) {
//			boolean goodsuc = DU.createGOODFolder();
//		}
		if (DU.checkFolderExisting() == false) {
			DU.createFolders();
		}
		File a = new File("C:\\Users\\Lukasz\\Desktop\\A.eml");
		System.out.println(a.getName());
		if (a.exists()) {
			boolean b = DU.moveFileBecauseOfScore(a, 0.52);
			System.out.println(b);
		} else {
			System.out.println("dupa");
		}
	}

}
