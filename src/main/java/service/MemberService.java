package service;

import entity.Member;
import io.smallrye.mutiny.Uni;
import java.util.List;


public interface MemberService {

    Uni<List<Member>> getAll();

    Uni<Member> getByName(String name);

    Uni<Member> addMember(Member member);

    Uni<Member> updateMember(String name,String updateName);

    Uni<Void> deleteMember(String name);

}
