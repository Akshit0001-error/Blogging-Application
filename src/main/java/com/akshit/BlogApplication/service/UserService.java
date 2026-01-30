package com.akshit.BlogApplication.service;

import com.akshit.BlogApplication.domain.entity.User;

import java.util.UUID;

public interface UserService {

    User getUserByID(UUID id);
}
