# Minimalistic Price Converter

## Verify APK
Run this command on the downloaded APK
```
apksigner verify --print-certs --verbose minimalistic-price-converter-<version>.apk`
```

Output should contain following:
```
Verifies
Verified using v1 scheme (JAR signing): true
Verified using v2 scheme (APK Signature Scheme v2): true
Verified using v3 scheme (APK Signature Scheme v3): true
Number of signers: 1
Signer #1 certificate DN: CN=Hank Milliken, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown
Signer #1 certificate SHA-256 digest: 075ae3a04f6bbb359a65c0089a289ac143bf23bd3aa75631c9bacfedcb43e5b8
Signer #1 certificate SHA-1 digest: 6f9a462629fa5c12bfe41b0e11802d1a01c2ed77
Signer #1 certificate MD5 digest: 8cd5d74d31f4aedc9879313cd4e9fc2f
Signer #1 key algorithm: RSA
Signer #1 key size (bits): 2048
Signer #1 public key SHA-256 digest: b12341148aee3bdaccde418a24d9f38e01ec1ff2616246803e47373a517b6c9f
Signer #1 public key SHA-1 digest: 06b21c64b3052cd5775ce48969e31924defd7006
Signer #1 public key MD5 digest: 2d3223b33400839b3ba7dc4cfb4cd8cc
```

## Build from source

1. `git clone https://github.com/Minimalistic-Apps/price-converter.git`
2. `cd price-converter`
3. `JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8 ./gradlew assembleRelease`

## Signing the APK
Useful guide: https://medium.com/modulotech/how-to-sign-an-unsigned-apk-using-command-line-636a056373a0

1. Generate key `keytool -genkey -v -keystore ~/.keystore/<your_name>.keystore -alias <your_alias> -keyalg RSA -keysize 2048 -validity 10000`
2. Sign apk `apksigner sign --ks ~/.keystore/<your_name>.keystore app/build/outputs/apk/release/app-release-unsigned.apk`
3. 


