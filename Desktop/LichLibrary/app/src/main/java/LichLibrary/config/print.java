/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LichLibrary.config;
import LichLibrary.config.cont;
import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 *
 * @author eater
 */
public class print {
    cont c = new cont();
    
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    
    private void addMetaData(Document document) {
        document.addTitle("Data Report");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, PerpusProto");
        document.addAuthor("Rajib Firdaus");
        document.addCreator("Admin");
    }
    
    private void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM dd, yyyy");
        String currentDateTime = dateFormat. format(currentDate);
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Laporan Data Perpustakaan PerpusProto", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + "; " + currentDateTime, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        // now add all this to the document
        document.add(preface);
    }
    
    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    private String convertMongoDate(String val){
    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    SimpleDateFormat outputFormat= new SimpleDateFormat("E, MMM dd, yyyy");
    try {
        String finalStr = outputFormat.format(inputFormat.parse(val));
//        System.out.println(finalStr);
        return finalStr;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "";
}
    
    //table to print
    private void addContentStaff(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Data Staff", catFont);
        anchor.setName("Data Staff");
        Paragraph catPart = new Paragraph(anchor);
        
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        
        catPart.add(paragraph);
        // Number of Colomn
        PdfPTable table = new PdfPTable(4);
//         Create Header of Table 
            PdfPCell hc;
            hc = new PdfPCell(new Phrase("Username",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Name",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Email",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Address",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            
        table.setHeaderRows(1);
        MongoCollection<org.bson.Document> staff = c.db.getCollection("user");
        org.bson.Document cek = new org.bson.Document();
        cek.put("level", "staf");
        
        FindIterable<org.bson.Document> show = staff.find(cek);
        show.forEach(v -> {
            if (v.isEmpty()) {
                paragraph.add(new Paragraph(
                "Data Tidak Ada",redFont));
            }else{
            table.addCell(new Phrase(" "+v.getString("username"),small));
            table.addCell(new Phrase(" "+v.getString("NamaLengkap"),small));
            table.addCell(new Phrase(" "+v.getString("email"),small));
            table.addCell(new Phrase(" "+v.getString("Alamat"),small));
            }
        });
        catPart.add(table);
        // now add all this to the document
        document.add(catPart);
    }
    
    private void addContentUsers(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Data Users", catFont);
        anchor.setName("Data Users");
        Paragraph catPart = new Paragraph(anchor);
        
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        
        catPart.add(paragraph);
        // Number of Colomn
        PdfPTable table = new PdfPTable(4);
//         Create Header of Table 
            PdfPCell hc;
            hc = new PdfPCell(new Phrase("Username",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Name",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Email",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Address",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            
        table.setHeaderRows(1);
        MongoCollection<org.bson.Document> users = c.db.getCollection("user");
        FindIterable<org.bson.Document> show = users.find(Filters.eq("level", "user"));
        show.forEach(v -> {
            if (v.isEmpty()) {
                paragraph.add(new Paragraph(
                "Data Tidak Ada",redFont));
            }else{
            table.addCell(new Phrase(" "+v.getString("username"),small));
            table.addCell(new Phrase(" "+v.getString("NamaLengkap"),small));
            table.addCell(new Phrase(" "+v.getString("email"),small));
            table.addCell(new Phrase(" "+v.getString("Alamat"),small));
            }
        });
        catPart.add(table);
        // now add all this to the document
        document.add(catPart);
    }
    
    private void addContentKategori(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Data Kategori", catFont);
        anchor.setName("Data Kategori");
        Paragraph catPart = new Paragraph(anchor);
        
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        
        catPart.add(paragraph);
        // Number of Colomn
        PdfPTable table = new PdfPTable(2);
//         Create Header of Table 
            PdfPCell hc;
            hc = new PdfPCell(new Phrase("Category",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Total Book",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            
        table.setHeaderRows(1);
        MongoCollection<org.bson.Document> genre = c.db.getCollection("kategoribuku");
        MongoCollection<org.bson.Document> book = c.db.getCollection("buku");
        
        FindIterable<org.bson.Document> show = genre.find();
        show.forEach(v -> {
            if (v.isEmpty()) {
                paragraph.add(new Paragraph(
                "Data Tidak Ada",redFont));
            }else{
                table.addCell(new Phrase(" "+v.getString("NamaKategori"),small));
              long count = book.countDocuments(Filters.eq("kategori", v.getString("NamaKategori")));
              if(count!=0){
                  table.addCell(new Phrase(" "+count,small));
              }else{
                  table.addCell(new Phrase(" None",small));
              }
            }
        });
        catPart.add(table);
        // now add all this to the document
        document.add(catPart);
    }
    
    private void addContentBook(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Data Book", catFont);
        anchor.setName("Data Book");
        Paragraph catPart = new Paragraph(anchor);
        
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        
        catPart.add(paragraph);
        // Number of Colomn
        PdfPTable table = new PdfPTable(7);
//         Create Header of Table 
            PdfPCell hc;
            hc = new PdfPCell(new Phrase("Tittle",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Writer",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Publisher",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Year",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Category",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Amount",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Stock",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            
        table.setHeaderRows(1);
        MongoCollection<org.bson.Document> book = c.db.getCollection("buku");
        MongoCollection<org.bson.Document> book_d = c.db.getCollection("kategoribuku_relasi");
        FindIterable<org.bson.Document> show = book.find();
        show.forEach(v -> {
            if (v.isEmpty()) {
                paragraph.add(new Paragraph(
                "Data Tidak Ada",redFont));
            }else{
            table.addCell(new Phrase(" "+v.getString("judul"),small));
            table.addCell(new Phrase(" "+v.getString("penulis"),small));
            table.addCell(new Phrase(" "+v.getString("penerbit"),small));
            table.addCell(new Phrase(" "+v.getInteger("tahun"),small));
            table.addCell(new Phrase(" "+v.getString("kategori"),small));
            table.addCell(new Phrase(" "+v.getInteger("stok"),small));
            FindIterable<org.bson.Document> show2 = book_d.find(Filters.eq("_id_buku", v.getObjectId("_id")));
              show2.forEach(va -> {
                  table.addCell(new Phrase(" "+va.getInteger("stok"),small));
              });
            
            }
        });
        catPart.add(table);
        // now add all this to the document
        document.add(catPart);
    }
    
    private void addContentPinjam(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Data Peminjaman", catFont);
        anchor.setName("Data Peminjaman");
        Paragraph catPart = new Paragraph(anchor);
        
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        
        catPart.add(paragraph);
        // Number of Colomn
        PdfPTable table = new PdfPTable(4);
//         Create Header of Table 
            PdfPCell hc;
            hc = new PdfPCell(new Phrase("Username",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Tittle",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Date Borrow",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Deadline",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            
        table.setHeaderRows(1);
       MongoCollection<org.bson.Document> pijm = c.db.getCollection("peminjaman");
        MongoCollection<org.bson.Document> book = c.db.getCollection("buku");
        
        FindIterable<org.bson.Document> show = pijm.find(Filters.eq("status", "Dipinjam"));
        show.forEach(v -> {
            if (v.isEmpty()) {
                paragraph.add(new Paragraph(
                "Data Tidak Ada",redFont));
            }else{
            table.addCell(new Phrase(" "+v.getString("username"),small));
            
            FindIterable<org.bson.Document> show2 = book.find(Filters.eq("_id", v.getObjectId("_id_buku")));
            show2.forEach(val2-> {
                table.addCell(new Phrase(" "+val2.getString("judul"),small));
            });
            table.addCell(new Phrase(" "+convertMongoDate(v.getDate("tgl_pinjam").toString()),small));
            table.addCell(new Phrase(" "+convertMongoDate(v.getDate("tgl_kembali").toString()),small));
                       
            }
        });
        catPart.add(table);
        // now add all this to the document
        document.add(catPart);
    }
    
    private void addContentKembali(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Data Pengembalian", catFont);
        anchor.setName("Data Pengembalian");
        Paragraph catPart = new Paragraph(anchor);
        
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        
        catPart.add(paragraph);
        // Number of Colomn
        PdfPTable table = new PdfPTable(4);
//         Create Header of Table 
            PdfPCell hc;
            hc = new PdfPCell(new Phrase("Username",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Tittle",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Fines",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            hc = new PdfPCell(new Phrase("Date Return",smallBold));
            hc.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hc);
            
        table.setHeaderRows(1);
        MongoCollection<org.bson.Document> book = c.db.getCollection("buku");
        MongoCollection<org.bson.Document> kem = c.db.getCollection("peminjaman");
        org.bson.Document cek = new org.bson.Document("$or",Arrays.asList(
                new org.bson.Document("status", "Dikembalikan"),new org.bson.Document("status", "Hilang")
        ));
        FindIterable<org.bson.Document> show = kem.find(cek);
        show.forEach(v -> {
            if (v.isEmpty()) {
                paragraph.add(new Paragraph(
                "Data Tidak Ada",redFont));
            }else{
            table.addCell(new Phrase(" "+v.getString("username"),small));
            
            FindIterable<org.bson.Document> show2 = book.find(Filters.eq("_id", v.getObjectId("_id_buku")));
            show2.forEach(val2-> {
                table.addCell(new Phrase(" "+val2.getString("judul"),small));
            });
            int x = v.getInteger("denda");
                if (x>0) {
                    table.addCell(new Phrase(" Rp "+x,small));
                }else{
                    table.addCell(new Phrase(" No Fines",small));
                }
            table.addCell(new Phrase(" "+convertMongoDate(v.getDate("tgl_dikembalikan").toString()),small));
                       
            }
        });
        catPart.add(table);
        // now add all this to the document
        document.add(catPart);
    }
    
    public void print(String file, String choose){
        try {
                
            Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                addMetaData(document);
                addTitlePage(document);
                
                if (choose.equals("staff")) {
                    addContentStaff(document);
            }if (choose.equals("users")) {
                    addContentUsers(document);
            }if (choose.equals("book")) {
                    addContentBook(document);
            }if (choose.equals("kategori")) {
                    addContentKategori(document);
            }if (choose.equals("pinjam")) {
                    addContentPinjam(document);
            }if (choose.equals("kembali")) {
                    addContentKembali(document);
            }if (choose.equals("s&s")) {
                addContentStaff(document);
                addContentUsers(document);
            }if (choose.equals("b&k")) {
                addContentBook(document);
                addContentKategori(document);
            }if (choose.equals("p&k")) {
                addContentPinjam(document);
                addContentKembali(document);
            }
            if (choose.equals("all")) {
                addContentStaff(document);
                addContentUsers(document);
                addContentBook(document);
                addContentKategori(document);
                addContentPinjam(document);
                addContentKembali(document);
            }
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}