package com.example.mateu.ftpserver;

/**
 * Created by mateu on 10.06.2016.
 */
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileList extends Thread{
    private DataInputStream din;
    private DataOutputStream dout;
    private ServerSocket server;
    private Socket socket;

    public FileList() {
        super("FIleList");
        start();
    }

    public void run() {
        try {
            server = new ServerSocket(8081);
            socket = server.accept();// akceptacja połączenia z klientem
            din=new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String filename = null;

        try {
            filename = din.readUTF();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File dir = new File(filename);
        String sciezka = null;
        sciezka=dir.getAbsolutePath().toString();
        if (dir.exists()) {
            try {
                dout.writeUTF(sciezka);
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();

                    for (File file : files) {
                        dout.writeUTF(file.getName());
                    }
                }
                dout.writeUTF("STOP");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                dout.writeUTF("STOP");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            server.close();
            socket.close();
            din.close();
            dout.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
