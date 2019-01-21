package com.company;

import java.util.Scanner;

public class Item {
    private String itemName;
    private int phone;
    private int price;

    public Item(){
        this.itemName=null;
        this.phone=0;
        this.price=0;
    }

    public Item(String itemName, int phone ,int price) {
        this.itemName = itemName;
        this.phone = phone;
        this.price=price;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public void printItem(){
        System.out.println("\titem name: "+this.itemName);
        System.out.println("\titem Price:"+this.price);
        System.out.println("\tphone number"+ this.phone);
    }
}
