package com.haulmont.testtask.entity;

import java.sql.Date;

/**
 * Created by Антон on 28.02.2017.
 */
public class Order {

    private String description;
    private long clientID;
    private Date dataOfCreation;
    private Date dataOfCompletion;
    private double price;
    private StatusDescription statusDescription;

    private enum StatusDescription {Запланирован, Выполнен, Принят_клиентом}

    public Order(String newDescription, long newClientID, Date newDataOfCreation, Date newDataOfCompletionm, double newPrice){
        this.description = newDescription;
        this.clientID = newClientID;
        this.dataOfCreation = newDataOfCreation;
        this.dataOfCompletion = newDataOfCompletionm;
        this.price = newPrice;
    }

    public String getDescription(){
        return this.description;
    }

    public long getClientID(){
        return this.clientID;
    }

    public Date getDataOfCreation(){
        return this.dataOfCreation;
    }

    public Date getDataOfComplrtion(){
        return this.dataOfCompletion;
    }

    public double getPrice(){
        return  this.price;
    }

    public String getStatusDescription(){
        /*if (this.statusDescription == StatusDescription.Запланирован) return "Запланирован";
        else if (this.statusDescription == StatusDescription.Выполнен) return "Выполнен";
        else return "Принят клиентом";*/
        return this.statusDescription.toString();
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void setClientID(long newClientID) {
        this.clientID = newClientID;
    }

    public void setDataOfCreation(Date newDataOfCreation) {
        this.dataOfCreation = newDataOfCreation;
    }

    public void setDataOfComplrtion(Date newDataOfCompertion) {
        this.dataOfCompletion = newDataOfCompertion;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void setStatusDescription(String statusDescription) {
        try {
            this.statusDescription = StatusDescription.valueOf(statusDescription);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception: Некорректный статус заказа!");
        }
    }

}


