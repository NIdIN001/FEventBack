package ru.nsu.fevent.entity

import javax.persistence.*

@Entity
@Table(name = "geo_regions")
data class Region(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geo_region_seq")
    @SequenceGenerator(name = "geo_region_seq", allocationSize = 1)
    val id: Int,

    @Column(name = "name")
    var name: String = "",

    @ManyToOne(targetEntity = District::class, fetch = FetchType.EAGER)
    var district: District
)
