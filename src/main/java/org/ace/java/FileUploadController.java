package org.ace.java;

import org.ace.java.dao.StudentDAOImpl;
import org.ace.java.model.School;

import org.ace.java.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Controller
public class FileUploadController {

	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "D://temp//";
    
    @Autowired
    private StudentDAOImpl impl;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file, HttpSession session) {

        if (file.isEmpty()) {
            ModelAndView model = new ModelAndView("uploadStatus");
            model.addObject("message", "File is empty");
            return model;
        }

        try {

            byte[] bytes = file.getBytes();
            File f1 = new File(UPLOADED_FOLDER);
            File[] files = f1.listFiles();
            for(int i=0; i < files.length; i++) {
            	files[i].delete();
            }
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            File file1 = new File(UPLOADED_FOLDER+file.getOriginalFilename());
			JAXBContext jaxbContext = JAXBContext.newInstance(School.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			School school = (School) jaxbUnmarshaller.unmarshal(file1);
		
            for(Student s1 : school.getStudents()) {
            	session.setAttribute("studentdata", s1);
            	impl.save(s1);
            }
            
            


        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
        ModelAndView model = new ModelAndView("uploadStatus");
        model.addObject("message", file.getOriginalFilename()+" is uploaded successfully");
        return model;
    }

    

}