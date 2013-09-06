package com.elka.nn.mail.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DirUtils {

	File actualRoot;
	String osname;
	
	public DirUtils(File rootDir) {
		actualRoot = rootDir;
		osname = System.getProperty("os.name");
	}
	
	private boolean createSPAMFolder() {
		String homepath = System.getProperty("user.home");
		String newPath = homepath;
		if (osname.startsWith("Windows")) {
			newPath = homepath + "\\Wiadomosci SPAM";
		} else if (osname.startsWith("Linux")){
			newPath = homepath + "/Wiadomosci SPAM";
		}
		boolean success = new File(newPath).mkdir();
		return success;
	}
	
	private boolean createGOODFolder() {
		String homepath = System.getProperty("user.home");
		String newPath = homepath;
		if (osname.startsWith("Windows")) {
			newPath = homepath + "\\Wiadomosci Pozadane";
		} else if (osname.startsWith("Linux")) {
			newPath = homepath + "/Wiadomosci Pozadane";
		}
		boolean success = new File(newPath).mkdir();
		return success;
	}
	
	private boolean checkIfSPAMFolderExists() {
		String homepath = System.getProperty("user.home");
		String newPath = homepath;
		if (osname.startsWith("Windows")) {
			newPath = homepath + "\\Wiadomosci SPAM";
		} else if (osname.startsWith("Linux")) {
			newPath = homepath + "/Wiadomosci SPAM";
		}
		File SPAMdir = new File(newPath);
		if (SPAMdir.exists())
			return true;
		else
			return false;
	}
	
	private boolean checkIfGOODFolderExists() {
		String homepath = System.getProperty("user.home");
		String newPath = homepath;
		if (osname.startsWith("Windows")) {
			newPath = homepath + "\\Wiadomosci Pozadane";
		} else if (osname.startsWith("Linux")) {
			newPath = homepath + "/Wiadomosci Pozadane";
		}
		File SPAMdir = new File(newPath);
		if (SPAMdir.exists())
			return true;
		else
			return false;
	}
	
/*	public boolean moveFileBecauseOfScore(File mail, double score) {
		String rootPath = actualRoot.getAbsolutePath();
		String SPAMDirPath = rootPath;
		String GOODDirPath = rootPath;
		String filename = mail.getName();
		if (osname.startsWith("Windows")) {
			SPAMDirPath = rootPath + "\\SPAM" + "\\" + filename;
			GOODDirPath = rootPath + "\\Pozadane" + "\\" + filename;
			System.out.println("Sciezka spamu: " + SPAMDirPath);
			System.out.println("Sciezka pozadanego: " + GOODDirPath);
		} else if (osname.startsWith("Linux")) {
			SPAMDirPath = rootPath + "/SPAM" + "/" + filename;
			GOODDirPath = rootPath + "/Pozadane" + "/" + filename;
		}
		boolean good = false;
		if (checkIfSPAMFolderExists() && checkIfGOODFolderExists()) {
			if (score >= 0.51) {
				good = mail.renameTo(new File(SPAMDirPath));
				System.out.println("tu przenosze do spamu:" + good);
			} else {
				good = mail.renameTo(new File(GOODDirPath));
				System.out.println("tu przenosze do dobrej:" + good);
			}
		}
		return good;
	}*/
	
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
	
	public void copyFile(File from, double score) {
		InputStream inStream = null;
		OutputStream outStream = null;
	    	try{
	    		File afile = from;
	    		File bfile = null;
	    		
	    		String rootPath = actualRoot.getAbsolutePath();
	    		String SPAMDirPath = rootPath;
	    		String GOODDirPath = rootPath;
	    		String filename = afile.getName();
	    		
	    		if (osname.startsWith("Windows")) {
	    			String homepath = System.getProperty("user.home");
	    			SPAMDirPath = homepath + "\\Wiadomosci SPAM" + "\\" + filename;
	    			GOODDirPath = homepath + "\\Wiadomosci Pozadane" + "\\" + filename;
	    			//SPAMDirPath = rootPath + "\\SPAM" + "\\" + filename;
	    			//GOODDirPath = rootPath + "\\Pozadane" + "\\" + filename;
//	    			System.out.println("Sciezka spamu: " + SPAMDirPath);
//	    			System.out.println("Sciezka pozadanego: " + GOODDirPath);
	    		} else if (osname.startsWith("Linux")) {
	    			String homepath = System.getProperty("user.home");
	    			SPAMDirPath = homepath + "/Wiadomosci SPAM" + "/" + filename;
	    			GOODDirPath = homepath + "/Wiadomosci Pozadane" + "/" + filename;
	    		}
	    		
	    		if (checkIfSPAMFolderExists() && checkIfGOODFolderExists()) {
	    			if (score >= 0.51) {
	    				bfile = new File(SPAMDirPath);
	    			} else {
	    				bfile = new File(GOODDirPath);
	    			}
	    		}
	    		
	    	    inStream = new FileInputStream(afile);
	    	    outStream = new FileOutputStream(bfile);
	 
	    	    byte[] buffer = new byte[1024];
	 
	    	    int length;
	    	    //copy the file content in bytes 
	    	    while ((length = inStream.read(buffer)) > 0){
	    	    	outStream.write(buffer, 0, length);
	    	    }
	 
	    	    inStream.close();
	    	    outStream.close();
	 
	    	    //delete the original file
	    	    //afile.delete();
	 
	    	    System.out.println("File is copied successful!");
	 
	    	}catch(IOException e){
	    	    e.printStackTrace();
	    	}
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
		File a = new File("C:\\Users\\Lukasz\\Desktop\\A");
		if (a.exists()) {
//			boolean b = DU.moveFileBecauseOfScore(a, 0.52);
//			System.out.println(b);
			DU.copyFile(a, 0.52);
		} else {
			System.out.println("dupa");
		}
	}

}
