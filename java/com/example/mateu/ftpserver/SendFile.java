package com.example.mateu.ftpserver;

/**
 * Created by mateu on 10.06.2016.
 */
import android.app.Activity;
import android.content.Context;
import android.support.v4.provider.DocumentFile;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SendFile extends Thread{
    private DataInputStream din;
    private DataOutputStream dout;
    private ServerSocket server;
    private Socket socket;

    public void run()
    {
        try {
            server = new ServerSocket(8081);
            socket = server.accept();// akceptacja połączenia z klientem
            din=new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String filename;
        try {
            filename = din.readUTF();

            File f=new File(filename);
            if(!f.exists())
            {
                dout.writeUTF("File Not Found");
                return;
            }
            else
            {
                dout.writeUTF("READY");
                FileInputStream fin=new FileInputStream(f);
                int ch;
                do
                {
                    ch=fin.read();
                    dout.writeUTF(String.valueOf(ch));
                }
                while(ch!=-1);
                fin.close();
                dout.writeUTF("File Receive Successfully");
            }
            server.close();
            socket.close();
            din.close();
            dout.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SendFile() {
        super("SendFile");
        start();
    }
}
