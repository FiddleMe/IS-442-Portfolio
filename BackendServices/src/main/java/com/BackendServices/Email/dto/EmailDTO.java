package com.BackendServices.Email.dto;

public class EmailDTO {
    private String sender;
    private String recipient;
    private String subject;
    private String msg;

    public EmailDTO (){

    }

    public EmailDTO (String sender, String recipient, String subject, String msg){
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.msg = msg;
    }
    
    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMsg() {
        return msg;
    }

     public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
