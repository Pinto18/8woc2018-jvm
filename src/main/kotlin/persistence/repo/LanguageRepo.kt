package persistence.repo

import data.Language
import data.User
import data.dao.Dao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javafx.scene.control.Tab
import persistence.Tables
import persistence.mapping.LanguageMapper
import persistence.tables.daos.LanguageentityDao
import persistence.tables.pojos.Languageentity
//import javax.inject.Inject
import javax.security.auth.login.Configuration


class LanguageRepo /*@Inject*/ constructor(private val languageentityDao: LanguageentityDao, private val languageMapper: LanguageMapper): Dao<Language> {
    /**
     * given a language deletes the entry within the table
     */
    override fun delete(language: Language): Completable {
        return Completable.fromAction {
            languageentityDao.delete(languageMapper.mapToEntity(language))
        }.subscribeOn(Schedulers.io())
    }

    /**
     * gets all language entries and returns them as an observable language
     */
    override fun getAll(): Observable<List<Language>> {
        return Observable.create<List<Languageentity>> {
            it.onNext(languageentityDao.findAll())
        }.map {
            it.map { languageMapper.mapFromEntity(it) }
        }.subscribeOn(Schedulers.io())
    }

    /**
     *  given an id gets and return a language observable
     */
    override fun getById(id: Int): Observable<Language> {
        return Observable.create<Languageentity> {
            it.onNext(languageentityDao.fetchById(id).first())
        }.map { languageMapper.mapFromEntity(it) }.subscribeOn(Schedulers.io())
    }

    /**
     * given a language object inserts an entry
     * and returns the generated id as an observable
     */
    override fun insert(language: Language): Observable<Int> {
        return Observable.create<Int> {
            val sqlString = "SELECT id FROM LanguageEntity WHERE id =(SELECT MAX(id) FROM LanguageEntity)"
            languageentityDao.insert(languageMapper.mapToEntity(language))
            it.onNext(languageentityDao
                    .configuration()
                    .dsl()
                    .fetch(sqlString)
                    .first()[Tables.LANGUAGEENTITY.ID])
        }.subscribeOn(Schedulers.io())
    }

    /**
     * given a language updates an entry
     * and returns a completable
     */
    override fun update(language: Language): Completable {
        return Completable.fromAction {
            languageentityDao.update(languageMapper.mapToEntity(language))
        }.subscribeOn(Schedulers.io())
    }

    /**
     * returns all source languages
     * as an observable list of languages
     */
    fun getSourceLanguages(): Observable<List<Language>> {
        return Observable.create<List<Language>> {
            it.onNext(languageentityDao
                    .findAll()
                    .filter {
                        it.canbesource == 0
                    }.map {
                        languageMapper.mapFromEntity(it)
                    }
            )
        }.subscribeOn(Schedulers.io())
    }
}