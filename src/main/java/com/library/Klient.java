package com.library;

import java.sql.SQLException;

public class Klient extends BaseModel {
    private int id;
    private String imie;
    private String nazwisko;
    private String pesel;


    public Klient(int id) {
        this.id = id;
    }
    public Klient() {}

    public int getId() {
        return id;
    }
    private void setId(int id) { this.id = id; }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public void save() throws SQLException {
        KlientDAO.getInstance().update(this);
    }

    public static Klient create(String firstName, String lastName, String pesel) throws SQLException {
        Klient klient = new Klient();
        klient.setImie(firstName);
        klient.setNazwisko(lastName);
        klient.setPesel(pesel);
        klient.id = KlientDAO.getInstance().create(klient);

        return klient;
    }

    public static void delete(Klient klient) throws SQLException {
        KlientDAO.getInstance().delete(klient.getId());
    }
}