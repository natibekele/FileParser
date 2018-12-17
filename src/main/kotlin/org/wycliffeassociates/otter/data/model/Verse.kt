package org.wycliffeassociates.otter.data.model

data class Verse(
        override val chunkNumber: Int,
        override var text: String,
        override val slug: String,
        override val sort: Int
): Chunk()