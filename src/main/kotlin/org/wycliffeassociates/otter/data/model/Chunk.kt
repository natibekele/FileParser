package org.wycliffeassociates.otter.data.model

abstract class Chunk {
    abstract val text: String
    abstract val slug: String
    abstract val sort: Int
}