//

package capstone.part1.goosukki.service;

import capstone.part1.goosukki.domain.Member;
import capstone.part1.goosukki.dto.DuplicateResponseDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MemberService {

    // 사용할 데이터베이스
    private static Firestore dbFirestore;

    private static final String COLLECTION_NAME = "members";

    private long sequence = 1L;

    public String saveMember (Member member) {
        dbFirestore = FirestoreClient.getFirestore();

        // 가장 최근에 가입된 회원 한 명의 데이터 가져오기
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
            // 새 회원에 부여할 sequence 값을, 가장 최근에 가입된 회원보다 1 큰 수로 변경
            sequence = Long.parseLong(document.getId()) + 1;
            System.out.println("sequence of the last member : " + document.getId());
        }

        // 만약 데이터베이스가 비어있었다면, sequence의 값은 처음에 초기화한 값인 1L임

        // Member 객체에 sequence 값 반영하기
        member.setSequence(Long.toString(sequence));

        // 데이터베이스에 Member 객체를 저장
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(member.getSequence()).set(member);

        try {
            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "memberService failed";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "memberService failed";
        }
    }

    // ID 중복 여부를 조회
    public DuplicateResponseDto duplicateId(String id) {
        System.out.println(id);
        dbFirestore = FirestoreClient.getFirestore();
        // 파이어베이스의 회원 정보 중, 해당 아이디를 가진 회원정보만 받아오기
        CollectionReference members = dbFirestore.collection(COLLECTION_NAME);
        Query query = members.whereEqualTo("id", id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        // 받아온 리스트가 비어있는지 점검
        boolean check = true;
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                // 리스트에 요소가 하나 들어있다면 check의 값을 false로 변경
                check = false;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (check) System.out.println("ID not duplicated");
        else System.out.println("ID duplicated");
        // 점검 결과를 DTO에 담아 리턴
        return new DuplicateResponseDto(check);
    }

    // 닉네임 중복 여부를 조회
    public DuplicateResponseDto duplicateNickname(String nickname) {
        System.out.println(nickname);
        dbFirestore = FirestoreClient.getFirestore();
        // 파이어베이스의 회원 정보 중, 해당 닉네임을 가진 회원정보만 받아오기
        CollectionReference members = dbFirestore.collection(COLLECTION_NAME);
        Query query = members.whereEqualTo("nickname", nickname);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        // 받아온 리스트가 비어있는지 점검
        boolean check = true;
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                // 리스트에 요소가 하나 들어있다면 check의 값을 false로 변경
                check = false;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (check) System.out.println("nickname not duplicated");
        else System.out.println("nickname duplicated");
        // 점검 결과를 DTO에 담아 리턴
        return new DuplicateResponseDto(check);
    }
}
