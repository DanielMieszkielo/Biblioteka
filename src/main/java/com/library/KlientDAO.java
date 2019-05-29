package com.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class KlientDAO extends ModelDAO<Klient> {
    private static KlientDAO instance = new KlientDAO();
    public static KlientDAO getInstance() { return instance; }

    @Override
    public String createTableSql() {
        return "CREATE TABLE IF NOT EXISTS klient(" +
                "id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "first_name VARCHAR(30)     NOT NULL," +
                "last_name  VARCHAR(30)     NOT NULL," +
                "pesel      CHARACTER(11)   NOT NULL UNIQUE)";
    }

    @Override
    public Klient parse(ResultSet resultSet) throws SQLException {
        Klient klient = new Klient(resultSet.getInt("id"));
        klient.setImie(resultSet.getString("first_name"));
        klient.setNazwisko(resultSet.getString("last_name"));
        klient.setPesel(resultSet.getString("pesel"));
        return klient;
    }

    @Override
    public ArrayList<Klient> parseMultiple(ResultSet resultSet) throws SQLException {
        ArrayList<Klient> klienci = new ArrayList<>();
        while (resultSet.next()) {
            klienci.add(this.parse(resultSet));
        }
        return klienci;
    }

    @Override
    public ArrayList<Klient> get(HashMap<String, String> params) throws SQLException {
        String query = "SELECT * FROM klient";
        query += Helpers.prepare_query_params(params);
        ResultSet rs = DatabaseManager.getInstance().executeQuery(query);

        return this.parseMultiple(rs);
    }

    public Klient get(int id) throws SQLException {
        String query = "SELECT * FROM klient WHERE id=" + id;
        return this.parse(DatabaseManager.getInstance().executeQuery(query));
    }

    @Override
    public ArrayList<Klient> all() throws SQLException {
        String query = "SELECT * FROM klient";
        ResultSet resultSet = DatabaseManager.getInstance().executeQuery(query);

        return this.parseMultiple(resultSet);
    }

    @Override
    public void update(Klient klient) throws SQLException {
        String query = "UPDATE klient SET"
                + " first_name='" + klient.getImie() + "'"
                + ",last_name='" + klient.getNazwisko() + "'"
                + ",pesel='" + klient.getPesel() + "'"
                + " WHERE id=" + klient.getId();
        DatabaseManager.getInstance().executeUpdate(query);
    }

    @Override
    public int create(Klient klient) throws SQLException {
        String query = "INSERT INTO 'klient' (first_name,last_name,pesel) VALUES (\n" +
                "  '" + klient.getImie() + "',\n" +
                "  '" + klient.getNazwisko() + "',\n" +
                "  '" + klient.getPesel() + "'\n" +
                ");";
        int id = DatabaseManager.getInstance().executeInsert(query);
        if (id == -1) {
            throw new SQLException("Error while trying to CREATE");
        }
        return id;
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM klient WHERE id=" + id;
        DatabaseManager.getInstance().executeUpdate(query);
    }
}
