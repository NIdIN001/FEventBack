package ru.nsu.fevent.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    val id: Int = 0,

    @Column(name = "login")
    val login: String = "",

    @Column(name = "salt")
    var salt: String = "",

    @Column(name = "password")
    val password: String = "",

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "city")
    var city: String? = null,

    @Column(name = "first_name")
    val firstName: String = "",

    @Column(name = "last_name")
    val lastName: String = "",

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)