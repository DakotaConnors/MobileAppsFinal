package edu.coloradomesa.myproject;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * Created by Dakota on 10/3/2017.
 */


class Connect extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String[] params) {
        String sentence;
        String modifiedSentence = "";
        int tries = 5;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Attempting to connect");
        try {
            Socket clientSocket = new Socket("72.166.239.105", 1201);
            System.out.println("connected");
            DataOutputStream Output = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader serverInput = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            if (params.length == 3 ) sentence = params[0] + params[1];
            else sentence = params[0];
            byte[] msg = sentence.getBytes("UTF-16");
            Output.write(msg, 0, msg.length);
            //Output.writeBytes(sentence);
            while (tries > 0) {
                if (serverInput.ready()) {
                    System.out.println("Reading data");
                    String sent;
                    int sentInt;
                    while(serverInput.ready()) {
                        sentInt = serverInput.read();
                        if (sentInt != 0) {
                            char b = (char) sentInt;
                            modifiedSentence += b;
                        }
                    }
                    tries = 0;
                } else if (tries > 0) {
                    try {
                        if (tries == 5) System.out.println("waiting for response");
                        else System.out.println("Still waiting [" + tries + "]");
                        Thread.sleep(5000);
                        tries--;
                    } catch (InterruptedException e) {
                        System.out.println("got interrupted!");
                    }
                }
                else {
                 System.out.println("nothing to read");
                }
                }
                clientSocket.close();
                System.out.println("closed connection");

        } catch (IOException i) {
            System.err.println("ERROR: " + i.getMessage());
            ;
        }
        if (params.length == 3) params[2] = "something";
        else params[1] = "something";
        params[0] = modifiedSentence;
        return modifiedSentence;
    }

    protected void onPostExecute() {
        System.out.println("Post Execute");
    }
}



/*public class Connect {
    public void main() {
        String sentence;
        String modifiedSentence;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Attempting to connect");
        try {
            Socket clientSocket = new Socket("127.0.0.1", 1201);
            System.out.println("connected");
            DataOutputStream Output = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader serverInput = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            sentence = "hello";
            Output.writeBytes(sentence);
            modifiedSentence = serverInput.readLine();
        } catch (IOException i) {
            System.err.println(i.getMessage());
            ;
        }
    }
}*/
