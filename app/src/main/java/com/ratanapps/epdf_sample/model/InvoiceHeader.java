package com.ratanapps.epdf_sample.model;

public class InvoiceHeader
{
    private String companyName;
    private String companySlogan;
    private String faxNumber;
    private String invoiceDate;
    private String invoiceId;
    private Address companyAddress;
    private String customerId;

    public InvoiceHeader(String companyName, String companySlogan, Address companyAddress, String faxNumber, String invoiceDate, String invoiceId, String customerId) {
        this.companyName = companyName;
        this.companySlogan = companySlogan;
        this.companyAddress = companyAddress;
        this.faxNumber = faxNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceId = invoiceId;
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySlogan() {
        return companySlogan;
    }

    public void setCompanySlogan(String companySlogan) {
        this.companySlogan = companySlogan;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Address companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public static class Address
    {
        String companyAddress;
        String cityName;
        String cityZipCode;
        String phoneNumber;

        public Address(String companyAddress, String cityName, String cityZipCode, String phoneNumber) {
            this.companyAddress = companyAddress;
            this.cityName = cityName;
            this.cityZipCode = cityZipCode;
            this.phoneNumber = phoneNumber;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityZipCode() {
            return cityZipCode;
        }

        public void setCityZipCode(String cityZipCode) {
            this.cityZipCode = cityZipCode;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

}
