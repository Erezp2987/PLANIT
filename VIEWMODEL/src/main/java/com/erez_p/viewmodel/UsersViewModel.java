package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.User;
import com.erez_p.model.Users;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.repository.UsersRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;
import com.google.firebase.firestore.Query;

public class UsersViewModel extends BaseViewModel <User, Users>{
    private UsersRepository repository;
    @Override
    protected BaseRepository<User,Users> createRepository(Application application) {
        repository=new UsersRepository(application);
        return repository;
    }

    public UsersViewModel(Application application) {
        super(User.class,Users.class,application);
    }
//    public void getAll(){
//        getAll(Query.Direction.ASCENDING);
//    }
    public void getAll(Query.Direction direction) {
        getAll(repository.getCollection().orderBy("userName", direction));
    }
    public void getUserByEmail(String email)
    {
        get(repository.getCollection().whereEqualTo("userEmail",email));
    }
}
