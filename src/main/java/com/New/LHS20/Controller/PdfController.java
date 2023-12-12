package com.New.LHS20.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Service.PdfService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api")
public class PdfController {


    @Autowired
    PdfService pdfservice;



    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response, @RequestBody Doctor_Prescription doctorpresc) throws IOException, DocumentException, java.io.IOException   {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

            pdfservice.export(response,doctorpresc);


    }
}