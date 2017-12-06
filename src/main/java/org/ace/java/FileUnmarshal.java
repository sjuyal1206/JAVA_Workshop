package org.ace.java;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class FileUnmarshal {

	public static void main(String[] args) {
		try{
			File file = new File("D://temp//schoolrecords.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Student customer = (Student) jaxbUnmarshaller.unmarshal(file);
			System.out.println(customer);
			
		}
		catch(JAXBException e) {
			e.printStackTrace();
		}
		
	}
	
}
