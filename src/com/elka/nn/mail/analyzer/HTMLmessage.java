package com.elka.nn.mail.analyzer;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

public class HTMLmessage {

	private MailReader mail;
	private Message p;
	private boolean isTextHTML = false;
	
	public HTMLmessage(MailReader mess) {
		mail = mess;
//		p = mail.getMessage();
	}
	
	public MailReader getMail() {
		return mail;
	}
	
	public void isHTML() throws MessagingException {
		Part p = mail.getMessage();
		isTextHTML = p.isMimeType("text/html");
	}
	
	public int getIntOfBoolean() {
		return isTextHTML ? 1 : 0;
	}
	
	public boolean analyzeParts(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            isTextHTML = p.isMimeType("text/html");
            if (isTextHTML) {
            	return true;
            }
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        analyzeParts(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    //analyzeParts(bp);
                	isTextHTML = true;
                	return isTextHTML;
                } else {
                    analyzeParts(bp);
                }
            }
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                analyzeParts(mp.getBodyPart(i));
            }
        }
        return isTextHTML;
    } 
	
	/**
	 * @param args
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		//String path = "D:\\SPAM_EML\\email2.eml";
		String path = "/home/lukasz/Pulpit/Buty.eml";
		MailReader MR = new MailReader(path);
		HTMLmessage Hmess = new HTMLmessage(MR);
		//Hmess.isHTML();
		Hmess.analyzeParts(Hmess.mail.getMessage());
		System.out.println(Hmess.getIntOfBoolean());
	}

}
