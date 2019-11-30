package com.example.mateu.ftpserver;

/**
 * Created by mateu on 10.06.2016.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DeleteFile extends Thread{
    private DataInputStream din;
    private DataOutputStream dout;
    private ServerSocket server;
    private Socket socket;

    public DeleteFile() {
        super("Delete");
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
            System.out.println(filename);
            if(f.exists())
            {
                dout.writeUTF("File Already Exists");
                option=din.readUTF();
            }
            else
            {
                dout.writeUTF("DeleteFile");
                option="Y";
            }

            if(option.compareTo("Y")==0)
            {
                f.delete();
                dout.writeUTF("File Delete Successfully");
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

