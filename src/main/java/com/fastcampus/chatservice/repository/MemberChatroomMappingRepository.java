package com.fastcampus.chatservice.repository;

import com.fastcampus.chatservice.entities.Member;
import com.fastcampus.chatservice.entities.MemberChatroomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChatroomMappingRepository extends JpaRepository<MemberChatroomMapping, Long> {



    boolean existsByMemberIdAndChatroomId(Long memberId, Long chatroomId);


    void deleteByMemberIdAndChatroomId(Long memberId, Long chatroomId);

    List<MemberChatroomMapping> findAllByMemberId(Long memberId);
}
