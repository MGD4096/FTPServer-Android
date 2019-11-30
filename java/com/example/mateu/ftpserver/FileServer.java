package com.example.mateu.ftpserver;

/**
 * Created by mateu on 10.06.2016.
 */
        import android.app.Activity;
        import android.graphics.Color;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.net.Inet4Address;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.net.UnknownHostException;


public class FileServer extends Thread{
    private Socket socket;
    private ServerSocket server;
    private TextView Status1;
    private TextView Adres1;
    private Button start;
    private Button stop;
    private EditText Pass1;
    private DataInputStream din;
    private DataOutputStream dout;
    public FileServer(TextView Status1, TextView Adres1, Button start, Button stop,EditText Pass1){
        super("FileServer");
        this.Status1=Status1;
        this.Adres1=Adres1;
        this.start=start;
        this.stop=stop;
        this.Pass1=Pass1;
        start();
    }
    public void run() {

        try {
            server = new ServerSocket(8080);

            /*Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                        ZmianaStanu();
                }
            });

            thread.start();*/

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            socket = server.accept();
            din=new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            String password=din.readUTF();
            String password1=Pass1.getText().toString();
            if(password.compareTo(password1)==0){
                while(true){
                    String Command=din.readUTF();

                    if(Command.compareTo("GET")==0)
                    {
                        System.out.println("\tGET");
                        SendFile send=new SendFile();
                        continue;
                    }
                    else if(Command.compareTo("SEND")==0)
                    {
                        System.out.println("\tSEND");
                        Recive rec=new Recive();
                        continue;
                    }
                    else if(Command.compareTo("LIST")==0)
                    {
                        System.out.println("\tLIST");
                        FileList list=new FileList();
                        continue;
                    }
                    else if(Command.compareTo("DELETE")==0)
                    {
                        System.out.println("\tDELETE");
                        DeleteFile del=new DeleteFile();
                        continue;
                    }
                    else if(Command.compareTo("MAKE")==0)
                    {
                        System.out.println("\tMAKE");
                        MkDirS dir=new MkDirS();
                        continue;
                    }else if(Command.compareTo("RENAME")==0)
                    {
                        System.out.println("\tRENAME");
                        RenameS dir=new RenameS();
                        continue;
                    }
                    else if(Command.compareTo("DISCONNECT")==0)
                    {
                        server.close();
                        socket.close();
                        System.exit(0);
                        break;
                    }

                }
            }else{
                server.close();
                socket.close();
                System.exit(0);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void zatrzymaj(Thread watek) throws IOException{
        System.out.println("Server stoped...");
        ZmianaStanu();
        server.close();
        socket.close();
    }
    public void ZmianaStanu(){

        try{
            if(Status1.getText()=="Włączony"){
                Status1.setText("Wyłączony");
            }
            else{
                Status1.setText("Włączony");

                try {
                    Adres1.setText(Inet4Address.getLocalHost().getHostAddress());
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }catch(NullPointerException exc){}
    }




}

