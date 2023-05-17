package capstone.part1.goosukki.photo.service;

import capstone.part1.goosukki.photo.domain.Photo;
import capstone.part1.goosukki.photo.dto.PhotoUploadResponseDto;
import capstone.part1.goosukki.post.dto.PostCreateRequestDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PhotoService {

    // 사용할 데이터베이스
    private static Firestore dbFirestore;

    // 사진을 저장하는 컬렉션의 이름
    private static final String COLLECTION_NAME = "photos";

    private long sequence = 1L;

    // 파이어베이스 Storage 버킷명
    private String firebaseBucket = "springrestdemo1.appspot.com";



    // 유저의 프로필 사진을 변경
    public PhotoUploadResponseDto changeProfile(String memberId, MultipartFile file) throws IOException, FirebaseAuthException, ExecutionException, InterruptedException {
        Long uploadedSequence = uploadPhoto(file);

        // Firestore Database에 저장된 프로필 사진 정보 업데이트
        // 프론트엔드에서 준 ID값을 사용하여, 해당 유저의 고유 번호 알아오기
        Query memberQuery = dbFirestore.collection("members").whereEqualTo("id", memberId);
        ApiFuture<QuerySnapshot> querySnapshot = memberQuery.get();
        String memberSequence = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            memberSequence = document.get("sequence").toString();
        }
        // 알아온 고유 번호 값을 이용하여, 해당 유저의 photo 필드 업데이트
        dbFirestore.collection("members").document(memberSequence).update("photo", String.valueOf(uploadedSequence));

        // 업로드된 프로필 사진의 정보를 담은 DTO를 리턴
        return new PhotoUploadResponseDto(uploadedSequence);
    }


    // 게시글을 올리기 위해, 안드로이드가 보내준 사진들을 캡션과 함께 모두 파이어베이스에 올린 후, 사진들의 고유 번호의 리스트를 리턴
    public List<String> uploadPostsPhotos (MultipartFile[] files, List<String> captions, PostCreateRequestDto requestDto) throws IOException{
        // 리턴할 고유 번호 리스트
        List<String> fileSequences = new ArrayList<>();

        // 모든 사진에 대하여
        for(int i=0; i<files.length; i++) {
            // Storage에 올리고
            String uploadedSequence = Long.toString(uploadPhoto(files[i]));
            // Firestore Database 상의 해당 사진 데이터에 캡션을 추가
            dbFirestore.collection(COLLECTION_NAME).document(uploadedSequence).update("caption", captions.get(i));
            // 리턴할 리스트에 고유 번호 추가
            fileSequences.add(uploadedSequence);
        }

        // 만들어진 고유 번호 리스트를 리턴
        return fileSequences;
    }



    // 사진을 하나 업로드하고, 그 사진에 부여된 고유 번호를 리턴하는 함수
    public Long uploadPhoto (MultipartFile file) throws IOException {
        // 파일명이 겹치면 안 되므로, 현재시각을 파일명으로 설정
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss"));
        // 버켓정보 설정
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream content = new ByteArrayInputStream(file.getBytes());
        // 파일 저장
        Blob blob = bucket.create(fileName, content, file.getContentType());

        // 파일 정보를 고유 번호와 함꼐 Firestore Database에 저장
        dbFirestore = FirestoreClient.getFirestore();

        // 가장 최근에 업로드된 이미지 하나의 데이터 가져오기
        Query query = dbFirestore.collection(COLLECTION_NAME).orderBy("sequence", Query.Direction.DESCENDING).limit(1);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        for (QueryDocumentSnapshot document : documents) {
            // 새 이미지에 부여할 sequence 값을, 가장 최근에 업로드된 이미지보다 1 큰 수로 변경
            sequence = Long.parseLong(document.getId()) + 1;
            System.out.println("sequence of the last photo : " + document.getId());
        }

        // Photo 객체 생성
        Photo photo = new Photo(sequence, fileName);

        // Firestore Database에 Photo 객체를 저장
        dbFirestore.collection(COLLECTION_NAME).document(photo.getSequence()).set(photo);

        return sequence;
    }


}
