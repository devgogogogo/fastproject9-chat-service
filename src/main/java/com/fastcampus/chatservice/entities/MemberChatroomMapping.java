package com.fastcampus.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberChatroomMapping {
    @Id
    @Column(name = "member_chatroom_mapping_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;


    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;
}
