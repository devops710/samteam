package com.mysite.samteam.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.mysite.samteam.repository.FirebaseRepository;
import com.mysite.samteam.vo.FirebaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FirebaseService {

    private final FirebaseRepository firebaseRepository;

    public void saveOrUpdateToken(String userId, String token) {
        FirebaseVO firebaseVO = firebaseRepository.findById(userId)
                .orElse(new FirebaseVO(userId, token));
        firebaseVO.setToken(token);
        firebaseRepository.save(firebaseVO);
    }

    // FirebaseService.java에 추가
    public void sendLoginConfirmationPush(String userId) {
        Optional<FirebaseVO> userToken = firebaseRepository.findById(userId);
        System.out.println(userToken);
        if (userToken.isPresent()) {
            System.out.println("토큰있음");
            String token = userToken.get().getToken();
            // 웹 푸시 알림 전송 로직
            Message message = Message.builder()
                    .setToken(token)
                    .putData("action", "LOGIN_CONFIRMATION")
                    .putData("message", "로그인을 인증해주세요.")
                    .build();

            try {
                String response = FirebaseMessaging.getInstance().send(message);
                System.out.println("Successfully sent message: " + response);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No token found for user: " + userId);
        }
    }

}
