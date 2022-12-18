package ru.nsu.fevent.entity

import javax.persistence.*

@Entity
@Table(name = "members")
data class Members(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", allocationSize = 1)
    val id: Int = 0,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    val user: User,

    @ManyToOne(targetEntity = Event::class, fetch = FetchType.EAGER)
    val event: Event
)