package io.github.marwoj.reactorperformance

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Activity(
    val title: String,
    val groupNumberNonIndexed: Int,
    @Indexed val groupNumberIndexed: Int,
    @Id val id: String? = null
)