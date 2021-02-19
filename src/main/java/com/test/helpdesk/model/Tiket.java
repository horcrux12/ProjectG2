package com.test.helpdesk.model;

import java.util.Date;

public class Tiket {
    private String idTiket;
    private String problemDesc;
    private String status;
    private User user;
    private Date createdDate;
    private Date updatedDate;

    public Tiket(String idTiket, String problemDesc, String status, User user, Date createdDate, Date updatedDate) {
        this.idTiket = idTiket;
        this.problemDesc = problemDesc;
        this.status = status;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Tiket() {
    }

    public String getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(String idTiket) {
        this.idTiket = idTiket;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Tiket{" +
                "idTiket='" + idTiket + '\'' +
                ", problemDesc='" + problemDesc + '\'' +
                ", status=" + status +
                ", user=" + user.toString() +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
