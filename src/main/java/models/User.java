package models;

public class User {
    private int id;
    private String name;
    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public int getId() {
        return id;
    }

    public User(int id, String name, String bankName){
        this.id=id;
        this.name=name;
        this.bankName = bankName;
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
}
