package org.adrahin.contactsbook.model.userModels;

public enum Roles {
    USER(0),
    ADMIN(1);

    private final int role;

    Roles(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
}
