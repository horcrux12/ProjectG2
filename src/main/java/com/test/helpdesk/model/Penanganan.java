package com.test.helpdesk.model;

import java.util.ArrayList;

public class Penanganan {
    private String idPenanganan;
    private ArrayList<Tiket> dataTiket = new ArrayList<>();

    public Penanganan(String idPenanganan, ArrayList<Tiket> dataTiket) {
        this.idPenanganan = idPenanganan;
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

    public ArrayList<Tiket> getDataTiket() {
        return dataTiket;
    }

    public void setDataTiket(ArrayList<Tiket> dataTiket) {
        this.dataTiket = dataTiket;
    }

    @Override
    public String toString() {
        return "Penanganan{" +
                "idPenanganan='" + idPenanganan + '\'' +
                ", dataTiket=" + dataTiket.toString() +
                '}';
    }
}
