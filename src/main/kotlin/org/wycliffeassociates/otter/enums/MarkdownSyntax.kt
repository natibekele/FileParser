package org.wycliffeassociates.otter.enums

enum class MarkdownSyntax(val syntax: Char) {
    HEADERS('#'),
    BLOCK_QUOTES('>'),
    BOLD('*'),
    ITALICS('_'),
    LINK('['),
    IMAGE('!')

}