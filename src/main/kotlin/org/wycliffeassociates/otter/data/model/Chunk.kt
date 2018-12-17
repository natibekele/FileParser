package org.wycliffeassociates.otter.data.model

abstract class Chunk {
    abstract var text: String
    abstract val slug: String
    abstract val sort: Int
    abstract val chunkNumber: Int?
}