package com;

import com.library.KlientDAO;
import com.library.ModelDAO;

import java.util.Arrays;
import java.util.List;

public class settings {
    public static final String appTitle = "Klient app";

    public static final String URL = "";
    public static final String dbFileName = "db.sqlite";

    public static final List<Class<? extends ModelDAO>> models = Arrays.asList(
            KlientDAO.class
    );
}
