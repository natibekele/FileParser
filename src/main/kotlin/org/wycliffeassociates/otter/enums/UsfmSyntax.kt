package org.wycliffeassociates.otter.enums

enum class UsfmSyntax(val sytnax: String) {
    MARKER_BOOK_NAME("\\id"),
    MARKER_CHAPTER_NUMBER("\\c"),
    MARKER_VERSE_NUMBER("\\v"),
    MARKER_NEW_PARAGRAPH("\\p"),
    MARKER_SECTION_HEADING("\\s"),
    MARKER_SECTION_HEADING_ONE("\\s1"),
    MARKER_SECTION_HEADING_TWO("\\s2"),
    MARKER_SECTION_HEADING_THREE("\\s3"),
    MARKER_SECTION_HEADING_FOUR("\\s4"),
    MARKER_CHUNK("\\s5")
}