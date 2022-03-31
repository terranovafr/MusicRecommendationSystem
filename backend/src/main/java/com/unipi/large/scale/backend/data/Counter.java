package com.unipi.large.scale.backend.data;

import com.unipi.large.scale.backend.entities.mongodb.MongoUser;

import java.util.ArrayList;
import java.util.List;

public class Counter {

    private int count;
    private List<MongoUser> users = new ArrayList<>();

    public Counter() {
    }

    public Counter(int count, MongoUser user) {
        this.count = count;
        this.users.add(user);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MongoUser> getUsers() {
        return users;
    }

    public void setUsers(List<MongoUser> users) {
        this.users = users;
    }
}
