package edu.galileo.android.taxiseguro.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rodrigo on 18/06/16.
 */
@JsonIgnoreProperties({"sentByMe"})
public class ChatTaxiServiceMessage {
    private String msg;
    private String sender;
    private boolean sentByMe;//Esto Firebase no la guardar'a en la Bdd, por ello se coloca la notacion

    public ChatTaxiServiceMessage() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if(obj instanceof ChatTaxiServiceMessage){
            ChatTaxiServiceMessage msg = (ChatTaxiServiceMessage)obj;
            equal = this.sender.equals(msg.getSender()) && this.msg.equals(msg.getMsg()) && this.sentByMe == msg.isSentByMe();
        }
        return equal;
    }
}
