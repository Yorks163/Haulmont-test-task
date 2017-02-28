package com.haulmont.testtask;

import com.haulmont.testtask.entity.*;

/**
 * Created by Антон on 28.02.2017.
 */
public class Main {

    public static void main (String[] args){

        Client client = new Client("Ivan", "Ivanov", "Ivanovich", 890712345);
        System.out.print(client.getFirstName());
    }
}
