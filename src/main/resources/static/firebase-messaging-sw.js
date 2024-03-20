importScripts('https://www.gstatic.com/firebasejs/10.9.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/10.9.0/firebase-messaging.js');

const firebaseConfig = {
    apiKey: "AIzaSyAUjRjVbCO2URZKQu1iMcb1VGJaKp3y608",
    authDomain: "samteam.firebaseapp.com",
    projectId: "samteam",
    storageBucket: "samteam.appspot.com",
    messagingSenderId: "496815299813",
    appId: "1:496815299813:web:01125b6a857d717485080c",
    measurementId: "G-VFGVP34Q6S"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();
messaging.usePublicVapidKey("BHg50PxopH9k2tuqZ80HaFwybZ7-M7-CeTcHa4C_xdzPTseQmWRHYom_i2nrIIAmS69BYXOUVhldxJJN3HBBa_s");

messaging.onBackgroundMessage(payload => {
    console.log(
        '[firebase-messaging-sw.js] Received background message ',
        payload,
    )
});