package util

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

        return "'" + param.toString().replace("'", "''") + "'"
    }
}