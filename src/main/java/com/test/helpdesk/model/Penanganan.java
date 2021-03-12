package com.test.helpdesk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Penanganan {
    private String idPenanganan;
    private Teknisi teknisi;
    private Date createdDate;
    private List<Tiket> dataTiket = new ArrayList<>();

    public Penanganan(String idPenanganan,
                      Teknisi teknisi,
                      Date createdDate,
                      List<Tiket> dataTiket) {
        this.idPenanganan = idPenanganan;
        this.teknisi = teknisi;
        this.createdDate = createdDate;
        this.dataTiket = dataTiket;
    }

    public Penanganan() {
    }

    public String getIdPenanganan() {
        return idPenanganan;
    }

    public void setIdPenanganan(String idPenanganan) {
        this.idPenanganan = idPenanganan;
    }

    public Teknisi getTeknisi() {
        return teknisi;
    }

    public void setTeknisi(Teknisi teknisi) {
        this.teknisi = teknisi;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Tiket> getDataTiket() {
        return dataTiket;
    }

    public void setDataTiket(List<Tiket> dataTiket) {
        this.dataTiket = dataTiket;
    }

    @Override
    public String toString() {
        return "Penanganan{" +
                "idPenanganan='" + idPenanganan + '\'' +
                ", teknisi=" + teknisi.toString() +
                ", createdDate=" + createdDate +
                ", dataTiket=" + dataTiket +
                '}';
    }
}
