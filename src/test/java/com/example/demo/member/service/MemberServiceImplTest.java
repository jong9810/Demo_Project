package com.example.demo.member.service;

import com.example.demo.entity.Member;
import com.example.demo.entity.enumerate.MemberStatus;
import com.example.demo.member.dto.MemberJoinDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceImplTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberService memberService;
    
    @Test
    void 회원가입() {
        //given
        Member member = createMember("회원1", 21, "member1", "1234");

        //when
        Long joinedId = memberService.join(member);
        em.flush();
        em.clear();

        //then
        Member joinedMember = em.find(Member.class, joinedId);
        assertThat(joinedMember.getId()).isEqualTo(member.getId());
        assertThat(joinedMember.getName()).isEqualTo(member.getName());
        assertThat(joinedMember.getAge()).isEqualTo(member.getAge());
        assertThat(joinedMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(joinedMember.getPw()).isEqualTo(member.getPw());
        assertThat(joinedMember.getStatus()).isEqualTo(MemberStatus.CREATED);
    }

    @Test
    void 회원전체목록조회() {
        //given
        Member member1 = createMember("회원1", 21, "member1", "1234");
        Member member2 = createMember("회원2", 21, "member2", "1234");
        Member member3 = createMember("회원3", 21, "member3", "1234");
        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        //when
        List<Member> memberList = memberService.getMemberList();

        //then
        assertThat(memberList.size()).isEqualTo(3);
        assertThat(memberList).extracting("name").containsExactly("회원1", "회원2", "회원3");
        assertThat(memberList).extracting("age").containsExactly(21, 21, 21);
        assertThat(memberList).extracting("memberId").containsExactly("member1", "member2", "member3");
        assertThat(memberList).extracting("pw").containsExactly("1234", "1234", "1234");
    }

    private static Member createMember(String name, int age, String memberId, String pw) {
        MemberJoinDto dto = new MemberJoinDto();
        dto.setName(name);
        dto.setAge(age);
        dto.setMemberId(memberId);
        dto.setPw(pw);
        return new Member(dto);
    }
}