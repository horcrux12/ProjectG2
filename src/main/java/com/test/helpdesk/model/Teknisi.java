package com.test.helpdesk.model;

public class Teknisi {
    private String idTeknisi;
    private User user;

    public Teknisi(String idTeknisi, User user) {
        this.idTeknisi = idTeknisi;
        this.user = user;
    }

    public Teknisi() {
    }

    public String getIdTeknisi() {
        return idTeknisi;
    }

    public void setIdTeknisi(String idTeknisi) {
        this.idTeknisi = idTeknisi;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Teknisi{" +
                "idTeknisi='" + idTeknisi + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
