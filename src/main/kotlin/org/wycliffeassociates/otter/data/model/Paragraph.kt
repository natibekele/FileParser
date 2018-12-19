package org.wycliffeassociates.otter.data.model


data class Paragraph(
        override var text: String,
        override val type: String,
        override val slug: String,
        override val sort: Int,
        override val chunkNumber: Int
) : Chunk()