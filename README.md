# 2023-goosukki-backend
AR을 사용한 위치기반 안드로이드 SNS 서비스 '구석기'의 백엔드 서버입니다.<br><br>
게시글이 작성된 실제 위치에 3D AR 기념물을 남겨, SNS 사용 중 공간감을 느낄 수 있도록 합니다.
각 게시글의 데이터에는 게시글 작성 위치의 좌표(위도, 경도) 정보가 포함됩니다. 앱 내 AR 카메라 기능으로 주위를 둘러보면, 근처에서 작성된 게시글들의 AR 기념물을 볼 수 있습니다. 화면 상에서 AR 기념물을 터치하면 해당 게시글의 내용을 열람합니다.
본 프로젝트의 서버는 Spring Boot를 통해 개발되었으며 AWS EC2, RDS, S3를 통해 배포되었습니다.<br><br>
본 프로젝트는 이화여대 컴퓨터공학전공 졸업프로젝트 "ARchive" 팀의 최종 결과물입니다.

![image](https://github.com/EwhaARchive/2023-goosukki-backend/assets/87855493/2116df2f-3d0d-437b-aa3a-0e48aba9e8af)

### 기술 개요
- Java 및 Spring을 사용
- 원격 데이터베이스로서 Firebase의 Firestore Database 사용. 파일 저장소로서 Firebase Storage 사용
- 배포 서버로서 AWS의 EC2 인스턴스 사용


### 기능 목록

- 파이어베이스 연동
  - JSON 데이터 관리를 위한 Firestore Database 연동
  - Storage에 이미지 파일을 새롭게 저장하는 기능
- 회원정보 관리
  - 회원 가입
  - 아이디 중복 여부 조회
  - 닉네임 중복 여부 조회
  - 회원의 프로필 사진 설정
- 게시글 업로드 기능
- 게시글 조회 기능
<br><br>
