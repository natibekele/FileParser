package org.wycliffeassociates.otter

import org.wycliffeassociates.otter.data.model.Book
import org.wycliffeassociates.resourcecontainer.ResourceContainer
import org.wycliffeassociates.resourcecontainer.entity.Project
import org.commonmark.parser.Parser
import org.wycliffeassociates.otter.data.model.Chapter
import org.wycliffeassociates.otter.data.model.Chunk
import org.wycliffeassociates.otter.data.model.Sentence
import org.wycliffeassociates.otter.enums.MarkdownSyntax.*
import java.io.File


fun main(args: Array<String>) {
    var file = File("/Users/NathanShanko/Downloads/en_obs/content/back/50.md")
    val book = DocumentParser().parseMarkdown(file)
    println(book)
}

class DocumentParser {


     fun parse(project: Project, rc: ResourceContainer): Book {
        val dc = rc.manifest.dublinCore
        when (dc.format) {
            "text/markdown" -> {
               val book = parseMarkdown(rc.dir)
                return Book("test book", chapters = book)
            }
//            "usfm", "USFM" -> {
//                parseUSFM(rc.dir, project)
//            }
         else -> {
             return Book()
         }
        }

    }

     fun parseMarkdown(directory: File): Map<Int, Chapter> {
        //val subdirectory = File(directory, "/content/back")
        //val files = subdirectory.listFiles()
        //println(files)
        var chunkList = mapOf<Int, Chunk>()
        var chapterList = mapOf<Int, Chapter>()
        var chapterNumber = 1
       // for (file in files) {
            val reader = directory.bufferedReader()
            reader.use {
                var stringStore = ""
                var sort = 1
                it.forEachLine {
                    if (it == "") {
                        println("break")
                        //break
                        val chunk = parseMarkdownBlock(stringStore, sort)
                        chunk to chunkList[sort]
                        sort++
                    } else {
                        stringStore += it + "\n"
                    }
                }
           }
            var newChapter = Chapter("no title", chunkList,sort = chapterNumber)
            chapterList[chapterNumber] to newChapter
            chapterNumber++
        //}
        return chapterList
    }

    private fun parseUSFM(directory: File, project: Project?) {
        val files = directory.listFiles()
        for (file in files) {
            val reader = file.bufferedReader()
            reader.use {
                it.forEachLine {
                    parseUSFMLine(it)
                }
            }
        }
    }

    private fun parseUSFMLine(line: String) {
        val split = line.split("\\s".toRegex(), 2) //search for spaces in line. limit 2 to stop after verse number
        if (split.isEmpty()) {
            return // no spaces on the line
        }
        when (split[0]) { // split[0] is the usfm syntax marker for the line

        }
    }

    private fun parseMarkdownBlock(text: String, sort: Int): Chunk {
        when (text.get(0)) {
            HEADERS.syntax -> {
                return Sentence(text, "header", "", sort)
            }
            BLOCK_QUOTES.syntax -> {
                return Sentence(text, "block-quote", "", sort)
            }
            BOLD.syntax -> {
                return Sentence(text, "bold-text", "", sort)
            }
            ITALICS.syntax -> {
                return Sentence(text, "italic-text", "", sort)
            }
            LINK.syntax -> {
                return Sentence(text, "link", "", sort)
            }
            IMAGE.syntax -> {
                return Sentence(text, "image-link", "", sort)
            }

            else -> return Sentence(text, "regular-text", "", sort)

        }
    }

}





