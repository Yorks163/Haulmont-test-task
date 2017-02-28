package com.haulmont.testtask.entity;

/**
 * Created by Антон on 28.02.2017.
 */
public class Order {

    private String description;
    private float clientID;
    private int dataOfCreation;
    private int dataOfComplrtion;
    private double price;
    private enum StatusDescription {PLANNED, MADE, ADOPTED_BY_THE_CLIENT}
    private StatusDescription statusDescription;

    public Order(String newDescription, float newClientID, int newDataOfCreation, int newDataOfComplrtionm, double newPrice){
        this.description = newDescription;
        this.clientID = newClientID;
        this.dataOfCreation = newDataOfCreation;
        this.dataOfComplrtion = newDataOfComplrtionm;
        this.price = newPrice;
    }

    public String getDescription(){
        return this.description;
    }

    public float getClientID(){
        return this.clientID;
    }

    public int getDataOfCreation(){
        return this.dataOfCreation;
    }

    public int getDataOfComplrtion(){
        return this.dataOfComplrtion;
    }

    public double getPrice(){
        return  this.price;
    }

    public String getStatusDescription(){
        if (this.statusDescription == StatusDescription.PLANNED) return "PLANNED";
        else if (this.statusDescription == StatusDescription.MADE) return "MADE";
        else return "ADOPTED_BY_THE_CLIENT";
    }


    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void setClientID(float newClientID) {
        this.clientID = newClientID;
    }

    public void setDataOfCreation(int newDataOfCreation) {
        this.dataOfCreation = newDataOfCreation;
    }

    public void setDataOfComplrtion(int newDataOfComplrtion) {
        this.dataOfComplrtion = newDataOfComplrtion;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void setStatusDescription(StatusDescription newStatusDescription){
        this.statusDescription = newStatusDescription;
    }



    }


