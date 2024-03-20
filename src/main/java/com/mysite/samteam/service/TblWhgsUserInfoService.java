package com.mysite.samteam.service;

import com.mysite.samteam.repository.TblWhgsUserInfoRepository;
import com.mysite.samteam.vo.TblWhgsUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TblWhgsUserInfoService {

    private final TblWhgsUserInfoRepository userRepository;

    public void registerUser(TblWhgsUserInfo userInfo) {
        userRepository.save(userInfo);
    }

    public Optional<TblWhgsUserInfo> loginUser(String userId, String password) {
        return userRepository.findById(userId)
                .filter(user -> user.getUserPwd().equals(password) && user.getVrfyYn().equals("Y"));
//                .filter(user -> user.getUserPwd().equals(password));
    }

    public void verifyUser(String userId) {
        TblWhgsUserInfo user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setVrfyYn("Y");
            userRepository.save(user);
        }
    }




    public boolean updateUser(String userId, TblWhgsUserInfo updateInfo) {
        Optional<TblWhgsUserInfo> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            TblWhgsUserInfo user = userOptional.get();
            if (updateInfo.getUserPwd() != null && !updateInfo.getUserPwd().isEmpty()) {
                user.setUserPwd(updateInfo.getUserPwd());
            }
            if (updateInfo.getHpNo() != null && !updateInfo.getHpNo().isEmpty()) {
                user.setHpNo(updateInfo.getHpNo());
            }
            if (updateInfo.getCompany() != null && !updateInfo.getCompany().isEmpty()) {
                user.setCompany(updateInfo.getCompany());
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
