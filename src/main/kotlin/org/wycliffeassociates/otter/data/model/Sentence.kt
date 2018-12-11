package org.wycliffeassociates.otter.data.model

import tornadofx.*

data class Sentence(
        override val text: String,
        val type: String,
        override val slug: String,
        override val sort: Int
) : Chunk()