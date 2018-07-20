import data.Language
import data.User
import data.UserPreferences

//mock users data


val languages = listOf(
    Language(0, "ENG", "English", true, "eng"),
    Language(1, "MAN", "Mandarin", true, "man"),
    Language(2, "IND", "Indonesian", false, "ind"),
    Language(3, "FRA", "France", true, "fra"),
    Language(5, "ESP", "Espanol", true, "esp"),
    Language(4, "POR", "Portugese", false, "por"),
    Language(6, "DAN", "Danish", false, "dan"),
    Language(7, "EWE", "Ewe", false, "ewe"),
    Language(8, "SWI", "Swiss", false, "swi"),
    Language(9, "SHE", "Shelta", true, "she")
)

fun userCreators() {
    var sourceLanguages : MutableList<Language> = mutableListOf()
    var targetLanguages : MutableList<Language> = mutableListOf()
    sourceLanguages.addAll(languages)
    targetLanguages.addAll(languages)

    var users:MutableList<User> = mutableListOf()
    for (i in 0..9) {
        val randomNumber = Math.floor(Math.random() * 9_000_000_0000L).toLong() + 1_000_000_0000L
        var preferences = UserPreferences(preferredSourceLanguage = languages[i], preferredTargetLanguage = languages [i])
        users.add(User(i, "${randomNumber + i}", "Path$i", sourceLanguages, targetLanguages, preferences))
        println(users[i])
    }
}

