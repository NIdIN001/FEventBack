package ru.nsu.fevent.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "events")
data class Event(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", allocationSize = 1)
    val id: Int = 0,

    @Column(name = "name")
    val name: String = "",

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "datetime_start")
    val datetimeStart: LocalDateTime = LocalDateTime.now(),

    @Column(name = "datetime_end")
    val datetimeEnd: LocalDateTime = LocalDateTime.now(),

    @Column(name = "latitude")
    val latitude: Float = 0f,

    @Column(name = "longitude")
    val longitude: Float = 0f,

    @Column(name = "max_members")
    val maxMembers: Int? = null,

    @Column(name = "members_count")
    var membersCount: Int = 0,

    @Column(name = "age_min")
    val ageMin: Int? = null,

    @Column(name = "age_max")
    val ageMax: Int? = null,

    @Column(name = "is_online")
    val isOnline: Boolean = false,

    @Column(name = "is_private")
    val isPrivate: Boolean = false,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    val creator: User
)