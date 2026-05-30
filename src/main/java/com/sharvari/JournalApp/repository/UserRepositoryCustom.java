package com.sharvari.JournalApp.repository;

import com.sharvari.JournalApp.model.Users;

import java.util.List;

public interface UserRepositoryCustom {
    List<Users> findUsersWithEmail();
}
