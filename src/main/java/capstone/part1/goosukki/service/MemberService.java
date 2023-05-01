//

package capstone.part1.goosukki.service;

import capstone.part1.goosukki.domain.Member;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MemberService {

    private static final String COLLECTION_NAME = "members";

    private long sequence = 1L;

    public String saveMember (Member member) {

        // 사용할 데이터베이스
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // 가장 최근에 가입된 회원 한 명의 데이터 가져오기
        Query query = dbFirestore.collection("members").orderBy("sequence", Query.Direction.DESCENDING).limit(1);
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
}
