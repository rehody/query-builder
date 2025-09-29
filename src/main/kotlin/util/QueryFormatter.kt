package util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object QueryFormatter {
    fun getEscapedIdentifier(id: String): String {
        return "\"" + id.replace("\"", "\"\"") + "\""
    }

    fun getEscapedParameter(param: Any?): String {
        if (param == null) {
            return SqlKeyword.NULL
        }

        if (param is Number || param is Boolean) {
            return param.toString()
        }

        if (param is LocalDateTime) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return "'" + param.format(formatter) + "'"
        }

        if (param is Raw) {
            return param.value
        }


        return "'" + param.toString().replace("'", "''") + "'"
    }
}