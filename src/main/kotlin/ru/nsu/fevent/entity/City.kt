package ru.nsu.fevent.entity

import javax.persistence.*

@Entity
@Table(name = "geo_cities")
data class City (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geo_city_seq")
    @SequenceGenerator(name = "geo_city_seq", allocationSize = 1)
    val id: Int,

    @Column(name = "name")
    var name: String = "",

    @ManyToOne(targetEntity = Region::class, fetch = FetchType.EAGER)
    var region: Region
)