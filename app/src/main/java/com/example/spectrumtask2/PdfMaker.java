package com.example.spectrumtask2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class PdfMaker extends AppCompatActivity {
    protected EditText name, regd, dept, dob,address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_maker);
        Objects.requireNonNull(getSupportActionBar()).hide();
        name = findViewById(R.id.namePdf);
        regd = findViewById(R.id.regdPdf);
        dept = findViewById(R.id.deptPdf);
        dob = findViewById(R.id.dobPdf);
        address=findViewById(R.id.addressPdf);
        phone=findViewById(R.id.phonePdf);
    }

    public void createPdf(View view){

//        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);
        String names=name.getText().toString();
        String regds=regd.getText().toString();
        String depts=dept.getText().toString();
        String dobs=dob.getText().toString();
        String addresss=address.getText().toString();
        String phones=phone.getText().toString();


        try {
//            String pdfPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/aProject/";
//
//            File dir = new File(pdfPath);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File file=new File(pdfPath,names.replace(" ","") + "s_id.pdf");
//            Log.i("fineName",file.toString());
//            //FileOutputStream fOut = new FileOutputStream(file);
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getExternalFilesDir("/pdf");
            File file = new File(directory, names.replace(" ","")+"_something" + ".pdf");
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            pdfDocument.setDefaultPageSize(PageSize.A6);
            document.setMargins(0, 0, 0, 0);

            @SuppressLint("UseCompatLoadingForDrawables") Drawable d = getDrawable(R.drawable.cet);
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();

            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image image = new Image(imageData);

            document.add(image);

            Paragraph id = new Paragraph("ID CARD").setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
            Paragraph clg = new Paragraph("College of Engineering and Technology\nBhubaneswar").setFontSize(12).setTextAlignment(TextAlignment.CENTER);

            float[] width = {100f, 100f};
            Table table = new Table(width);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addCell(new Cell().add(new Paragraph("Name")));
            table.addCell(new Cell().add(new Paragraph(names)));

            table.addCell(new Cell().add(new Paragraph("Regd. Number")));
            table.addCell(new Cell().add(new Paragraph(regds)));

            table.addCell(new Cell().add(new Paragraph("Department")));
            table.addCell(new Cell().add(new Paragraph(depts)));

            table.addCell(new Cell().add(new Paragraph("Date of Birth")));
            table.addCell(new Cell().add(new Paragraph(dobs)));

            table.addCell(new Cell().add(new Paragraph("Address")));
            table.addCell(new Cell().add(new Paragraph(addresss)));

            table.addCell(new Cell().add(new Paragraph("Contact Number")));
            table.addCell(new Cell().add(new Paragraph(phones)));

            BarcodeQRCode qrCode = new BarcodeQRCode(names + "\n" + regds + "\n" + depts + "\n" + dobs + "\n" + addresss + "\n" + phones);
            PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument);
            Image qrCodeImage = new Image(qrCodeObject).setHorizontalAlignment(HorizontalAlignment.CENTER).setWidth(80);


            document.add(id);
            document.add(clg);
            document.add(table);
            document.add(qrCodeImage);

            document.close();
            Toast.makeText(this, "Pdf successfully Created", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"pdf Created in"+file,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e("showerror",Log.getStackTraceString(e));
        }
    }

//    public void checkPermission(String permission, int requestCode) {
//        if (ContextCompat.checkSelfPermission(PdfMaker.this, permission) == PackageManager.PERMISSION_DENIED) {
//
//            // Requesting the permission
//            ActivityCompat.requestPermissions(PdfMaker.this, new String[]{permission}, requestCode);
//        }
//    }
}