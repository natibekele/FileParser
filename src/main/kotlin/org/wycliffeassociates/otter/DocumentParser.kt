package org.wycliffeassociates.otter

import org.wycliffeassociates.otter.data.model.*
import org.wycliffeassociates.otter.enums.MarkdownSyntax.*
import org.wycliffeassociates.otter.enums.UsfmSyntax.*

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

    private fun parseMarkdown(directory: File): List<Book> {
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

    private fun parseUSFM(directory: File): List<Book> {
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
                                MARKER_BOOK_NAME.syntax -> {
                                    identification = parsedLine[1]
                                }
                                MARKER_TABLE_OF_CONTENT_SHORT.syntax -> {
                                    bookName = parsedLine[1]
                                }
                                MARKER_CHAPTER_NUMBER.syntax -> {
                                    //add chunklist to chapter list
                                    chapterList.add(Chapter("null", chunkList))
                                    // clear chunk list to store chunks for next chapter
                                    chunkList = mutableListOf()
                                    //get chapter number
                                    chapterNum = parsedLine[1]?.let {
                                        it.replace("\\s".toRegex(), "").toInt() //strip potential whitespace and convert to int
                                    }
                                }
                                MARKER_VERSE_NUMBER.syntax -> {
                                    val sub = parsedLine[1].split("\\s+".toRegex(), 2)
                                    if (sub.size >= 2) {
                                        verseNumber = sub[0].replace("\\s".toRegex(), "").toInt()
                                        val verse = Verse(verseNumber, sub[1], "", verseNumber)
                                        chunkList.add(verse)
                                    }
                                }
                                MARKER_NEW_PARAGRAPH.syntax -> {
                                    val paragraphMarker = Paragraph("paragraph marker", "/p", "para", 0,0)
                                    chunkList.add(paragraphMarker)
                                }
                                MARKER_SECTION_HEADING.syntax -> {
                                }
                                MARKER_SECTION_HEADING_ONE.syntax -> {
                                }
                                MARKER_SECTION_HEADING_TWO.syntax -> {
                                }
                                MARKER_SECTION_HEADING_THREE.syntax -> {
                                }
                                MARKER_SECTION_HEADING_FOUR.syntax -> {
                                }
                                MARKER_CHUNK.syntax -> {
                                }
                                MARKER_FOOTNOTE.syntax -> {
                                }
                                MARKER_FOOTNOTETEXT.syntax -> {
                                }
                                MARKER_QUOTE.syntax -> {
                                }
                                MARKER_QUOTE_2.syntax -> {
                                }
                                MARKER_M.syntax -> {
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

    private fun parseMarkdownBlock(text: String, sort: Int): Chunk {
        when (text.get(0)) {
            HEADERS.syntax -> {
                return Sentence(text, "header", "sentence_$sort", sort, sort)
            }
            BLOCK_QUOTES.syntax -> {
                return Sentence(text, "block-quote", "sentence_$sort", sort, sort)
            }
            BOLD.syntax -> {
                return Sentence(text, "bold-text", "sentence_$sort", sort, sort)
            }
            ITALICS.syntax -> {
                return Sentence(text, "italic-text", "sentence_$sort", sort, sort)
            }
            LINK.syntax -> {
                return Sentence(text, "link", "sentence_$sort", sort, sort)
            }
            IMAGE.syntax -> {
                return Sentence(text, "image-link", "sentence_$sort", sort, sort)
            }

            else -> return Sentence(text, "regular-text", "sentence_$sort", sort, sort)

        }
    }

}





