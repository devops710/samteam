

// Firebase 설정 및 초기화
    const firebaseConfig = {
        apiKey: "AIzaSyAUjRjVbCO2URZKQu1iMcb1VGJaKp3y608",
        authDomain: "samteam.firebaseapp.com",
        projectId: "samteam",
        storageBucket: "samteam.appspot.com",
        messagingSenderId: "496815299813",
        appId: "1:496815299813:web:01125b6a857d717485080c",
        measurementId: "G-VFGVP34Q6S"
    };
    console.log("얍");
    firebase.initializeApp(firebaseConfig);
    console.log("얍");

    const messaging = firebase.messaging();
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('userId');

// 사용자에게 푸시 알림 권한 요청
    function requestNotificationPermissions() {
        console.log("Requesting notification permission...");
        Notification.requestPermission().then((permission) => {
            if (permission === 'granted') {
                console.log("Notification permission granted.");
                // 현재 등록 토큰 가져오기
                getToken(messaging).then((currentToken) => {
                    if (currentToken) {
                        console.log("Token:", currentToken);
                        // 서버에 사용자의 FCM 토큰을 전송하여 vrfyYn 상태 업데이트 요청
                        fetch('/api/firebase/token', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify({userId: userId, token: currentToken}),
                        })
                            .then(response => response.json())
                            .then(data => console.log('Verification response:', data))
                            .catch(error => console.error('Error:', error));
                    } else {
                        console.log('No registration token available. Request permission to generate one.');
                    }
                }).catch((err) => {
                    console.error('An error occurred while retrieving token. ', err);
                });
            } else {
                console.log('Unable to get permission to notify.');
            }
        });
    }

// 서비스 워커 등록 및 푸시 알림 권한 요청
    if ('serviceWorker' in navigator) {
        console.log("얍");
        navigator.serviceWorker.register('/firebase-messaging-sw.js')
            .then((registration) => {
                console.log('Service Worker 등록 성공:', registration.scope);
                messaging.useServiceWorker(registration);
                requestNotificationPermissions(); // 알림 권한 요청
            }).catch((err) => {
            console.log('Service Worker 등록 실패:', err);
        });
    }

// 예시: 사용자가 웹 푸시 알림에 응답했다고 가정할 때 실행되는 함수
    function notifyServerAboutAuthentication(userId) {
        fetch('/api/user/verify', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({userId: userId, vrfyYn: 'Y'}), // 사용자 ID와 업데이트할 vrfyYn 상태
        })
            .then(response => {
                if (response.ok) {
                    console.log('User authentication status updated successfully.');
                } else {
                    console.error('Failed to update user authentication status.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

