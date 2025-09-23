package com.fastcampus.chatservice.controller;

import com.fastcampus.chatservice.dto.ChatMessage;
import com.fastcampus.chatservice.dto.ChatroomDto;
import com.fastcampus.chatservice.entities.Chatroom;
import com.fastcampus.chatservice.entities.Message;
import com.fastcampus.chatservice.service.ChatService;
import com.fastcampus.chatservice.vos.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chats")
@RestController
public class ChatController {

    private final ChatService chatService;


    @PostMapping
    public ChatroomDto createChatroom(@AuthenticationPrincipal CustomOAuth2User user, @RequestBody String title) {
        Chatroom chatroom = chatService.createChatroom(user.getMember(), title);

        return ChatroomDto.from(chatroom);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(@PathVariable Long chatroomId, @AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.joinChatroom(user.getMember(), chatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@PathVariable Long chatroomId, @AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.leaveChatroom(user.getMember(), chatroomId);
    }

    @GetMapping
    public List<ChatroomDto> getChatroomList(@AuthenticationPrincipal CustomOAuth2User user) {
        List<Chatroom> chatroomList = chatService.getChatroomList(user.getMember());

        return chatroomList.stream()
                .map(ChatroomDto::from)
                .toList();
    }

    @GetMapping("/{chatroomId}/messages")
    public List<ChatMessage> getMessagesList(@PathVariable Long chatroomId) {
        List<Message> messageList = chatService.getMessageList(chatroomId);
       return messageList.stream()
                .map(message -> new ChatMessage(message.getMember().getNickname(), message.getText()))
                .toList();
    }
}

