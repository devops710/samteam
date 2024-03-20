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
    private final FirebaseService firebaseService; // FirebaseService 주입

    public void registerUser(TblWhgsUserInfo userInfo) {
        userRepository.save(userInfo);
    }

//    public Optional<TblWhgsUserInfo> loginUser(String userId, String password) {
//        return userRepository.findById(userId)
//                .filter(user -> user.getUserPwd().equals(password) && user.getVrfyYn().equals("Y"));
////                .filter(user -> user.getUserPwd().equals(password));
//    }


    public Optional<TblWhgsUserInfo> loginUser(String userId, String password) {
        Optional<TblWhgsUserInfo> userOpt = userRepository.findById(userId);
        System.out.println(userId);
        System.out.println(password);
        System.out.println(userOpt);
        if (userOpt.isPresent()) {
            TblWhgsUserInfo user = userOpt.get();
            System.out.println(user);
            if (user.getUserPwd().equals(password)) {
                System.out.println("비번일치");
                if ("N".equals(user.getVrfyYn())) {
                    System.out.println("미인증");
                    // 로그인 절차 보류. 웹 푸시 알림 처리 필요
                    firebaseService.sendLoginConfirmationPush(userId); // 웹 푸시 알림 전송
                    System.out.println("구독 요청이 먼저 아닌가요?");
                    return Optional.empty();
                }
                // 인증 상태 'Y'이면 로그인 성공
                return Optional.of(user);
            }
        }
        return Optional.empty(); // 로그인 실패
    }


//    public void verifyUser(String userId) {
//        TblWhgsUserInfo user = userRepository.findById(userId).orElse(null);
//        if (user != null) {
//            user.setVrfyYn("Y");
//            userRepository.save(user);
//        }
//    }

    public void verifyUser(String userId) {
        Optional<TblWhgsUserInfo> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            TblWhgsUserInfo user = userOpt.get();
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
