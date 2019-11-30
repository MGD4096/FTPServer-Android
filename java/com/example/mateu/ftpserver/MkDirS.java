package com.example.mateu.ftpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mateu on 21.06.2016.
 */
public class MkDirS extends Thread{

    private DataInputStream din;
    private DataOutputStream dout;
    private ServerSocket server;
    private Socket socket;
    /**
     * nadpisana metoda z klasy abstrakcyjnej Thread. Tworzy połączenie socket na kolejnym porcie oraz strumienie do odczytu i zapisu na tym sockecie
     * po czym sprawdza czy plik istnieje i jeśli istnieje zostaje wysłany
     * @exception IOException
     * @exception InterruptedException
     */
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
            if(f.exists())
            {
                dout.writeUTF("Dir Already Exists");
                return;
            }
            else
            {
                dout.writeUTF("MakeDir");
                f.mkdir();
            }
            dout.writeUTF("Make Dir Successfully");
            server.close();
            socket.close();
            din.close();
            dout.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public MkDirS() {
        super("MakeDir Server");
        start();
    }
}
