package com.company;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import java.nio.ByteBuffer;
import java.util.Scanner;

public class Main {

    private static final int newPost=300;
    private static final int getAllPosts=301;
    private static final int getSpecificPost=302;
    private static final int getAllUserList=304;

    public static void main(String[] args) {

        Socket socket=null;
        InputStream inputStream=null;
        OutputStream outputStream=null;
        String option="-1";
        Scanner scanner=new Scanner(System.in);
        byte[] byteBuffer=new byte[4];
        byte[] stringBuffer;
        User user=new User();
        boolean exitLoop=false;
        int firstTime=0;

	//connect to sever
        try {
            socket=new Socket("127.0.0.1",3001);
            inputStream=socket.getInputStream();
            outputStream=socket.getOutputStream();

            System.out.println("please enter you choice\n" +
                    "0.to exit\n"+
                    "1.to login\n" +
                    "2.to singup\n");

            while (exitLoop==false){
                if (firstTime==0){
                    option=scanner.nextLine();
                    firstTime++;
                }

                switch (option){
                    case "0": {
                        socket.close();
                        exitLoop=true;
                        break;
                    }
                    case "1": {
                        sendOption(outputStream, option);
                        user.login(inputStream, outputStream, socket);
                        if (user.isActive() == true) {
                            exitLoop = true;
                        }
                        if (user.isActive() == false) {
                            System.out.println("please enter what you want to do\n" +
                                    "0.to exit\n" +
                                    "1.to login\n" +
                                    "2.to singup\n");
                            option = "-1";
                            option = scanner.nextLine();
                            while (option == "0" || option == "1" || option == "2") {
                                scanner.reset();
                                System.out.println("wrong choice please enter one of the choices from the instruction\n" +
                                        "0.to exit\n" +
                                        "1.to login\n" +
                                        "2.to singup\n");
                                option = scanner.nextLine();
                            }
                        }
                        break;
                    }
                    case "2":{
                        sendOption(outputStream,option);
                        user.singUp(inputStream,outputStream,socket);
                        if (user.isActive()==true){
                            exitLoop=true;
                        }
                        if (user.isActive()==false){
                            System.out.println("please enter what you want to do\n" +
                                    "0.to exit\n"+
                                    "1.to login\n" +
                                    "2.to singup\n");
                            option="-1";
                            option=scanner.nextLine();
                            while (option=="0"||option=="1"||option=="2"){
                                scanner.reset();
                                System.out.println("wrong choice please enter one of the choices from the instruction\n" +
                                        "0.to exit\n"+
                                        "1.to login\n" +
                                        "2.to singup\n");
                                option=scanner.nextLine();
                            }
                        }
                        break;
                    }
                    default:{
                        System.out.println("wrong choice please enter one of the choices from the instruction\n" +
                                "0.to exit\n"+
                                "1.to login\n" +
                                "2.to singup\n");
                        option=scanner.nextLine();
                    }
                }

            }
            System.out.println("please choose operation" +
                    "\n1.add post" +
                    "\n2.get all posts" +
                    "\n3.4.5.........");
            while (true) {
                option = scanner.nextLine();
                switch (option) {
                    case "1": {
                        user.sendPost(outputStream, inputStream);
                        break;
                    }
                    case "2":{

                    }
                }
            }
            //add post
            //get all posts
            //get specific user posts
            //delete one of your posts


        }catch (IOException e){

        }

    }
    static void sendOption(OutputStream outputStream,String option){
        byte[] stringBuffer;
        stringBuffer=new byte[option.length()];
        stringBuffer[0]=(byte) option.charAt(0);
        try {
            outputStream.write(stringBuffer);
        }catch (IOException e){
            e.printStackTrace();
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
                System.out.println("wrong input");
            }

        }
        return test1;
    }
}


