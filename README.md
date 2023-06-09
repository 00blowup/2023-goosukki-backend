# 2023-goosukki-backend
## '구석기'의 백엔드 서버 코드
- Java 및 Spring을 사용
- 원격 데이터베이스로서 Firebase의 Firestore Database 사용. 파일 저장소로서 Firebase Storage 사용.
- 배포 서버로서 AWS의 EC2 인스턴스 사용.


## 개발 현황

- 파이어베이스 연동 완료
  - JSON 데이터 관리를 위한 Firestore Database 연동 완료
  - Storage에 이미지 파일을 새롭게 저장하는 기능 구현 완료
- 회원가입 기능 구현 완료
  - 새 회원 가입 요청을 받음 -> Firebase에 가장 최근에 저장된 회원의 고유 번호 읽어오기 -> 가장 최근에 저장된 회원의 고유 번호에 1을 더한 수를 새 회원의 고유 번호로 부여 -> 새 회원 정보를 Firestore Database에 저장
  - 아이디 중복 여부 조회 기능
  - 닉네임 중복 여부 조회 기능
- 회원의 프로필 사진 설정 기능 구현 완료
  - 로그인 중인 회원이 새 프로필 사진 파일을 등록 -> 해당 사진 파일을 파이어베이스 Storage에 업로드 -> 사진 정보를 고유 번호와 함께 Firestore Databse의 photos 컬렉션에 추가 -> Firestore Database 상의 members 컬렉션에서 해당 회원의 프로필 사진 정보 필드(photo) 값을 해당 사진의 고유번호로 업데이트함.
  - 회원가입 시에는 프로필 사진을 등록하지 않음. 회원가입 완료 후에 로그인 상태에서 자신의 프로필 사진을 변경할 수 있음. 프로필 사진을 변경하지 않은 회원의 프로필 사진은 구기 얼굴 사진임. (Storage에 저장된 defualt.JPG 파일)
- 게시글 업로드 기능 구현 완료
- 게시글 조회 기능 구현 완료
<br><br>

## 서버를 로컬에서 실행하는 법 (배포 이전에 해당함. 이제는 AWS EC2로 배포한 Base URL로 접근하면 됨.)
해당 리포지토리의 코드를 클론받아서 로컬에 저장하세요. 그 후 다음의 과정을 따라하세요.<br>
### 시크릿 키 파일 다운받기
파이어베이스 콘솔에서 시크릿 키 파일을 다운받아 최상위폴더에 넣어줘야 실행이 됩니다!!<br>
이 파일은 타인에게 노출되면 안 되기 때문에 깃허브에 올려지지 않습니다. 깃이그노어에도 포함되어있습니다.<br>
그래서 여러분이 직접 파이어베이스 콘솔에서 받아오셔야 합니다.<br>
받는 법: 파이어베이스 콘솔 접속>좌측 상단 톱니바퀴>사용자 및 권한>서비스 계정 탭>새 비공개 키 생성 버튼 클릭<br>
<img width="80%" src="https://i.imgur.com/7zgCjsH.jpg"/></br>
<img width="80%" src="https://i.imgur.com/7sZXhS5.jpg"/></br>
<img width="80%" src="https://i.imgur.com/M9Y0XFi.jpg"/></br>
<img width="80%" src="https://i.imgur.com/v5QlX11.jpg"/></br>
받으셨다면 파일의 이름을 serviceAccountKey.json 으로 바꾸어 최상위폴더에 넣어주세요!!!!!<br>
<br><br>
### 서버 실행하기
IntelliJ에서 해당 리포지토리를 연다.<br>
File>Settings...를 누른다.<br>
![image](https://user-images.githubusercontent.com/87855493/236627678-3352248c-12a7-4194-967f-501381f1ddb6.png)
뜬 창의 좌측 상단에 있는 검색창에 gradle을 검색한다.<br>
![image](https://user-images.githubusercontent.com/87855493/236627759-ccd13bde-8f9a-4d7d-8d35-751a15d2a098.png)
Build and run using... 과 Run test using... 을 IntelliJ IDEA로 변경한다.<br>
![image](https://user-images.githubusercontent.com/87855493/236627785-a0941c13-9277-4ec9-a747-cfcb6a3cada1.png)
GoosukkiApplication.java를 클릭하여 띄운다.<br>
7번째 줄인 "public class GoosukkiApplication {" 좌측에 있는 초록색 재생 버튼을 눌러 프로그램을 실행시킨다. (만약 초록 재생 버튼이 안 뜬다면 아직 빌드가 덜 된 것이므로 5분 정도 기다리세요)
