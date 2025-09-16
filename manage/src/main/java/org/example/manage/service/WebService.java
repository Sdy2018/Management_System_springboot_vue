package org.example.manage.service;

import org.example.manage.entity.Account;
import org.example.manage.entity.Employee;


public interface WebService {
    void register(Account account);
    Account login(Account account);
    void reset(Account account);
}
