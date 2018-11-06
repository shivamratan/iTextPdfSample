package com.ratanapps.epdf_sample.model;

import java.util.ArrayList;

public class InvoiceBody
{
    private ArrayList<BodyItem> bodyItemsList = new ArrayList<>();
    public InvoiceBody(ArrayList<BodyItem> bodyItemsList)
    {
        this.bodyItemsList = bodyItemsList;
    }

    public ArrayList<BodyItem> getBodyItemsList() {
        return bodyItemsList;
    }

    public void setBodyItemsList(ArrayList<BodyItem> bodyItemsList) {
        this.bodyItemsList = bodyItemsList;
    }

    public static class BodyItem
    {
        String Description;
        String Taxed;
        double Amount;

        public BodyItem() {
            Description = " ";
            Taxed = " ";
            Amount = 0.0;
        }

        public BodyItem(String description, String taxed, double amount) {
            Description = description;
            Taxed = taxed;
            Amount = amount;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getTaxed() {
            return Taxed;
        }

        public void setTaxed(String taxed) {
            Taxed = taxed;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }
    }
}
