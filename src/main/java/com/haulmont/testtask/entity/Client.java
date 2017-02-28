package com.haulmont.testtask.entity;

/**
 * Created by Антон on 28.02.2017.
 */
public class Client {

    private String firstName;
    private String surname;
    private String patronymic;
    private double number;
    private float id;

    public Client(String newFirstName, String newSurname, String newPatronymic, double newNumber){
        this.firstName = newFirstName;
        this.surname = newSurname;
        this.patronymic = newPatronymic;
        this.number = newNumber;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getSurname(){
        return this.surname;
    }

    public String getPatronymic(){
        return this.patronymic;
    }

    public double getNumber(){
        return this.number;
    }

    public float getId(){
        return this.id;
    }

    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }

    public void setSurname(String newSurname){
        this.surname = newSurname;
    }

    public void setPatronymic(String newPatronymic){
        this.patronymic = newPatronymic;
    }

    public void setNumber(double newNumber){
        this.number = newNumber;
    }

    public void setId(float newId){
        this.id = newId;
    }
}
