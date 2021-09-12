# Minimalistic Price Converter

## Build from source

1. `git clone https://github.com/Minimalistic-Apps/price-converter.git`
2. `cd price-converter`
3. `JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8 ./gradlew assembleRelease`

## Signing the APK
Useful guide: https://medium.com/modulotech/how-to-sign-an-unsigned-apk-using-command-line-636a056373a0

1. Generate key `keytool -genkey -v -keystore ~/.keystore/<your_name>.keystore -alias <your_alias> -keyalg RSA -keysize 2048 -validity 10000`
2. Sign apk `jarsigner -keystore ~/.keystore/<your_name>.keystore app/build/outputs/apk/release/app-release-unsigned.apk <your_alias>` 
   or `apksigner sign --ks ~/.keystore/<your_name>.keystore app/build/outputs/apk/release/app-release-unsigned.apk`
3. 
