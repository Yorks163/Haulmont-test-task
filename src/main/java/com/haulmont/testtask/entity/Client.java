package com.haulmont.testtask.entity;


public class Client {

    private String firstName;
    private String surname;
    private String patronymic;
    private String number;
    private long id;

    public Client(String newFirstName, String newSurname, String newPatronymic, String newNumber){
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

    public String getNumber(){
        return this.number;
    }

    public long getId(){
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

    public void setNumber(String newNumber){
        this.number = newNumber;
    }

    public void setId(long newId){
        this.id = newId;
    }
}
