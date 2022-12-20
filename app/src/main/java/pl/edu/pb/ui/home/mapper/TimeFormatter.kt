package pl.edu.pb.ui.home.mapper

import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal object TimeFormatter {
    private const val PATTERN_FORMAT = "dd/MM/yyyy-HH:mm:ss"

    internal val formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault())
}