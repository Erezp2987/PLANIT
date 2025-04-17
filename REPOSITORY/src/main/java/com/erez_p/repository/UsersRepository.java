package com.erez_p.repository;

import android.app.Application;

import com.erez_p.model.User;
import com.erez_p.model.Users;
import com.erez_p.repository.BASE.BaseRepository;
import com.google.firebase.firestore.Query;

public class UsersRepository extends BaseRepository<User, Users> {
    public UsersRepository(Application application)
    {
        super(User.class,Users.class,application);
    }
    @Override
    protected Query getQueryForExist(User entity) {
        return getCollection().whereEqualTo("email", entity.getUserEmail());
    }
}
