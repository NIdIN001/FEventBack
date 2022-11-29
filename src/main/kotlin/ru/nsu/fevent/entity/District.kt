package ru.nsu.fevent.entity

import javax.persistence.*

@Entity
@Table(name = "geo_districts")
data class District (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geo_district_seq")
    @SequenceGenerator(name = "geo_district_seq", allocationSize = 1)
    val id: Int,

    @Column(name = "name")
    var name: String = "",
)