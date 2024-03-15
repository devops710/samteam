package com.mysite.samteam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TblWhgsUserInfoService {

    private final TblWhgsUserInfoRepository userRepository;

    public void registerUser(TblWhgsUserInfo userInfo) {
        userRepository.save(userInfo);
    }

    public Optional<TblWhgsUserInfo> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public void updateUser(TblWhgsUserInfo userInfo) {
        userRepository.save(userInfo);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<TblWhgsUserInfo> getAllUsers() {
        return userRepository.findAll();
    }
}


