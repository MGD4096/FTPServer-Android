package com.example.mateu.ftpserver;

/**
 * Created by mateu on 10.06.2016.
 */
import android.app.Activity;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.support.v4.provider.DocumentFile;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Recive extends Thread{
    private DataInputStream din;
    private DataOutputStream dout;
    private ServerSocket server;
    private Socket socket;

    public Recive() {
        super("Recive");
        start();
    }

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

            if(filename.compareTo("File not found")==0)
            {
                return;
            }
            File f=new File(filename);
            String option;

            if(f.exists())
            {
                dout.writeUTF("File Already Exists");
                option=din.readUTF();
            }
            else
            {
                dout.writeUTF("SendFile");
                option="Y";
            }

            if(option.compareTo("Y")==0)
            {
                FileOutputStream fout=new FileOutputStream(f,true);
                int ch;
                String temp;
                do
                {
                    temp=din.readUTF();
                    ch=Integer.parseInt(temp);
                    if(ch!=-1)
                    {
                        fout.write(ch);
                    }
                }while(ch!=-1);
                fout.close();
                dout.writeUTF("File Send Successfully");
            }
            else
            {
                return;
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
}
