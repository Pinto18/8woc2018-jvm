package persistence.repo

import data.Language
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.sqlite.SQLiteDataSource
import persistence.data.LanguageStore
import persistence.mapping.LanguageMapper
import persistence.tables.daos.LanguageentityDao
import persistence.tables.pojos.Userentity
import java.io.File
import java.util.*

class LanguageRepoTest {
    private lateinit var languageRepo: LanguageRepo

    @Before
    fun setup(){
        val dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:test.db"

        val sqlSchema = File("src\\main\\resources\\databaseInit.sql")
        val config = DSL.using(dataSource.connection, SQLDialect.SQLITE).configuration()
        val sb = StringBuilder()
        sqlSchema.forEachLine {
            sb.append(it)
            if(it.contains(';')){
                config.dsl().fetch(sb.toString())
                sb.delete(0, sb.length)
            }
        }
        languageRepo = LanguageRepo(LanguageentityDao(config), LanguageMapper())
    }

    @Test
    fun insertAndRetrieveByIdTest(){
        LanguageStore.languages.forEach {
            it.id = 0
            it.id = languageRepo.insert(it).blockingFirst()
            Assert.assertEquals(it,languageRepo.getById(it.id).blockingFirst())
        }
    }

    @Test
    fun retrieveAllTest(){
        LanguageStore.languages.forEach {
            it.id = 0
            it.id = languageRepo.insert(it).blockingFirst()
        }
        Assert.assertEquals(LanguageStore.languages, languageRepo.getAll().blockingFirst())
    }

    @Test
    fun retrieveSourceLanguagesTest(){
        LanguageStore.languages.forEach {
            it.id = 0
            it.id = languageRepo.insert(it).blockingFirst()
        }
        Assert.assertEquals(LanguageStore.languages.filter {
            it.canBeSource
        }, languageRepo.getSourceLanguages().blockingFirst())
    }

    @Test
    fun updateTest(){
        LanguageStore.languages.forEach {
            // insert the original language
            it.id = languageRepo.insert(it).blockingFirst()

            // create the updated version of the language
            val updatedLanguage = Language(
                    name = "Khoisan",
                    anglicizedName = "Khoisan",
                    canBeSource = false,
                    slug = "khi"
            )
            updatedLanguage.id = it.id

            // try to update the language in the repo
            languageRepo.update(updatedLanguage)
            val actual = languageRepo.getById(updatedLanguage.id).blockingFirst()
            Assert.assertEquals(it, actual)
            // roll back the tests for the next case
            languageRepo.update(it)
        }
    }
}