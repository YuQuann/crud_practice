package service.impl;

import entity.Member;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import repository.MemberRepository;
import service.MemberService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@ApplicationScoped
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Inject
    MemberRepository memberRepository;

    @Override
    public Uni<List<Member>> getAll() {
        return memberRepository.listAll();
    }

    @Override
    public Uni<Member> getByName(String name) {

        Random random = new Random();


        return memberRepository.find("name", name).firstResult()
                .onItem().transform(member -> {
                    if (member != null) {
                        member.setCreateDateTime(LocalDateTime.now())
                                .setEmpty(false)
                                .setModifyDateTime(LocalDateTime.now().minusDays(3))
                                .setRandomLuckyNumber(random.nextInt(1000));
                        return member;
                    } else {
                        Member memberEmpty = new Member();
                        memberEmpty.setCreateDateTime(LocalDateTime.now())
                                .setEmpty(true)
                                .setRandomLuckyNumber(1000);
                        return memberEmpty;
                    }
                });
    }

    @Override
    public Uni<Member> addMember(Member member) {

        String newMemberName = member.getName();

        return memberRepository.find("name",newMemberName).firstResult()
                .onItem().transformToUni(existMember -> {
                    if (existMember == null){
                        return memberRepository.persist(member);
                    }else {
                        Member emptyMember = new Member();
                        emptyMember.setEmpty(true);
                        return Uni.createFrom().item(emptyMember);
                    }
                });
    }

    @Override
    public Uni<Member> updateMember(String name, String updateName) {

        return memberRepository.find("name", name).firstResult()
                .onItem().transformToUni(member -> {
                    if (member != null) {
                        member.setName(updateName);
                        return memberRepository.update(member);
                    } else {
                        return Uni.createFrom().nullItem();
                    }
                });
    }

    @Override
    public Uni<Void> deleteMember(String name) {

        return memberRepository.find("name", name).firstResult()
                .onItem().transformToUni(member -> memberRepository.delete(member));
    }
}
