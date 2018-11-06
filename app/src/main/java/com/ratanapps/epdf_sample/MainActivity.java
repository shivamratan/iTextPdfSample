package com.ratanapps.epdf_sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ratanapps.epdf_sample.activity.PdfBuilder;
import com.ratanapps.epdf_sample.communication.PdfGeneratedCallback;
import com.ratanapps.epdf_sample.model.InvoiceAdditive;
import com.ratanapps.epdf_sample.model.InvoiceBody;
import com.ratanapps.epdf_sample.model.InvoiceHeader;
import com.ratanapps.epdf_sample.model.InvoiceSubject;
import com.ratanapps.epdf_sample.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PdfGeneratedCallback {

    private final String TAG = "PDF_TAG";
    private Button btn_showPdf;
    private Button btn_genPdf;
    private File pdffile;
    private int permissionRequestCode= 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_showPdf = (Button)findViewById(R.id.button_showPdf);
        btn_genPdf = (Button)findViewById(R.id.button_genPdf);

         pdffile = new File(Util.getAppPath(this)+"transaction.pdf");
        if(pdffile.exists())
        {
            btn_genPdf.setText("Re-Generate PDF");
            btn_showPdf.setVisibility(View.VISIBLE);
        }

    }

    private void createPDF() {

        btn_showPdf.setVisibility(View.GONE);
        InvoiceHeader.Address companyAddress = new InvoiceHeader.Address("Down Street , Flamingo Hotel Warp Tower ","Chicago","0935","+1876543223");
        InvoiceHeader.Address billToAddress = new InvoiceHeader.Address("Street 345, Kuaa Market Near 36 China Square","Briston","0935","+1876543223");


        ArrayList<InvoiceBody.BodyItem> bodyItemList = new ArrayList<>();
        bodyItemList.add(new InvoiceBody.BodyItem("Main Fee","X",2300.00));
        bodyItemList.add(new InvoiceBody.BodyItem("Service Fee","X",456.00));
        bodyItemList.add(new InvoiceBody.BodyItem("Labour Fee","X",353.00));
        bodyItemList.add(new InvoiceBody.BodyItem("Additional Fee","X",4.00));
        bodyItemList.add(new InvoiceBody.BodyItem("Additional1 Fee","X",3.00));

        ArrayList<String> tncList = new ArrayList<>();
        tncList.add("Total Payment Due on 30 days");
        tncList.add("Please Invoice Number in your invoice Check");
        tncList.add("Please Invoice Number in your invoice Check");

        InvoiceAdditive.AdditionalRate additionalRate = new InvoiceAdditive.AdditionalRate("$ 344.00","$ 333.00","6.25 %","$ 21.56","-","$ 371.56");


        InvoiceHeader invoiceHeader = new InvoiceHeader("XYZ Company","Simplicity is Perfection",companyAddress,"73993398983","23 Nov, 2018","INV-3434GCR","CUST009");
        InvoiceSubject invoiceSubject = new InvoiceSubject("Kevin","ABC Company",billToAddress);
        InvoiceBody invoiceBody = new InvoiceBody(bodyItemList);
        InvoiceAdditive invoiceAdditive = new InvoiceAdditive(tncList,additionalRate);

        PdfBuilder pdfBuilder = PdfBuilder.getBuilder(this)
                                .header(invoiceHeader)
                                .subject(invoiceSubject)
                                .body(invoiceBody)
                                .additives(invoiceAdditive)
                                .build();
    }


    private boolean checkFilePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},permissionRequestCode);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

      if(requestCode == permissionRequestCode) {
          if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              createPDF();
          } else {
              Log.e(TAG, "WRITE_PERMISSION_DENIED");
          }
      }

    }

    @Override
    public void onPdfGenerated() {
        btn_showPdf.setVisibility(View.VISIBLE);
        btn_genPdf.setText("Re-Generate PDF");
    }


    public void genPdf(View view){
        Log.e(TAG,"REAching to Gen PDF");
       if(checkFilePermission())
        createPDF();
    }

    public void showDocs(View view) {

        if(pdffile.exists())
        {
            try {
                Util.openFile(this,pdffile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
