# aop-part5-chapter02 - CleanArchitecture로 쇼핑 앱 만들기

------

## 목차

1. 클린아키텍쳐 빌드업
2. 메인화면 구현하기
3. Mock 데이터로 리스트 구현하기
4. 상세화면 및 주문기능 구현하기
5. 프로필 탭 구현하기
6. 파이어베이스로 로그인 기능 구현하기
7. 주문내역 구현하기

## 결과 화면

| 제품 리스트 & 새로고침                                       | 제품 상세 및 주문기능                                        |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [![제품 리스트 & 새로고침](https://camo.githubusercontent.com/7b7e0fb744ae945b062b6bc448b44d7e141709ab819d302a681e1790edeef283/68747470733a2f2f696d6775722e636f6d2f3049456c3037632e6a7067)](https://camo.githubusercontent.com/7b7e0fb744ae945b062b6bc448b44d7e141709ab819d302a681e1790edeef283/68747470733a2f2f696d6775722e636f6d2f3049456c3037632e6a7067) | [![제품 상세 및 주문기능](https://camo.githubusercontent.com/84ec153239ff3925b18e37b12a4a94a0dfc8f8ddb65b78dc50498c486b2371ba/68747470733a2f2f696d6775722e636f6d2f50396c62696f722e6a7067)](https://camo.githubusercontent.com/84ec153239ff3925b18e37b12a4a94a0dfc8f8ddb65b78dc50498c486b2371ba/68747470733a2f2f696d6775722e636f6d2f50396c62696f722e6a7067) |

| 구글 로그인                                                  | 프로필 정보 불러오기및 주문 리스트 불러오기                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [![구글 로그인](https://camo.githubusercontent.com/6373870e4c411f365f30ef0b253769b362f50d3b98dfa93270937fe12cdc4624/68747470733a2f2f696d6775722e636f6d2f4577795a7266662e6a7067)](https://camo.githubusercontent.com/6373870e4c411f365f30ef0b253769b362f50d3b98dfa93270937fe12cdc4624/68747470733a2f2f696d6775722e636f6d2f4577795a7266662e6a7067) | [![프로필 정보 불러오기및 주문 리스트 불러오기](https://camo.githubusercontent.com/2792389029afdd283f99607b79b0a96ea4310cc02bb286641e4431eb393d835d/68747470733a2f2f696d6775722e636f6d2f50493562466b452e6a7067)](https://camo.githubusercontent.com/2792389029afdd283f99607b79b0a96ea4310cc02bb286641e4431eb393d835d/68747470733a2f2f696d6775722e636f6d2f50493562466b452e6a7067) |

---

## 추가 첨부

기존에 Part2 틴더 앱 클론 프로젝트에서 파이어베이스 환경설정을 하면서 이메일 로그인 기능을 구현했다보니 착각한 부분이 있었습니다. 🙏

Part5 Chapter02 클립 06번 영상에서 01:40 ~ 02:00사이에 들어가 있는 내용으로, 이전 프로젝트에서 SHA-1 키 등록에 대한 내용이 있었는데, 기존에 Part2에 틴더 프로젝트에서는 말씀주신 것처럼 SHA-1에 대한 세팅을 해주진 않았었습니다.

이전 프로젝트에서 하고 넘어갔다고 착각하여 해당 내용을 누락한 것에 대한 추가 첨부입니다.

### 파이어베이스에서 Debug용 KeyStore SHA-1키 등록 방법

1. 파이어베이스 콘솔 접속 -> 프로젝트 선택 -> 프로젝트 내 사이드 탭의 **[프로젝트 설정]** 탭을 클릭합니다.

   ![firebase setting](https://imgur.com/q38gkr0.jpg)

2. 하단에 **[디지털 지문 추가]** 버튼을 클릭하여 SHA-1 키 등록을 위한 폼을 설정합니다.

3. 문서는 [Self-signing Your Application](https://developers.google.com/android/guides/client-auth#self-signing_your_application)를 참고하여 SHA-1키를 추출하는 것을 추천합니다.

4. 방법은 Windows /Mac, Linux로 나눠 추출할 수 있습니다.
   디버그 모드에서 사용하는 Keystore는 `debug.keystore` 파일이며, 이 파일의 위치는 아래와 같습니다.

- Windows : C:\Users\your_user_name.android\
- Mac OS, inux : ~/.android/

5. 아래 디렉토리로 이동 이후, 아래 명령어로 keyStore를 통해 SHA-1키를 추출합니다.

- Windows : keystore” -alias androiddebugkey -storepass android -keypass android
- MacOS 및 Linux : keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
  - ![결과 이미지](https://imgur.com/B2NBb3o.jpg)

6. 인증서 지문 중 SHA-1키를 복사하여 아래와 같이 넣어줍니다.

   ![SHA-1](https://imgur.com/HQMHP8W.jpg)

   
