package ru.nsu.fevent.entity

import javax.persistence.*

@Entity
@Table(name = "friends")
data class FriendRelation(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_seq")
    @SequenceGenerator(name = "friend_seq", allocationSize = 1)
    val id: Int = 0,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: FriendStatus,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    val from: User,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    val to: User
)
