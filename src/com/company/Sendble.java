package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface Sendble {

    public void login(InputStream inputStream, OutputStream outputStream, Socket socket)throws IOException;

    public void singUp(InputStream inputStream, OutputStream outputStream, Socket socket)throws IOException;

    public void sendPost(OutputStream outputStream,InputStream inputStream)throws IOException;

    public void getAllI(InputStream inputStream, OutputStream outputStream)throws IOException;

    public void getSpecificUI(InputStream inputStream, OutputStream outputStream)throws IOException;

    public void  getAllUserList(OutputStream outputStream,InputStream inputStream)throws IOException;
}
