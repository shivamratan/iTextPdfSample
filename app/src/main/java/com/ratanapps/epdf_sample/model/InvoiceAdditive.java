package com.ratanapps.epdf_sample.model;

import java.util.ArrayList;

public class InvoiceAdditive
{
    private ArrayList<String> termsnConditionList = new ArrayList<>();
    private AdditionalRate additionalRate = null;

    public InvoiceAdditive(ArrayList<String> termsnConditionList, AdditionalRate additionalRate) {
        this.termsnConditionList = termsnConditionList;
        this.additionalRate = additionalRate;
    }

    public ArrayList<String> getTermsnConditionList() {
        return termsnConditionList;
    }

    public void setTermsnConditionList(ArrayList<String> termsnConditionList) {
        this.termsnConditionList = termsnConditionList;
    }

    public AdditionalRate getAdditionalRate() {
        return additionalRate;
    }

    public void setAdditionalRate(AdditionalRate additionalRate) {
        this.additionalRate = additionalRate;
    }

    public static class AdditionalRate
    {
        private String subTotal;
        private String taxable;
        private String taxRate;
        private String taxDue;
        private String otherCharges;
        private String totalDues;

        public AdditionalRate(String subTotal, String taxable, String taxRate, String taxDue, String otherCharges, String totalDues) {
            this.subTotal = subTotal;
            this.taxable = taxable;
            this.taxRate = taxRate;
            this.taxDue = taxDue;
            this.otherCharges = otherCharges;
            this.totalDues = totalDues;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getTaxable() {
            return taxable;
        }

        public void setTaxable(String taxable) {
            this.taxable = taxable;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getTaxDue() {
            return taxDue;
        }

        public void setTaxDue(String taxDue) {
            this.taxDue = taxDue;
        }

        public String getOtherCharges() {
            return otherCharges;
        }

        public void setOtherCharges(String otherCharges) {
            this.otherCharges = otherCharges;
        }

        public String getTotalDues() {
            return totalDues;
        }

        public void setTotalDues(String totalDues) {
            this.totalDues = totalDues;
        }
    }
}
