package org.wycliffeassociates.otter.data.model

data class Verse(
        val verseNumber: Integer,
        override val text: String,
        override val slug: String,
        override val sort: Int
): Chunk()