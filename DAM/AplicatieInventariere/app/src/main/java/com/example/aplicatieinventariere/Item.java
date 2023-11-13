package com.example.aplicatieinventariere;

import java.io.Serializable;

public class Item implements Serializable {
    protected int cod;
    protected String denumire;
    protected int inventarFaptic;
    protected String url;

    public Item(int cod, String denumire, int inventarFaptic, String url) {
        this.cod = cod;
        this.denumire = denumire;
        this.inventarFaptic = inventarFaptic;
        this.url = url;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getInventarFaptic() {
        return inventarFaptic;
    }

    public void setInventarFaptic(int inventarFaptic) {
        this.inventarFaptic = inventarFaptic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
