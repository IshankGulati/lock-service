package com.lockservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ishank on 15/1/17.
 */
public class Lock {
    private long id;

    private String userId;

    public Lock(){}

    public Lock(long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getUserId() {
        return userId;
    }

}
