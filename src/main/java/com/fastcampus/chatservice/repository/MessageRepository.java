package com.fastcampus.chatservice.repository;

import com.fastcampus.chatservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatroomId(Long chatroomId);
}
