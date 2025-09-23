package com.fastcampus.chatservice.service;

import com.fastcampus.chatservice.entities.Chatroom;
import com.fastcampus.chatservice.entities.Member;
import com.fastcampus.chatservice.entities.MemberChatroomMapping;
import com.fastcampus.chatservice.entities.Message;
import com.fastcampus.chatservice.repository.ChatroomRepository;
import com.fastcampus.chatservice.repository.MemberChatroomMappingRepository;
import com.fastcampus.chatservice.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatroomRepository chatroomRepository;
    private final MemberChatroomMappingRepository memberChatroomMappingRepository;
    private final MessageRepository messageRepository;

    public Chatroom createChatroom(Member member, String title) {
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        chatroom = chatroomRepository.save(chatroom);

        MemberChatroomMapping memberChatroomMapping = chatroom.addMember(member);

       memberChatroomMapping =memberChatroomMappingRepository.save(memberChatroomMapping);

       return chatroom;
    }

    public Boolean joinChatroom(Member member, Long chatroomId) {
        if (memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(),chatroomId)) {
            log.info("이미 참여한 채팅방입니다.");
            return false;
        }
        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatroom)
                .build();

        memberChatroomMappingRepository.save(memberChatroomMapping);

        return true;
    }

    @Transactional
    public Boolean leaveChatroom(Member member, Long chatroomId) {
        if (!memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(),chatroomId)) {
            log.info("참여하지 않는 방입니다.");
            return false;
        }
        memberChatroomMappingRepository.deleteByMemberIdAndChatroomId(member.getId(), chatroomId);

        return true;
    }

    public List<Chatroom> getChatroomList(Member member) {
        List<MemberChatroomMapping> memberChatroomMappingList = memberChatroomMappingRepository.findAllByMemberId(member.getId());

        return memberChatroomMappingList.stream()
                .map(MemberChatroomMapping::getChatroom)
                .toList();
    }

    public Message saveMessage(Member member, Long chatroomId, String text) {

        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        Message message = Message.builder()
                .text(text)
                .member(member)
                .chatroom(chatroom)
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getMessageList(Long chatroomId) {
        return messageRepository.findByChatroomId(chatroomId);
    }
}
