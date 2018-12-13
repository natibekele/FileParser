package org.wycliffeassociates.otter.data.model


data class Sentence(
        override var text: String,
        val type: String,
        override val slug: String,
        override val sort: Int
) : Chunk()