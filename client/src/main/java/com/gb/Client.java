package com.gb;

import com.gb.classes.Command;
import com.gb.classes.command.UpdateCatalog;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        ObjectEncoderOutputStream oeos = null;
        ObjectDecoderInputStream odis = null;
        try (Socket socket = new Socket("localhost", 6830)){
            oeos = new ObjectEncoderOutputStream(socket.getOutputStream());
//            MyMessage textMessage = new MyMessage("Hello Server!!!");
            Command command = new UpdateCatalog();
//            oeos.writeObject(textMessage);
            oeos.writeObject(command);
            oeos.flush();
            odis = new ObjectDecoderInputStream(socket.getInputStream());
//            MyMessage msgFromServer = (MyMessage) odis.readObject();
            Command msgFromServer = (Command) odis.readObject();
//            System.out.println("Answer from server: " + msgFromServer.getText());
            System.out.println("Answer from server: " + msgFromServer.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                oeos.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            try {
                odis.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
