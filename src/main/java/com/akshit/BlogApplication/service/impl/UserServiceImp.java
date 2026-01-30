package com.akshit.BlogApplication.service.impl;

import com.akshit.BlogApplication.domain.entity.User;
import com.akshit.BlogApplication.repository.UserRepository;
import com.akshit.BlogApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByID(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+ id));
    }
}
