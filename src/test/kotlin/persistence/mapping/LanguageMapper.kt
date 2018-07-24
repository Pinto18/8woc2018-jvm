package persistence.mapping

import data.Language
import org.junit.Assert
import org.junit.Test
import persistence.tables.pojos.Languageentity
import java.util.*

class LanguageMapperTest {

    val LANGUAGE_TABLE = listOf(
            mapOf(
                    "name" to "Español",
                    "slug" to "esp",
                    "anglicizedName" to "Spanish",
                    "canBeSource" to "true"
            ),
            mapOf(
                    "name" to "हिन्दी",
                    "slug" to "hin",
                    "anglicizedName" to "Hindi",
                    "canBeSource" to "true"
            )
    )

    @Test
    fun testIfLanguageEntityCorrectlyMappedToLanguage() {
        for (testCase in LANGUAGE_TABLE) {
            val input = Languageentity(
                    Random().nextInt(),
                    testCase["name"],
                    testCase["slug"],
                    0,
                    testCase["anglicizedName"]
            )

            val expected = Language(
                    id = input.id,
                    slug = input.slug,
                    name = input.name,
                    anglicizedName = input.anglicizedname,
                    canBeSource = input.canbesource == 0
            )

            val result = LanguageMapper().mapFromEntity(input)
            try {
                Assert.assertEquals(expected, result)
            } catch (e: AssertionError) {
                println("Input: ${input.name}")
                println("Result: ${result.name}")
                throw e
            }
        }
    }

    @Test
    fun testIfLanguageCorrectlyMappedToLanguageEntity() {
        for (testCase in LANGUAGE_TABLE) {
            val input = Language(
                    id = Random().nextInt(),
                    slug = testCase["slug"].orEmpty(),
                    name = testCase["name"].orEmpty(),
                    anglicizedName = testCase["anglicizedName"].orEmpty(),
                    canBeSource = testCase["canBeSource"] == "true"
            )

            val expected = Languageentity(
                    input.id,
                    input.slug,
                    input.name,
                    if(input.canBeSource) 0 else 1,
                    input.anglicizedName
            )
            val result = LanguageMapper().mapToEntity(input)
            try {
                Assert.assertEquals(expected, result)
            } catch (e: AssertionError) {
                println("Input: ${input.name}")
                println("Result: ${result.name}")
                throw e
            }
        }
    }

}