package com.elka.nn.mail.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class MailReader {

	private File emlFile;
	private Properties props;
	private Session mailSession;
	private InputStream source;
	private MimeMessage message; 
	
//	private boolean textIsHtml = false;
	
	public MailReader(String mailPath) throws FileNotFoundException, MessagingException {
		emlFile = new File(mailPath);
		props = System.getProperties();
		mailSession = Session.getDefaultInstance(props);
		source = new FileInputStream(emlFile);
		message = new MimeMessage(mailSession, source);
	}
	
	public String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
//            textIsHtml = p.isMimeType("text/html");
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    } 
	
	public MimeMessage getMessage() {
		return message;
	}
	
	public int isImageIn(String str) {
		return str.contains("<img src") ? 1 : 0;
	}
	
	public int isLinkIn(String str) {
		return str.contains("<a href") ? 1 : 0;
	}
	
	
	/**
	 * @param args
	 * @throws MessagingException 
	 * @throws IOException 
	 */
		
	public static void main(String[] args) throws MessagingException, IOException {
		// TODO Auto-generated method stub
//		String path = "D:\\SPAM_EML\\email6.eml";
		String path = "/home/lukasz/Pulpit/Buty.eml";
		MailReader MR = new MailReader(path);
		String ret = MR.getText(MR.message);
		System.out.println(ret);
		System.out.println(ret.contains("<img src"));
		System.out.println(MR.isLinkIn(ret));
	}

}
