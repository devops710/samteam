// 웹 푸시 알림 구독 및 서비스 워커 등록
if ('serviceWorker' in navigator && 'PushManager' in window) {
    navigator.serviceWorker.register('/firebase-messaging-sw.js').then(registration => {
        console.log('ServiceWorker registration successful with scope: ', registration.scope);
        // 여기에 웹 푸시 구독 로직 추가
    }).catch(err => {
        console.log('ServiceWorker registration failed: ', err);
    });
}
