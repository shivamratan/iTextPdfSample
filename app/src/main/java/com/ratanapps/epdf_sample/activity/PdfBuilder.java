package com.ratanapps.epdf_sample.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ratanapps.epdf_sample.communication.PdfGeneratedCallback;
import com.ratanapps.epdf_sample.model.InvoiceAdditive;
import com.ratanapps.epdf_sample.model.InvoiceBody;
import com.ratanapps.epdf_sample.model.InvoiceFooter;
import com.ratanapps.epdf_sample.model.InvoiceHeader;
import com.ratanapps.epdf_sample.model.InvoiceSubject;
import com.ratanapps.epdf_sample.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PdfBuilder {

    private InvoiceHeader invoiceHeader = null;
    private InvoiceSubject invoiceSubject = null;
    private InvoiceBody invoiceBody = null;
    private InvoiceAdditive invoiceAdditive = null;
    private InvoiceFooter invoiceFooter = null;
    private static Context context;
    private Document document = null;

    private PdfBuilder() {

    }

    public static PdfBuilder getBuilder(Context context) {
        //create pdf here
        PdfBuilder.context = context;
        return new PdfBuilder();
    }


    private void init()
    {
        File file = new File(Util.getAppPath(context)+"transaction.pdf");
        if(file.exists())
            file.delete();


      try {

          document = new Document();

          // Location to save
          PdfWriter.getInstance(document, new FileOutputStream(file));

          // Open to write
          document.open();

          // Document Settings
          document.setPageSize(PageSize.A4);
          document.addCreationDate();
          document.addAuthor("Venu Assappa");
          document.addCreator("Venu Assappa");
      }catch (Exception e)
      {
          e.printStackTrace();
      }

    }


    private void addHeadernSubject(InvoiceHeader header, InvoiceSubject subject) {

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);


        PdfPCell leftHeaderCell = new PdfPCell();
        leftHeaderCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell rightHeaderCell = new PdfPCell();
        rightHeaderCell.setBorder(Rectangle.NO_BORDER);

        Font companyHeadingFont = new Font(Font.FontFamily.COURIER,24.0f,Font.NORMAL,BaseColor.BLACK);
        Font invoiceHeadingFont = new Font(Font.FontFamily.COURIER,26.0f,Font.BOLD,BaseColor.BLUE);



        //creating Header
        if(header!=null)
        {
            leftHeaderCell.addElement(new Phrase(header.getCompanyName(),companyHeadingFont));
            leftHeaderCell.addElement(new Phrase(header.getCompanySlogan()));

            leftHeaderCell.addElement(new Paragraph("\n"));
            leftHeaderCell.addElement(new Paragraph(""));

            InvoiceHeader.Address companyAddress = header.getCompanyAddress();
            leftHeaderCell.addElement(new Paragraph(companyAddress.getCompanyAddress()));
            leftHeaderCell.addElement(new Paragraph(companyAddress.getCityName()+"  "+companyAddress.getCityZipCode()));
            leftHeaderCell.addElement(new Paragraph("Phone: "+companyAddress.getPhoneNumber()));
            leftHeaderCell.addElement(new Paragraph("Fax: "+header.getFaxNumber()));

            Paragraph invoicePara = new Paragraph("INVOICE",invoiceHeadingFont);
            invoicePara.setAlignment(Element.ALIGN_RIGHT);

            rightHeaderCell.addElement(invoicePara);
            rightHeaderCell.addElement(new Paragraph("\n"));

            Paragraph invoiceDatePara = new Paragraph("Date  "+header.getInvoiceDate());
            invoiceDatePara.setAlignment(Element.ALIGN_RIGHT);
            rightHeaderCell.addElement(invoiceDatePara);

            Paragraph invoiceIdPara = new Paragraph("Invoice "+header.getInvoiceId());
            invoiceIdPara.setAlignment(Element.ALIGN_RIGHT);
            rightHeaderCell.addElement(invoiceIdPara);

            Paragraph customerIdPara = new Paragraph("Customer ID "+header.getCustomerId());
            customerIdPara.setAlignment(Element.ALIGN_RIGHT);
            rightHeaderCell.addElement(customerIdPara);


        }

        //creating Subject
        if(subject!=null)
        {
            leftHeaderCell.addElement(new Paragraph("\n"));
            leftHeaderCell.addElement(new Paragraph(""));

            Font billtoFont = new Font();
            billtoFont.setStyle(Font.BOLD);
            Paragraph billToPara = new Paragraph("BILL TO",billtoFont);
            leftHeaderCell.addElement(billToPara);

            leftHeaderCell.addElement(new Phrase(subject.getContactPersonName()));
            leftHeaderCell.addElement(new Phrase(subject.getCompanyName()));
            leftHeaderCell.addElement(new Phrase(subject.getToAddress().getCompanyAddress()));
            leftHeaderCell.addElement(new Phrase(subject.getToAddress().getCityName()+", "+subject.getToAddress().getCityZipCode()));
            leftHeaderCell.addElement(new Phrase(subject.getToAddress().getPhoneNumber()));
        }


        headerTable.addCell(leftHeaderCell);
        headerTable.addCell(rightHeaderCell);

        try {
            document.add(headerTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    private void addBody(InvoiceBody body) {
        //table body

      if(body!=null) {
          float headingTopBottompadding = 5.0f;
          float[] columnWidths = {20, 5, 5};
          PdfPTable table = new PdfPTable(columnWidths);
          table.setSpacingBefore(20.0f);
          table.setWidthPercentage(100);
          table.getDefaultCell().setUseAscender(true);
          table.getDefaultCell().setUseDescender(true);
        //  table.getDefaultCell().setBackgroundColor(new BaseColor(59, 72, 139));

          Font headingFont = new Font();
          headingFont.setStyle(Font.BOLD);
          headingFont.setColor(BaseColor.WHITE);
          headingFont.setSize(13.0f);
          PdfPCell descriptionHeadingCell = new PdfPCell(new Phrase("DESCRIPTION", headingFont));
          descriptionHeadingCell.setBackgroundColor(new BaseColor(59, 72, 139));
          descriptionHeadingCell.setPaddingTop(headingTopBottompadding);
          descriptionHeadingCell.setPaddingBottom(headingTopBottompadding);
          descriptionHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);

          PdfPCell taxedHeadingCell = new PdfPCell(new Phrase("TAXED", headingFont));
          taxedHeadingCell.setBackgroundColor(new BaseColor(59, 72, 139));
          taxedHeadingCell.setPaddingTop(headingTopBottompadding);
          taxedHeadingCell.setPaddingBottom(headingTopBottompadding);
          taxedHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);

          PdfPCell amountHeadingCell = new PdfPCell(new Phrase("AMOUNT", headingFont));
          amountHeadingCell.setBackgroundColor(new BaseColor(59, 72, 139));
          amountHeadingCell.setPaddingTop(headingTopBottompadding);
          amountHeadingCell.setPaddingBottom(headingTopBottompadding);
          amountHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);

          table.addCell(descriptionHeadingCell);
          table.addCell(taxedHeadingCell);
          table.addCell(amountHeadingCell);

          table.setHeaderRows(1);

          ArrayList<InvoiceBody.BodyItem> bodyItemList = body.getBodyItemsList();
          //adding padding cells--------------
          bodyItemList.add(new InvoiceBody.BodyItem());
          bodyItemList.add(new InvoiceBody.BodyItem());
          bodyItemList.add(new InvoiceBody.BodyItem());
          bodyItemList.add(new InvoiceBody.BodyItem());
          // -----------------------

          for (int counter = 0; counter < bodyItemList.size(); counter++) {
              String description = bodyItemList.get(counter).getDescription();
              String taxed = bodyItemList.get(counter).getTaxed();
              double amount = bodyItemList.get(counter).getAmount();
              Paragraph descriptionPara = new Paragraph(description);
              Paragraph taxedPara = new Paragraph(taxed);
              Paragraph amountPara = null;

              if (description.trim().isEmpty() && taxed.trim().isEmpty())
                  amountPara = new Paragraph(" ");
              else
                  amountPara = new Paragraph(Double.toString(amount));

                /*serialPara.setAlignment(Element.ALIGN_LEFT);
                keyPara.setAlignment(Element.ALIGN_CENTER);
                valuePara.setAlignment(Element.ALIGN_CENTER);*/

              PdfPCell cell1 = new PdfPCell(descriptionPara);
              PdfPCell cell2 = new PdfPCell(taxedPara);
              PdfPCell cell3 = new PdfPCell(amountPara);
              cell1.setPaddingLeft(10.0f);
              cell3.setPaddingRight(10.0f);

              cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
              cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);

              if (counter != bodyItemList.size() - 1) {
                  cell1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
                  cell2.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
                  cell3.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
              } else {
                  cell1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
                  cell2.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
                  cell3.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
              }

              if (counter % 2 != 0) {
                  cell1.setBackgroundColor(GrayColor.WHITE);
                  cell2.setBackgroundColor(GrayColor.WHITE);
                  cell3.setBackgroundColor(GrayColor.WHITE);
              } else {
                  cell1.setBackgroundColor(new BaseColor(0, 0, 1, 0.1f));
                  cell2.setBackgroundColor(new BaseColor(0, 0, 1, 0.1f));
                  cell3.setBackgroundColor(new BaseColor(0, 0, 1, 0.1f));
              }

              table.addCell(cell1);
              table.addCell(cell2);
              table.addCell(cell3);
          }

          try {
              document.add(table);
          } catch (DocumentException e) {
              e.printStackTrace();
          }
      }

    }

    private void addAdditives(InvoiceAdditive additive) {
        // GST , Terms & Condition Part
        if(additive!=null){
            float[] columnWidths = {20, 10};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.setSpacingAfter(30.0f);

            PdfPCell leftCell = new PdfPCell();
            leftCell.setBorder(Rectangle.NO_BORDER);


            //termsnCondition Cell in Table
            PdfPTable tncTable = new PdfPTable(1);
            tncTable.setWidthPercentage(90.0f);
            tncTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            leftCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            tncTable.setSpacingBefore(10.0f);
            Font headingFont = new Font();
            headingFont.setStyle(Font.BOLD);
            headingFont.setColor(BaseColor.WHITE);
            headingFont.setSize(13.0f);
            PdfPCell descriptionHeadingCell = new PdfPCell(new Phrase("OTHER COMMENTS", headingFont));
            descriptionHeadingCell.setBackgroundColor(new BaseColor(59, 72, 139));
            descriptionHeadingCell.setPaddingTop(5.0f);
            descriptionHeadingCell.setPaddingBottom(5.0f);
            descriptionHeadingCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            tncTable.addCell(descriptionHeadingCell);
            tncTable.setHeaderRows(1);

            ArrayList<String> tncItem = additive.getTermsnConditionList();
            for(int i=0;i<tncItem.size();i++)
            {
                PdfPCell cell = new PdfPCell(new Phrase((i+1)+". "+tncItem.get(i)));
                cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT);
                cell.setPaddingLeft(5.0f);
                cell.setPaddingRight(5.0f);
                tncTable.addCell(cell);
            }


            //space addition
            for(int i=0;i<2;i++)
            {
                PdfPCell cell = new PdfPCell(new Phrase(" "));
               if(i!=1)
                cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT);
               else
                   cell.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.BOTTOM);
                tncTable.addCell(cell);
            }

            leftCell.addElement(tncTable);


            PdfPCell rightCell = new PdfPCell();
            rightCell.setBorder(Rectangle.NO_BORDER);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            //Additional Rate Description
            float addRateTableColumnwidth[] = {5,5};
            PdfPTable addRateTable = new PdfPTable(addRateTableColumnwidth);
            addRateTable.setWidthPercentage(100);

            addRateTable.addCell(getBorderlessCell("SubTotal",Element.ALIGN_RIGHT));
            addRateTable.addCell(getBorderlessCell(additive.getAdditionalRate().getSubTotal(),Element.ALIGN_RIGHT));

            addRateTable.addCell(getBorderlessCell("Taxable ",Element.ALIGN_RIGHT));
            addRateTable.addCell(getBorderlessCell(additive.getAdditionalRate().getTaxable(),Element.ALIGN_RIGHT));

            addRateTable.addCell(getBorderlessCell("Tax Rate ",Element.ALIGN_RIGHT));
            addRateTable.addCell(getBorderlessCell(additive.getAdditionalRate().getTaxRate(),Element.ALIGN_RIGHT));

            addRateTable.addCell(getBorderlessCell("Tax Due ",Element.ALIGN_RIGHT));
            addRateTable.addCell(getBorderlessCell(additive.getAdditionalRate().getTaxDue(),Element.ALIGN_RIGHT));

            PdfPCell otherChargesCell = getBorderlessCell("Other ",Element.ALIGN_RIGHT);
            otherChargesCell.setPaddingBottom(4.0f);
            addRateTable.addCell(otherChargesCell);

            PdfPCell otherChargesValueCell = getBorderlessCell(additive.getAdditionalRate().getOtherCharges(),Element.ALIGN_RIGHT);
            otherChargesValueCell.setPaddingBottom(4.0f);
            addRateTable.addCell(otherChargesValueCell);


            Font boldfont = new Font();
            boldfont.setStyle(Font.BOLD);
            boldfont.setSize(14.0f);
            PdfPCell totalSumCellLable = new PdfPCell(new Paragraph("Total Dues",boldfont));
            totalSumCellLable.setPaddingTop(3.0f);
            totalSumCellLable.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            totalSumCellLable.setBorder(Rectangle.TOP);

            PdfPCell totalSumCellValue = new PdfPCell(new Paragraph(additive.getAdditionalRate().getTotalDues(),boldfont));
            totalSumCellValue.setPaddingTop(3.0f);
            totalSumCellValue.setBorder(Rectangle.TOP);
            totalSumCellValue.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);

            addRateTable.addCell(totalSumCellLable);
            addRateTable.addCell(totalSumCellValue);



            if(invoiceHeader!=null)
            {
                PdfPCell instruction = new PdfPCell(new Phrase("Make All Checks Payable to "+invoiceHeader.getCompanyName()));
                instruction.setColspan(2);
                instruction.setPaddingTop(20.0f);
                instruction.setHorizontalAlignment(Element.ALIGN_CENTER);
                instruction.setBorder(Rectangle.NO_BORDER);
                instruction.setPaddingBottom(5.0f);

                addRateTable.addCell(instruction);
            }

            rightCell.addElement(addRateTable);


            table.addCell(leftCell);
            table.addCell(rightCell);

            try {
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFooter(InvoiceFooter footer) {
        // Footer Part
        if(footer!=null){
            Paragraph queryParaLable = new Paragraph(footer.getQueryString());
            queryParaLable.setAlignment(Element.ALIGN_CENTER);

            Paragraph queryContact = new Paragraph(footer.getQueryName()+", "+footer.getQueryPhone()+", "+footer.getQueryMail());
            queryContact.setAlignment(Element.ALIGN_CENTER);

            Font boldfont = new Font();
            boldfont.setSize(14.0f);
            boldfont.setStyle(Font.BOLDITALIC);
            Paragraph thankuPara = new Paragraph(footer.getThankuMessage(),boldfont);
            thankuPara.setPaddingTop(35.0f);
            thankuPara.setAlignment(Element.ALIGN_CENTER);
          try{
            document.add(queryParaLable);
            document.add(queryContact);
            document.add(new Paragraph("\n"));
            document.add(thankuPara);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        }
    }


    @SuppressLint("StaticFieldLeak")
    public PdfBuilder build()
    {
        new AsyncTask<Void,Void,Void>(){

            ProgressDialog progressDialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Generating PDF Please wait...");
                progressDialog.show();

            }

            @Override
            protected Void doInBackground(Void... voids) {

                init();
                addHeadernSubject(invoiceHeader,invoiceSubject);
                addBody(invoiceBody);
                addAdditives(invoiceAdditive);
                addFooter(invoiceFooter);
                document.close();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.cancel();
                PdfGeneratedCallback pdfGeneratedCallback = (PdfGeneratedCallback)context;
                pdfGeneratedCallback.onPdfGenerated();
                Toast.makeText(context,"Pdf Successfully Generated",Toast.LENGTH_LONG).show();

            }
        }.execute();
        return this;
    }

    public PdfBuilder header(InvoiceHeader header) {
            this.invoiceHeader = header;
            return this;
    }

    public PdfBuilder subject(InvoiceSubject subject) {
            this.invoiceSubject = subject;
            return this;
    }


    public PdfBuilder body(InvoiceBody body) {
            this.invoiceBody = body;
            return this;
    }

    public PdfBuilder additives(InvoiceAdditive additive) {
            this.invoiceAdditive = additive;
            return this;
    }

    public PdfBuilder footer(InvoiceFooter footer) {
            this.invoiceFooter = footer;
            return this;
    }


    private PdfPCell getBorderlessCell(String elementName,int alignment)
    {
        PdfPCell cell = new PdfPCell(new Phrase(elementName));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

}
