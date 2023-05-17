package capstone.part1.goosukki.post.service;

import capstone.part1.goosukki.post.domain.Post;
import capstone.part1.goosukki.post.dto.PostCreateRequestDto;
import capstone.part1.goosukki.post.dto.PostCreateResponseDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

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
}
