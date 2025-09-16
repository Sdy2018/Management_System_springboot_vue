package org.example.manage.entity;

import java.util.Date;

public class Admin extends Account {

    public Admin(Integer id, String username, String password, String name, String role, String resetPassword, String avatar) {
        super(id, username, password, name, role, resetPassword, avatar);
    }

    public Admin(Account account) {
        super(account.getId(), account.getUsername(), account.getPassword(),  account.getName(), account.getRole(), account.getResetPassword(), account.getAvatar());
    }

    public Admin() {
        super();
    }
}
