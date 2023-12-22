package hse.course.android_hse_course_lab3.utils

enum class Languages {

    Russian,
    English;

    companion object GetLanguageAbbreviation {

        fun getLanguageAbbreviation(language: String): String {
            return when (language) {
                English.name -> "en"
                Russian.name -> "ru"
                else -> return "en"
            }
        }

        fun getLanguageIndex(language: String): Int {
            return when (language) {
                English.name -> 0
                Russian.name -> 1
                else -> 0
            }
        }
    }
}