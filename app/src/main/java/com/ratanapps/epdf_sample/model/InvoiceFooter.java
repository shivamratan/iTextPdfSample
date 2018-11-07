package com.ratanapps.epdf_sample.model;

public class InvoiceFooter
{
    private String queryString;
    private String queryName;
    private String queryPhone;
    private String queryMail;
    private String thankuMessage;

    public InvoiceFooter(String queryString, String queryName, String queryPhone, String queryMail, String thankuMessage) {
        this.queryString = queryString;
        this.queryName = queryName;
        this.queryPhone = queryPhone;
        this.queryMail = queryMail;
        this.thankuMessage = thankuMessage;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQueryPhone() {
        return queryPhone;
    }

    public void setQueryPhone(String queryPhone) {
        this.queryPhone = queryPhone;
    }

    public String getQueryMail() {
        return queryMail;
    }

    public void setQueryMail(String queryMail) {
        this.queryMail = queryMail;
    }

    public String getThankuMessage() {
        return thankuMessage;
    }

    public void setThankuMessage(String thankuMessage) {
        this.thankuMessage = thankuMessage;
    }
}
