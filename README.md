###### JWT

This repository demonstrates how to use JWT token to pass between different microservices for implementing authentication and authorization. 
Services either connects to Auth service to validate token or to get the claims. Alternatively, using public key encryption we can avoid such calls to the auth service. Services themselves 
can validate the token and extract the token.

Public key encryption requires jks and cer files. Generate these files as below

1. jks: `keytool -genkey -keyalg RSA -alias jwt-secret -keystore jwtsecret.jks -storepass <jks store password> -keypass <key password>`
2. cer: `keytool -export -keystore jwtsecret.jks -alias jwt-secret -file publicKey.cer`
