package com.stayzy.service;

import com.stayzy.dto.UserResponse;
import com.stayzy.model.User;

public interface UserService {
    UserResponse getUserProfile(User user);
}
