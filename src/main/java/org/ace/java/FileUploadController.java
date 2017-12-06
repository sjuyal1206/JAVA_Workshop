package org.ace.java;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

@Controller
public class FileUploadController {

	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "D://temp//";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            ModelAndView model = new ModelAndView("uploadStatus");
            model.addObject("message", "File is empty");
            return model;
        }

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }

        ModelAndView model = new ModelAndView("uploadStatus");
        model.addObject("message", file.getOriginalFilename()+" is uploaded successfully");
        return model;
    }

    

}