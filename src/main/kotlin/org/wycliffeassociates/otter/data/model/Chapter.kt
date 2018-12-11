package org.wycliffeassociates.otter.data.model

data class Chapter(
        var title: String? = null,
        var chunks: Map<Int, Chunk>? = null,
        var slug: String? = null,
        var sort: Int? = null
)