###### JWT

This repository demonstrates how to use JWT token to pass between different microservices for implementing authentication and authorization. 
Services either connects to Auth service to validate token or to get the claims. Alternatively, using public key encryption we can avoid such calls to the auth service. Services themselves 
can validate the token and extract the token.

Public key encryption requires jks and cer files for signing. Generate these files as below

1. jks: 
   ```
   keytool -genkey -keyalg RSA -alias jwt-secret -keystore jwtsecret.jks -storepass <jks store password> -keypass <key password>
   ```
2. cer: 
   ```
   keytool -export -keystore jwtsecret.jks -alias jwt-secret -file publicKey.cer
   ```

Public key encryption requires jks and cer files to encrypt the claims. Generate these files as below

1. jks: 
   ```
   keytool -genkey -keyalg RSA -alias jwt-consumer -keystore jwtconsumer.jks -storepass <jks store password> -keypass <key password>
   ```
2. cer: 
   ```
   keytool -export -keystore jwtconsumer.jks -alias jwt-consumer -file jwtConsumerPublicKey.cer
   ```

These jks, cer should not be bundled along with the application. These should be injected. For running the application locally,
copy below to application.yaml. 

```yaml
key-store:
    path: classpath:jwtsecret.jks
    password: mypass
    alias: jwt-secret
token-resolver:
    key-store:
        path: classpath:jwtconsumer.jks
        password: mypass
        alias: jwt-consumer
```

By default, the body of the JWT claim is encoded. However, our token contains customerId field which is a PII. Hence, it needs to be encrypted.
Encryption is not done via private key, this is because everyone will have the public key and would be able to decrypt it. So customer id
is encrypted using the public key. It can only be decrypted with the private key. That's how we can be assured of only legitimate party would be able to 
read the customer id.
