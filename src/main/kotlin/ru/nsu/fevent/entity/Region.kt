package ru.nsu.fevent.entity

import javax.persistence.*

@Entity
@Table(name = "geo_regions")
data class Region(
    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "name")
    var name: String = "",

    @ManyToOne(targetEntity = District::class, fetch = FetchType.EAGER)
    var district: District
)
