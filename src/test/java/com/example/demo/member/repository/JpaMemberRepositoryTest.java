package com.example.demo.member.repository;

import com.example.demo.entity.Member;
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
class JpaMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    
    @Test
    void 회원저장_id로_찾기() {
        //if empty
        Member emptyMember = memberRepository.findById(1L);
        assertThat(emptyMember).isNull();
        
        //given
        Member member = createMember("회원1", 20, "member1", "1234");

        //when
        Long savedId = memberRepository.save(member);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(savedId);

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getAge()).isEqualTo(member.getAge());
        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(findMember.getPw()).isEqualTo(member.getPw());
        assertThat(findMember.getStatus()).isEqualTo(member.getStatus());
    }

    @Test
    void memberId로_회원찾기() {
        //given
        String memberId = "member1";
        Member member = createMember("회원1", 20, memberId, "1234");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByMemberId(memberId);

        //then
        assertThat(findMember).isEqualTo(member);
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getAge()).isEqualTo(member.getAge());
        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(findMember.getPw()).isEqualTo(member.getPw());
        assertThat(findMember.getStatus()).isEqualTo(member.getStatus());
    }

    @Test
    void 모든회원찾기() {
        //if empty
        List<Member> emptyList = memberRepository.findAll();
        assertThat(emptyList.size()).isEqualTo(0);

        //given
        Member member1 = createMember("회원1", 21, "member1", "1234");
        Member member2 = createMember("회원2", 22, "member2", "2345");
        Member member3 = createMember("회원3", 23, "member3", "3456");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        em.flush();
        em.clear();

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        assertThat(memberList.size()).isEqualTo(3);
        assertThat(memberList).extracting("name").containsExactly("회원1", "회원2", "회원3");
        assertThat(memberList).extracting("age").containsExactly(21, 22, 23);
        assertThat(memberList).extracting("memberId").containsExactly("member1", "member2", "member3");
        assertThat(memberList).extracting("pw").containsExactly("1234", "2345", "3456");
    }

    @Test
    void 회원삭제() {
        //given
        Member member = createMember("회원1", 21, "member1", "1234");
        memberRepository.save(member);

        //when
        Long deletedId = memberRepository.delete(member);
        em.flush();
        em.clear();
        Member deletedMember = memberRepository.findById(deletedId);

        //then
        assertThat(deletedId).isEqualTo(1L);
        assertThat(deletedMember).isNull();
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