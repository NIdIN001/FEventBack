package ru.nsu.fevent.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "geo_districts")
data class District (
    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "name")
    var name: String = "",
)