package com.New.LHS20.Service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.New.LHS20.Dao.DoctorPrescriptionRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.RegistrationForm;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Header;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import io.jsonwebtoken.io.IOException;

@Service
public class PdfService {

    
    @Autowired
    DoctorPrescriptionRepository prescrepo;
    @Autowired
    RegistrationRepository  regrepo;

    public void export(HttpServletResponse response, Doctor_Prescription doctorpresc)  throws IOException, DocumentException, java.io.IOException   {
        List<Doctor_Prescription> doctorprescList=  prescrepo.findByPatient(doctorpresc.getPatient());
        Doctor_Prescription doctorpresc1 =doctorprescList.get(0);
        Patient   pat =doctorpresc1.getPatient();
        RegistrationForm regform =regrepo.findByUsername(pat.getUsername());
        Doctor doctor=doctorpresc1.getDoctor();
//        MyCartList cartList=ordersEntityList.getCartList();
         Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, response.getOutputStream());
//    Image image= Image.getInstance("omg.jpg");
//        image.scaleAbsolute(120,40);
//        image.setAlignment(50);
        document.open();
              Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE);
        fontTitle.setSize(18);
        fontTitle.setColor(19, 124, 50);
        Header header = new Header("Doctor_Prescription", "0");
        Paragraph paragraph = new Paragraph("LIFELINE HEALTHCARE SYSTEM");
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        // fontParagraph.setColor(206,33,33);
    Paragraph paragraph2 = new Paragraph("Doctor_Prescription", fontTitle);
        paragraph2.setSpacingAfter(1f);
    paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph paragraph4 = new Paragraph("Patient Name :"+regform.getFirstName()+" " +regform.getLastName());
        paragraph4.setAlignment(Paragraph.ALIGN_RIGHT);
Paragraph paragraph5 = new Paragraph("Doctor Name :"+doctor.getFirstName() );
Paragraph paragraph6 = new Paragraph("Specility :"+doctor.getSpeciality());
               // Paragraph paragraph7 = new Paragraph(addressEntity.getState()+","+addressEntity.getPincode());
        Paragraph paragraph3 = new Paragraph("Presc_Id", fontParagraph);
    Table table = new Table(2, 6);
        table.setAlignment(5);
        table.setBorder(2);
        table.setPadding(3);
        Cell cell = new Cell("Id");
    table.addCell(cell);
         table.addCell(String.valueOf(doctorpresc1.getPatient()));
         table.addCell(paragraph3);
        table.addCell(String.valueOf(doctorpresc1.getPatient()));
    table.addCell("Slot");

    document.add(paragraph);
    document.add(paragraph2);
//    document.add(image);
    document.add(paragraph4);
    document.add(paragraph5);
    document.add(paragraph6);
//    document.add(paragraph7);
    document.add(table);

    document.close();

    }
}