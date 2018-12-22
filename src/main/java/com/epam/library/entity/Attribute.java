package com.epam.library.entity;

public class Attribute {
    //Why fields are protected ?
    private int id;
    private String name;
    private String secondName;
    private int langID;
    private int coreID;
    private String coreName;
    private String secondCoreName;

    public Attribute() {
    }

    public Attribute(int id, String name, int coreID) {
        this.id = id;
        this.name = name;
        this.coreID = coreID;
    }

    public Attribute(String name, String secondName, int langID) {
        this.name = name;
        this.secondName = secondName;
        this.langID = langID;
    }

    public Attribute(String name, int langID) {
        this.name = name;
        this.langID = langID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getCoreID() {
        return coreID;
    }

    public void setCoreID(int coreID) {
        this.coreID = coreID;
    }

    public int getLangID() {
        return langID;
    }

    public void setLangID(int langID) {
        this.langID = langID;
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public String getSecondCoreName() {
        return secondCoreName;
    }

    public void setSecondCoreName(String secondCoreName) {
        this.secondCoreName = secondCoreName;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", langID=" + langID +
                ", coreID=" + coreID +
                ", coreName='" + coreName + '\'' +
                ", secondCoreName='" + secondCoreName + '\'' +
                '}';
    }
}
