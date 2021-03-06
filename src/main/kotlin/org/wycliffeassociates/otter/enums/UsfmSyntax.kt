package org.wycliffeassociates.otter.enums

enum class UsfmSyntax(val syntax: String) {
    MARKER_BOOK_NAME("\\id"),
    MARKER_MAJOR_TITLE("\\mt"),
    MARKER_TABLE_OF_CONTENTS_LONG("\\toc1"),
    MARKER_TABLE_OF_CONTENT_SHORT("\\toc2"),
    MARKER_TOC_ABBREV("\\toc3"),
    MARKER_CHAPTER_NUMBER("\\c"),
    MARKER_VERSE_NUMBER("\\v"),
    MARKER_NEW_PARAGRAPH("\\p"),
    MARKER_SECTION_HEADING("\\s"),
    MARKER_SECTION_HEADING_ONE("\\s1"),
    MARKER_SECTION_HEADING_TWO("\\s2"),
    MARKER_SECTION_HEADING_THREE("\\s3"),
    MARKER_SECTION_HEADING_FOUR("\\s4"),
    MARKER_CHUNK("\\s5"),
    MARKER_FOOTNOTE("\\f"),
    MARKER_FOOTNOTETEXT("\\ft"),
    MARKER_QUOTE("\\q"),
    MARKER_QUOTE_2("\\q2"),
    MARKER_M("\\m")

}