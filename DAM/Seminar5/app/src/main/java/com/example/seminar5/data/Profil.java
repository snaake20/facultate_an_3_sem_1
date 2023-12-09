package com.example.seminar5.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "profile")
public class Profil implements Serializable, Cloneable {
    // @PrimaryKey(autogenerate = true)
    // int id ;
    @PrimaryKey @NonNull
    protected String email;
    protected String nume;
    protected int varsta;

    protected boolean eMajor;

//    protected Gen gen;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean iseMajor() {
        return eMajor;
    }

    public void seteMajor(boolean eMajor) {
        this.eMajor = eMajor;
    }

//    public Gen getGen() {
//        return gen;
//    }

//    public void setGen(Gen gen) {
//        this.gen = gen;
//    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

//    public Profil(String email){
//        this.email=email;
//    }

    public Profil(String email, String nume, int varsta, boolean eMajor) {
        this.email = email;
        this.nume = nume;
        this.varsta = varsta;
        this.eMajor = eMajor;
//        this.gen = gen;
    }

    @Override
    public String toString() {
        return "Profil{" +
                "email='" + email + '\'' +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", eMajor=" + (eMajor ? "da" : "nu") +
//                ", gen=" + gen.toString() +
                '}';
    }


    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        Profil clona = (Profil) super.clone();
        return clona;
    }
}
