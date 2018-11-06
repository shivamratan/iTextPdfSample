package com.ratanapps.epdf_sample.model;

public class InvoiceSubject
{
    private String contactPersonName;
    private String companyName;
    private InvoiceHeader.Address toAddress;

    public InvoiceSubject(String contactPersonName, String companyName, InvoiceHeader.Address toAddress) {
        this.contactPersonName = contactPersonName;
        this.companyName = companyName;
        this.toAddress = toAddress;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public InvoiceHeader.Address getToAddress() {
        return toAddress;
    }

    public void setToAddress(InvoiceHeader.Address toAddress) {
        this.toAddress = toAddress;
    }
}
