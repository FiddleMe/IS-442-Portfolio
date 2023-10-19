package com.BackendServices.Email;

public class Email {
    private String recipient;
    private String subject;
    private String msg;
    
    


    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMsg() {
        return msg;
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
