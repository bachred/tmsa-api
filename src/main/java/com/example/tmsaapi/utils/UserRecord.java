package com.example.tmsaapi.utils;

public class UserRecord {

    private final int userAdded;
    private final int userskipped;
    private final int TotalRecords;

    public UserRecord(int userAdded, int userskipped, int TotalRecords) {
        this.userAdded = userAdded;
        this.userskipped = userskipped;
        this.TotalRecords = TotalRecords;
    }

    public int getUserAdded() {
        return this.userAdded;
    }

    public int getUserskipped() {
        return this.userskipped;
    }

    public int getTotalRecords() {
        return this.TotalRecords;
    }

}
