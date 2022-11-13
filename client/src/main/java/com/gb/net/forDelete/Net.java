package com.gb.net.forDelete;

import com.gb.classes.Command;
import com.gb.classes.MyMessage;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Net {
    private final Callback callback;
    private final Socket socket;
    private ObjectEncoderOutputStream oeos = null;
    private ObjectDecoderInputStream odis = null;

    public Net(Callback callback, Socket socket) throws IOException {
        this.callback = callback;
        this.socket = socket;
        oeos = new ObjectEncoderOutputStream(this.socket.getOutputStream());

//        init();

        odis = new ObjectDecoderInputStream(this.socket.getInputStream());


        Thread readThread = new Thread(this::readMessages);
        readThread.setDaemon(true);
        readThread.start();
    }


    public void init(){
        try (Socket socket = new Socket("localhost", 6830)){
            oeos = new ObjectEncoderOutputStream(socket.getOutputStream());
            MyMessage textMessage = new MyMessage("Hello Server!!!");
            oeos.writeObject(textMessage);
            oeos.flush();
            odis = new ObjectDecoderInputStream(socket.getInputStream());
            MyMessage msgFromServer = (MyMessage) odis.readObject();
            System.out.println("Answer from server: " + msgFromServer.getText());
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

    private void readMessages() {
        try {
            while (true) {
                System.out.println("А readMessages вообще запустилось?");
                Command com = (Command) odis.readObject();
                System.out.println("Что-то прошло");
                System.out.println(com.getClass());
                callback.onReceive(com);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessages(Command command) {
        try {
            oeos.writeObject(command);
            oeos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
