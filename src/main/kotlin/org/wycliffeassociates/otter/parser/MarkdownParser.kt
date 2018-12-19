package org.wycliffeassociates.otter.parser

import org.wycliffeassociates.otter.data.model.Book
import org.wycliffeassociates.otter.data.model.Chapter
import org.wycliffeassociates.otter.data.model.Chunk
import org.wycliffeassociates.otter.data.model.Sentence
import org.wycliffeassociates.otter.enums.MarkdownSyntax
import java.io.File


 fun parseMarkdown(directory: File): List<Book> {
    val files = directory.listFiles()
    var chunkList = mutableListOf<Chunk>()
    var chapterList = mutableListOf<Chapter>()
    val bookList = mutableListOf<Book>()
    for (file in files) {
        if (file.extension == "md" || file.extension == "MD") {
            val chapterNumber = file.nameWithoutExtension.toInt()
            val reader = file.bufferedReader()
            reader.use {
                var stringStore = ""
                var sort = 1
                it.forEachLine {
                    if (it == "" && stringStore != "") { //if empty line
                        //break
                        val chunk = parseMarkdownBlock(stringStore, sort)
                        chunkList.add(chunk)
                        sort++
                        stringStore = "" //reset string store
                    } else {
                        stringStore += it + "\n"
                    }
                }
            }
            var chapterTitle = chunkList[0]?.text
            var newChapter = Chapter(chapterTitle, chunkList, sort = chapterNumber)
            chapterList.add(newChapter)
            chunkList = mutableListOf()
        }
        else {
            Error("not a md file", Throwable())
        }
    }


    bookList.add(Book("", chapters = chapterList))
    return bookList
}


private fun parseMarkdownBlock(text: String, sort: Int): Chunk {
    when (text.get(0)) {
        MarkdownSyntax.HEADERS.syntax -> {
            return Sentence(text, "header", "sentence_$sort", sort, sort)
        }
        MarkdownSyntax.BLOCK_QUOTES.syntax -> {
            return Sentence(text, "block-quote", "sentence_$sort", sort, sort)
        }
        MarkdownSyntax.BOLD.syntax -> {
            return Sentence(text, "bold-text", "sentence_$sort", sort, sort)
        }
        MarkdownSyntax.ITALICS.syntax -> {
            return Sentence(text, "italic-text", "sentence_$sort", sort, sort)
        }
        MarkdownSyntax.LINK.syntax -> {
            return Sentence(text, "link", "sentence_$sort", sort, sort)
        }
        MarkdownSyntax.IMAGE.syntax -> {
            return Sentence(text, "image-link", "sentence_$sort", sort, sort)
        }

        else -> return Sentence(text, "regular-text", "sentence_$sort", sort, sort)

    }
}
