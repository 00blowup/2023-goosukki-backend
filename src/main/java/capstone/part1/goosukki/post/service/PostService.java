package capstone.part1.goosukki.post.service;

import capstone.part1.goosukki.post.domain.Post;
import capstone.part1.goosukki.post.dto.PostCreateRequestDto;
import capstone.part1.goosukki.post.dto.PostCreateResponseDto;
import capstone.part1.goosukki.post.dto.PostRecentResponseDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {

    // 사용할 데이터베이스
    private static Firestore dbFirestore;

    // 게시글 정보가 저장될 컬렉션의 이름
    private static final String COLLECTION_NAME = "posts";

    private long sequence = 1L;

    public PostCreateResponseDto addPost(List<String> fileSequences, PostCreateRequestDto requestDto) throws ExecutionException, InterruptedException {
        // fileSequence의 내용과 requestDto의 내용을 종합하여 Firestore Database에 post 정보 하나 올리기
        dbFirestore = FirestoreClient.getFirestore();

        // 가장 최근에 저장된 게시글 하나의 데이터 가져오기
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
            // 새 게시글에 부여할 sequence 값을, 가장 최근에 가입된 회원보다 1 큰 수로 변경
            sequence = Long.parseLong(document.getId()) + 1;
            System.out.println("sequence of the last post : " + document.getId());
        }

        // 만약 데이터베이스가 비어있었다면, sequence의 값은 처음에 초기화한 값인 1L임

        // requestDto에 담긴 writer 아이디를 기준으로, 해당 회원의 고유 번호 알아오기
        Query memberQuery = dbFirestore.collection("members").whereEqualTo("id", requestDto.getWriter());
        ApiFuture<QuerySnapshot> querySnapshot = memberQuery.get();
        String memberSequence = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            memberSequence = document.get("sequence").toString();
        }

        // 저장할 Post 객체 만들기
        Post post = new Post(
                memberSequence,
                requestDto.getTitle(),
                fileSequences,
                requestDto.getLatitude(),
                requestDto.getLongitude(),
                requestDto.getPlacename(),
                requestDto.getSecret()
        );

        // Post 객체의 sequence 지정해주기
        post.setSequence(Long.toString(sequence));

        // 데이터베이스에 Post 객체를 저장
        dbFirestore.collection(COLLECTION_NAME).document(Long.toString(sequence)).set(post);

        // 만들어진 게시글의 내용을 DTO에 담아 리턴
        return new PostCreateResponseDto(post);
    }

    public PostRecentResponseDto recent () {
        // 필요한 것: 작성자 닉네임, 작성자 프로필 사진 파일명, Post 객체, 사진 파일의 이름 문자열 리스트, 캡션 문자열 리스트
        Post post = null;
        String writerNickname = null;
        String writerPhoto = null;
        List<String> filenames = new ArrayList<>();
        List<String> captions = new ArrayList<>();

        String profilePhotoSequence = null;

        // post 컬렉션에 접근, 가장 최근에 올라간 Post 객체 받아오기
        dbFirestore = FirestoreClient.getFirestore();

        // 가장 최근에 저장된 게시글 하나의 데이터 가져오기
        Query postQuery = dbFirestore.collection("posts").orderBy("sequence", Query.Direction.DESCENDING).limit(1);
        ApiFuture<QuerySnapshot> postFuture = postQuery.get();
        List<QueryDocumentSnapshot> postDocuments = null;
        try {
            postDocuments = postFuture.get().getDocuments();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        // DTO에 담을 Post 객체 업데이트
        for (QueryDocumentSnapshot document : postDocuments) {
            post = document.toObject(Post.class);
        }

        // 받아온 Post 객체의 작성자 고유 번호를 이용하여 members 컬렉션에 접근, 그 작성자의 닉네임과 프로필 사진 고유 번호 받아오기
        Query memberQuery = dbFirestore.collection("members").whereEqualTo("sequence", post.getWriter());
        ApiFuture<QuerySnapshot> memberFuture = memberQuery.get();
        List<QueryDocumentSnapshot> memberDocuments = null;
        try {
            memberDocuments = memberFuture.get().getDocuments();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        // 필요한 변수 값을 업데이트
        for(QueryDocumentSnapshot document : memberDocuments) {
            writerNickname = document.get("nickname").toString();
            profilePhotoSequence = document.get("photo").toString();
        }

        // 받아온 프로필 사진 고유 번호를 이용하여, photos 컬렉션에 접근, 프로필 사진의 파일명 받아오기
        Query profileQuery = dbFirestore.collection("photos").whereEqualTo("sequence", profilePhotoSequence);
        ApiFuture<QuerySnapshot> profileFuture = profileQuery.get();
        List<QueryDocumentSnapshot> profileDocuments = null;
        try {
            profileDocuments = profileFuture.get().getDocuments();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        // 필요한 변수 값을 업데이트
        for(QueryDocumentSnapshot document : profileDocuments) writerPhoto = document.get("filename").toString();

        // 받아온 Post 객체의 List<String> files 를 이용하여, photos 컬렉션에 접근, 사진들의 이름 리스트와 캡션 리스트 받아오기
        // 모든 파일 고유번호에 대하여
        for(String file : post.getFiles()) {
            // 파일 고유 번호로 파일 정보 하나 받아오기
            Query photoQuery = dbFirestore.collection("photos").whereEqualTo("sequence", file);
            ApiFuture<QuerySnapshot> photoFuture = photoQuery.get();
            List<QueryDocumentSnapshot> photoDocuments = null;
            try{
                photoDocuments = photoFuture.get().getDocuments();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            // 해당 파일 정보 안에서 파일명과 캡션 꺼내오기
            String name = null;
            String caption = null;
            for(QueryDocumentSnapshot document : photoDocuments) {
                name = document.get("filename").toString();
                caption = document.get("caption").toString();
            }

            // 파일명을 filenames에 추가, 캡션을 captions에 추가
            filenames.add(name);
            captions.add(caption);
        }

        // 얻은 모든 정보를 DTO에 담아 리턴
        return new PostRecentResponseDto(writerNickname, writerPhoto, post, filenames, captions);
    }
}
