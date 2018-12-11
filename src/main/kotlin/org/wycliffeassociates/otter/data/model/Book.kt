package org.wycliffeassociates.otter.data.model

data class Book (
    val title: String? = null,
    val bookName: String? = null,
    val bookAbrev: String? = null,
    val chapters: Map<Int, Chapter>? = null,
    val slug: String? = null,
    val sort: Int ? = null
)