package com.candan.mail;

public class EmailBody {
    private  String address;
    private int skinPageNumber;
    private int skinRowPerPage;
    private int heartPageNumber;
    private int heartRowPerPage;
    private int envPageNumber;
    private int envRowPerPage;

    public String getAddress() { return address;  }

    public int getSkinPageNumber() { return skinPageNumber; }

    public int getSkinRowPerPage() { return skinRowPerPage; }

    public int getHeartPageNumber() { return heartPageNumber; }

    public int getHeartRowPerPage() { return heartRowPerPage; }

    public int getEnvPageNumber() { return envPageNumber; }

    public int getEnvRowPerPage() { return envRowPerPage; }
}
