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


class DocumentParser {
    val book = Book()

    fun parse(project: Project, rc: ResourceContainer) {
        val dc = rc.manifest.dublinCore
        when (dc.format) {
            "md" -> {
                parseMarkdown(rc.dir, project)
            }
            "usfm", "USFM" -> {
                parseUSFM(rc.dir, project)
            }
        }

    }

    private fun parseMarkdown(directory: File, project: Project): Chapter {
        val files = directory.listFiles()
        val chapter = Chapter()
        for (file in files) {
            val reader = file.bufferedReader()
            reader.use {
                var stringStore = ""
                var sort = 1
                it.forEachLine {
                    if (it == "") {
                        //break
                        val chunk = parseMarkdownBlock(stringStore, sort)
                        chapter.chunks.
                        sort++
                    } else {
                        stringStore += it + "\n"
                    }
                }
            }
        }
    }

    private fun parseUSFM(directory: File, project: Project) {
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





