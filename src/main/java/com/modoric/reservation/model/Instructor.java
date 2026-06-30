package com.modoric.reservation.model;

/** レッスンを担当するインストラクターを表すエンティティです。 */
public class Instructor {
    private int id;
    private String name;

    public Instructor() {
    }

    public Instructor(String name) {
        this.name = name;
    }

    public Instructor(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Instructor{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }

}
