package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class User implements Sendble {

    //finals
    private final int newPost=300;
    private final int getAllPosts=301;
    private final int getSpecificPost=302;
    private final int getAllUserList=304;
    //


    private boolean active=false;
    private String username;
    private String password;
    ///////////////////////////////////////////////////////
                    //constructor//
    public User() {
        this.username=null;
        this.password=null;
    }
    /////////////////////////////////////////////////
                //getters and setters//

    public boolean isActive() {
        return active;
    }

    private void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private void sendInfo(OutputStream outputStream)throws IOException {
        int actuallyread;
        Scanner scanner = new Scanner(System.in);
        byte[] byteBuffer = new byte[4];
        byte stringBuffer[];
        //client user input user name
        System.out.println("please enter username");
        this.setUsername(scanner.nextLine());
        scanner.reset();
        //client user name password
        System.out.println("please enter your password");
        this.setPassword(scanner.nextLine());
        scanner.reset();
        //----------------------------------------//



            //send to server length of username string

            ByteBuffer.wrap(byteBuffer).putInt(this.getUsername().length());
            stringBuffer = this.getUsername().getBytes();
            outputStream.write(byteBuffer);

            //send to server String of username

            stringBuffer = this.getUsername().getBytes();
            outputStream.write(stringBuffer);

            //send hashcode
            ByteBuffer.wrap(byteBuffer).putInt(hashCode());
            outputStream.write(byteBuffer);

            //get activation token for the user



    }
    //////////////////////////////////////////////////
                //interface implementation//


    @Override
    public void login(InputStream inputStream,OutputStream outputStream,Socket socket){
            try {
                byte[] byteBuffer=new byte[4];
                int actualyRead;
                //sending user info to the server
                //sending user info to the server
                sendInfo(outputStream);


                //get activation token
                actualyRead=inputStream.read(byteBuffer);
                if (actualyRead!=4){
                    System.out.println("something went wrong");
                    return;
                }
                if (ByteBuffer.wrap(byteBuffer).getInt()==1){
                    setActive(true);
                    System.out.println("the user is connected");
                }else if(ByteBuffer.wrap(byteBuffer).getInt()!=1){
                    if (ByteBuffer.wrap(byteBuffer).getInt()==0){
                        System.out.println("wrong username or password re enter");
                    }else {
                        System.out.println("omg you are an hacker!!!!");
                        inputStream.close();
                        outputStream.close();
                        socket.close();
                    }
                }


            }catch (IOException e){
                e.printStackTrace();
            }












    }

    @Override
    public void singUp(InputStream inputStream, OutputStream outputStream,Socket socket) {
        int actuallyread;
        byte[] byteBuffer = new byte[4];
        try {
            this.sendInfo(outputStream);

            //if  not taken user login and get token
            actuallyread = inputStream.read(byteBuffer);
            if (actuallyread != 4) {
                System.out.println("some thing went wrong exit server");
                inputStream.close();
                outputStream.close();
                socket.close();
            }
            if (ByteBuffer.wrap(byteBuffer).getInt() == 1) {
                setActive(true);
                System.out.println("account -" + this.getUsername() + " is created you can do actions now");
            } else if (ByteBuffer.wrap(byteBuffer).getInt() == 0) {
                setActive(false);
                System.out.println("user name is taken try again");
            } else {
                System.out.println("what the hell u are an H4x0r,disconnecting .....");
                inputStream.close();
                outputStream.close();
                socket.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void sendPost(OutputStream outputStream,InputStream inputStream) {
        int actualyRead;
        if (this.isActive()){
            //
            byte[] byteBuffer=new byte[4];
            byte[] stringBuffer;
            //
            String itemName;
            int phoneNum;
            int price;
            Scanner scanner=new Scanner(System.in);
            System.out.println("please enter items name: ");
            System.out.println("\n");
            itemName=scanner.nextLine();
            scanner.reset();
            System.out.println("please enter phone number of item owner: ");
            phoneNum=intValidaator();
            System.out.println("please enter item's price: ");
            price=intValidaator();
            Item item=new Item(itemName,phoneNum,price);

            try {

                //send option number
                ByteBuffer.wrap(byteBuffer).putInt(newPost);
                outputStream.write(byteBuffer);
                //send user name length
                ByteBuffer.wrap(byteBuffer).putInt(this.username.length());
                outputStream.write(byteBuffer);
                //send username bytes
                stringBuffer=new byte[this.username.length()];
                stringBuffer=this.username.getBytes();
                outputStream.write(stringBuffer);
                //send item name letter number
                ByteBuffer.wrap(byteBuffer).putInt(item.getItemName().length());
                outputStream.write(byteBuffer);
                //send item name
                stringBuffer=new byte[item.getItemName().length()];
                stringBuffer=item.getItemName().getBytes();
                outputStream.write(stringBuffer);
                //send phone number
                ByteBuffer.wrap(byteBuffer).putInt(phoneNum);
                outputStream.write(byteBuffer);
                //send price
                ByteBuffer.wrap(byteBuffer).putInt(phoneNum);
                outputStream.write(byteBuffer);
                //get message if the post was successful or not
                actualyRead=inputStream.read(byteBuffer);
                if (actualyRead!=4){
                    System.out.println("something went wrong");
                    return;
                }
                if (ByteBuffer.wrap(byteBuffer).getInt()==1){
                    System.out.println("post is successful");
                }
                else{
                    System.out.println("something went wrong");
                    return;
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    //get all items in the server
    public void getAllI(InputStream inputStream, OutputStream outputStream)throws IOException {
        byte[] byteBuffer=new byte[4];
        byte[] stringBuffer;
        int actualyRead;
        Item item=new Item();
        if (this.isActive()){

            //send request
            ByteBuffer.wrap(byteBuffer).putInt(getAllPosts);
            outputStream.write(byteBuffer);
            //get number of items
            actualyRead=inputStream.read(byteBuffer);
            if (actualyRead!=4){
                System.out.println("something went wrong");
                return;
            }
            //get the items and print them
            for (int i=0;i<ByteBuffer.wrap(byteBuffer).getInt();i++){
                //getting string length
                actualyRead=inputStream.read(byteBuffer);
                if(actualyRead!=4){
                    System.out.println("something went wrong");
                    return;
                }
                //getting string name
                stringBuffer=new byte[ByteBuffer.wrap(byteBuffer).getInt()];
                actualyRead=inputStream.read(stringBuffer);
                if (actualyRead!=stringBuffer.length){
                    System.out.println("something went wrong");
                    return;
                }
                item.setItemName(new String(stringBuffer));


                //getting price
                actualyRead=inputStream.read(byteBuffer);
                if (actualyRead!=4){
                    System.out.println("something went wrong");
                }
                item.setPrice(ByteBuffer.wrap(byteBuffer).getInt());
                //getting phone
                actualyRead=inputStream.read(byteBuffer);
                if (actualyRead!=4){
                    System.out.println("something went wrong");
                    return;
                }
                item.setPhone(ByteBuffer.wrap(byteBuffer).getInt());
                item.printItem();
            }

        }
    }
    //get all items from specific user
    @Override
    public void getSpecificUI(InputStream inputStream, OutputStream outputStream)throws IOException {
            byte[] byteBuffer=new byte[4];
            byte[] strtingBuffer;
            String searchUser;
            int actuallyRead;
            Scanner scanner=new Scanner(System.in);
            Item item=new Item();
        if (this.isActive()){
            //send request
                ByteBuffer.wrap(byteBuffer).getInt(getSpecificPost);
                outputStream.write(byteBuffer);
            //send the username you want to see his items
            System.out.println("please enter the specific user name you want his posts");
            searchUser=new String(scanner.nextLine());
            scanner.reset();
            //send the user length of the user that you are searching
            ByteBuffer.wrap(byteBuffer).getInt(searchUser.length());
            outputStream.write(byteBuffer);
            //send the searchUser
            strtingBuffer=searchUser.getBytes();
            outputStream.write(strtingBuffer);
            //get the item number amount
            actuallyRead=inputStream.read(byteBuffer);
            if (actuallyRead!=4){
                System.out.println("something went wrong");
                return;
            }

            for(int i=0;i<ByteBuffer.wrap(byteBuffer).getInt();i++){
                //print all user items
                actuallyRead=inputStream.read(strtingBuffer);
                if (actuallyRead!=strtingBuffer.length){
                    System.out.println("something went wrong");
                    return;
                }
                item.setItemName(new String(strtingBuffer));


                actuallyRead=inputStream.read(byteBuffer);
                if (actuallyRead!=4){
                    System.out.println("something went wrong");
                    return;
                }
                item.setPrice(ByteBuffer.wrap(byteBuffer).getInt());

                actuallyRead=inputStream.read(byteBuffer);
                if (actuallyRead!=4){
                    System.out.println("something went wrong");
                    return;
                }
                item.setPhone(ByteBuffer.wrap(byteBuffer).getInt());

                item.printItem();
            }



        }
    }

    @Override
    //getting all users
    public void getAllUserList(OutputStream outputStream,InputStream inputStream) throws IOException {
        byte[] byteBuffer=new byte[4];
        byte[] stringBuffer;
        //send request
        ByteBuffer.wrap(byteBuffer).putInt(getAllUserList);
        outputStream.write(byteBuffer);

        int numberOfUsers;
        int actualyRead;
        //getting number amount of all users
        actualyRead=inputStream.read(byteBuffer);
        if (actualyRead!=4){
            System.out.println("something went wrong");
            return;
        }
        numberOfUsers=ByteBuffer.wrap(byteBuffer).getInt();

        //get the users and print them
        for (int i=0;i<numberOfUsers;i++){
            actualyRead=inputStream.read(byteBuffer);
            if (actualyRead!=4){
                return;
            }
            stringBuffer=new byte[ByteBuffer.wrap(byteBuffer).getInt()];
            actualyRead=inputStream.read(stringBuffer);
            if (actualyRead!=stringBuffer.length){
                System.out.println("something went wrong");
                return;
            }
            System.out.println("\t"+new String(stringBuffer));

        }
    }
    public static int intValidaator(){
        Scanner scanner=new Scanner(System.in);
        Integer test1=1;
        String test=new String();

        while (true) {
            try {
                test = scanner.nextLine();
                test1=test1.parseInt(test);
                System.out.println(test);
                break;
            }
            catch (Exception e){
                System.out.println("re enter the input-(numbers only)");
            }

        }
        return test1;
    }
    /////////////////////////////////////////////////////////////////////
    //hashCode//

    private int passHash(){
        int hash=7;
        for (int i=0;i<password.length();i++){
            hash*=(int)this.password.charAt(i);
        }
        return hash*331;
    }
    @Override
    public int hashCode() {
        int hash=13;
        for (int i=0;i<this.username.length();i++){
            hash*=(int)this.username.charAt(i);
        }
        hash=(hash*131)^passHash();
        return hash;
    }
}
//////////////////////////////////////////////////////////////////////////////

