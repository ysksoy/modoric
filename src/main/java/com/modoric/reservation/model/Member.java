package com.modoric.reservation.model;

/** 予約システムを利用する会員を表すエンティティです。 */
public class Member {
    private final int id;
    private final String name;
    private final String email;

    public Member(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Member{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + '}';
    }

}
