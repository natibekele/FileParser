package org.wycliffeassociates.otter.parser

import org.wycliffeassociates.otter.data.model.*
import org.wycliffeassociates.otter.enums.UsfmSyntax
import java.io.File

fun parseUSFM(directory: File): List<Book> {
    val bookList = mutableListOf<Book>()

    val files = directory.listFiles()
    for (file in files) {
        if(file.extension == "usfm" || file.extension =="USFM") {
            var chunkList = mutableListOf<Chunk>()
            var verseNumber: Int = 0
            var identification = ""
            var bookName: String = ""
            val chapterList = mutableListOf<Chapter>()
            var chapterNum = 1

            val reader = file.bufferedReader()
            reader.use {
                it.forEachLine {
                    val parsedLine = parseUSFMLine(it)
                    when (parsedLine[0]) {
                        UsfmSyntax.MARKER_BOOK_NAME.syntax -> {
                            identification = parsedLine[1]
                        }
                        UsfmSyntax.MARKER_TABLE_OF_CONTENT_SHORT.syntax -> {
                            bookName = parsedLine[1]
                        }
                        UsfmSyntax.MARKER_CHAPTER_NUMBER.syntax -> {
                            //add chunklist to chapter list
                            chapterList.add(Chapter("null", chunkList))
                            // clear chunk list to store chunks for next chapter
                            chunkList = mutableListOf()
                            //get chapter number
                            chapterNum = parsedLine[1]?.let {
                                it.replace("\\s".toRegex(), "").toInt() //strip potential whitespace and convert to int
                            }
                        }
                        UsfmSyntax.MARKER_VERSE_NUMBER.syntax -> {
                            val sub = parsedLine[1].split("\\s+".toRegex(), 2)
                            if (sub.size >= 2) {
                                verseNumber = sub[0].replace("\\s".toRegex(), "").toInt()
                                val verse = Verse(verseNumber, sub[1], "", verseNumber)
                                chunkList.add(verse)
                            }
                        }
                        UsfmSyntax.MARKER_NEW_PARAGRAPH.syntax -> {
                            val paragraphMarker = Paragraph("paragraph marker", "/p", "para", 0,0)
                            chunkList.add(paragraphMarker)
                        }
                        UsfmSyntax.MARKER_SECTION_HEADING.syntax -> {
                        }
                        UsfmSyntax.MARKER_SECTION_HEADING_ONE.syntax -> {
                        }
                        UsfmSyntax.MARKER_SECTION_HEADING_TWO.syntax -> {
                        }
                        UsfmSyntax.MARKER_SECTION_HEADING_THREE.syntax -> {
                        }
                        UsfmSyntax.MARKER_SECTION_HEADING_FOUR.syntax -> {
                        }
                        UsfmSyntax.MARKER_CHUNK.syntax -> {
                        }
                        UsfmSyntax.MARKER_FOOTNOTE.syntax -> {
                        }
                        UsfmSyntax.MARKER_FOOTNOTETEXT.syntax -> {
                        }
                        UsfmSyntax.MARKER_QUOTE.syntax -> {
                        }
                        UsfmSyntax.MARKER_QUOTE_2.syntax -> {
                        }
                        UsfmSyntax.MARKER_M.syntax -> {
                        }
                        "" -> {

                        }
                        else -> {
                            if (parsedLine[0].length == 1) {
                                // add this to the next coming verse
                                //addFormattingToNextVerse(line)
                            } else {

                            }
                        }
                    }
                }
            }
            bookList.add(Book(bookName, bookName, "", chapterList))
        }
    }
    return bookList
}

private fun parseUSFMLine(line: String): List<String> {
    val split = line.split("\\s+".toRegex(), 2)
    if (split.isEmpty()) {
        return listOf()
    }
    return split
}