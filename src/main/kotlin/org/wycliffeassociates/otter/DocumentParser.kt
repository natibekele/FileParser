package org.wycliffeassociates.otter

import org.wycliffeassociates.otter.data.model.*
import org.wycliffeassociates.otter.enums.MarkdownSyntax.*
import org.wycliffeassociates.otter.enums.UsfmSyntax.*
import org.wycliffeassociates.otter.parser.parseMarkdown
import org.wycliffeassociates.otter.parser.parseUSFM

import java.io.File

class DocumentParser {

    private val sep = System.lineSeparator()

    fun parse(directory: File): List<Book> {
        var bookList = mutableListOf<Book>()
        val fileList = directory.listFiles()


        when(fileList[0].extension) {
            "md" -> {
                return parseMarkdown(directory)
            }
            "usfm" -> {
                return parseUSFM(directory)
            }
            else -> {
                return bookList
            }
        }

    }
}





