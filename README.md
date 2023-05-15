# 2023-goosukki-backend
'구석기'의 백엔드 서버 코드


### 개발 현황

- 파이어베이스 연동 완료
  - Firestore Database를 이용한 데이터 CRUD 구현 완료
  - Storage에 이미지 파일을 새롭게 저장하는 기능 구현 완료
- 회원가입 기능 구현 완료
  - Firebase에 가장 최근에 저장된 회원의 순번을 읽어와서, 그 다음 순번을 새 회원에게 부여한 뒤, 회원 정보를 Firestore Database에 저장
  - 아이디 중복 여부 조회 기능
  - 닉네임 중복 여부 조회 기능
- 회원의 프로필 사진 설정 기능 구현 완료
  - 로그인 중인 회원이 새 프로필 사진 파일을 등록하면, 해당 사진 파일을 파이어베이스 Storage에 업로드한 후, Firestore Database 상에서 해당 회원의 프로필 사진 정보 필드 값을 업데이트함.
  - 회원가입 시에는 프로필 사진을 등록하지 않음. 회원가입 완료 후에 로그인 상태에서 자신의 프로필 사진을 변경할 수 있음. 프로필 사진을 변경하지 않은 회원의 프로필 사진은 구기 얼굴 사진임.
<br><br>

### 중요! 다운받아서 실행할 때 주의할 점!!!!!
파이어베이스 콘솔에서 시크릿 키 파일을 다운받아 최상위폴더에 넣어줘야 실행이 됩니다!!<br>
받는 법: 파이어베이스 콘솔 접속>좌측 상단 톱니바퀴>사용자 및 권한>서비스 계정 탭>새 비공개 키 생성 버튼 클릭<br>
<img width="80%" src="https://i.imgur.com/7zgCjsH.jpg"/></br>
<img width="80%" src="https://i.imgur.com/7sZXhS5.jpg"/></br>
<img width="80%" src="https://i.imgur.com/M9Y0XFi.jpg"/></br>
<img width="80%" src="https://i.imgur.com/v5QlX11.jpg"/></br>
이때 이 파일의 이름은 serviceAccountKey.json 이어야 합니다!<br>
이 파일은 타인에게 노출되면 안 되기 때문에 깃허브에 올려지지 않습니다. 깃이그노어에도 포함되어있습니다.<br><br>
서버를 실행하는 방법:
이 레포지토리를 클론해서 받는다.<br>
IntelliJ에서 해당 레포지토리를 연다.<br>
File>Settings...를 누른다.<br>
![image](https://user-images.githubusercontent.com/87855493/236627678-3352248c-12a7-4194-967f-501381f1ddb6.png)
뜬 창의 좌측 상단에 있는 검색창에 gradle을 검색한다.<br>
![image](https://user-images.githubusercontent.com/87855493/236627759-ccd13bde-8f9a-4d7d-8d35-751a15d2a098.png)
Build and run using... 과 Run test using... 을 IntelliJ IDEA로 변경한다.<br>
![image](https://user-images.githubusercontent.com/87855493/236627785-a0941c13-9277-4ec9-a747-cfcb6a3cada1.png)
GoosukkiApplication.java를 클릭하여 띄운다.<br>
7번째 줄인 "public class GoosukkiApplication {" 좌측에 있는 초록색 재생 버튼을 눌러 프로그램을 실행시킨다. (만약 초록 재생 버튼이 안 뜬다면 아직 빌드가 덜 된 것이므로 5분 정도 기다리세요)
