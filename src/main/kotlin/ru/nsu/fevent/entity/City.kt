package ru.nsu.fevent.entity

import javax.persistence.*

@Entity
@Table(name = "geo_cities")
data class City (
    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "name")
    var name: String = "",

    @ManyToOne(targetEntity = Region::class, fetch = FetchType.EAGER)
    var region: Region
)