package app.ui

import api.Door43Retrieval
import api.model.Door43Mapper
import data.model.Language
import data.model.User
import data.model.UserPreferences
import io.reactivex.Observable
import persistence.injection.DaggerDatabaseComponent
import persistence.repo.UserRepo

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        val database = DaggerDatabaseComponent.builder().build().inject()

        // already inserted
        /*
        val door43Languages = Door43Retrieval()
                .getLanguages()
        door43Languages.map {
            it.map { Door43Mapper().mapToLanguage(it) }
        }.flatMap {
            Observable.fromIterable(it)
        }.subscribe {
            println(it)
            database.getLanguageDao().insert(it).blockingFirst()
        }
        */
        println("Loading languages from database...")
        database
                .getLanguageDao()
                .getAll()
                .doOnNext {
                    println("${it.size} languages in the database")
                    val gatewayLanguages = it.filter { it.isGateway }
                    val targetLanguages = it.filter { !it.isGateway }
                    println("    ${gatewayLanguages.size} gateway")
                    println("    ${targetLanguages.size} other")

                    val english = gatewayLanguages.filter { it.slug == "en" }.first()
                    val chinese = gatewayLanguages.filter { it.slug == "zh" }.first()

                    val sharkBay = targetLanguages.filter { it.slug == "ssv" }.first()
                    val sengseng = targetLanguages.filter { it.slug == "ssz" }.first()

                    val french = gatewayLanguages.filter { it.slug == "fr" }.first()
                    val malimba = targetLanguages.filter { it.slug == "mzd" }.first()

                    val glenn = User(
                            id = 0,
                            audioHash = "ae84205efd",
                            audioPath = "/Users/matthew/8woc2018/audio.wav",
                            sourceLanguages = listOf(english, chinese),
                            targetLanguages = listOf(sharkBay, sengseng),
                            userPreferences = UserPreferences(
                                    id = 0,
                                    sourceLanguage = english,
                                    targetLanguage = sharkBay
                            )
                    )
                    println("\n\nCreating user...")
                    printUser(glenn)
                    database.getUserDao().insert(glenn).blockingFirst()

                    val userDao = database.getUserDao() as UserRepo
                    var retrievedGlenn = userDao.getByHash(glenn.audioHash).blockingFirst()

                    println("\n\nAdding French as a source language...")
                    userDao.addLanguage(retrievedGlenn, french, true).blockingAwait()
                    println("Adding Malimba as a target language...")
                    userDao.addLanguage(retrievedGlenn, malimba, false).blockingAwait()
                    retrievedGlenn = userDao.getByHash(glenn.audioHash).blockingFirst()
                    printUser(retrievedGlenn, false)

                    println("\n\nSetting French as the default source language...")
                    userDao.setLanguagePreference(retrievedGlenn, french, true).blockingAwait()
                    println("Setting Sengseng as the default target language...")
                    userDao.setLanguagePreference(retrievedGlenn, sengseng, false).blockingAwait()
                    retrievedGlenn = userDao.getByHash(glenn.audioHash).blockingFirst()
                    printUser(retrievedGlenn, false)

                }.blockingFirst()
    }

    fun printUser(user: User, verbose: Boolean = true) {
        println("USER")
        println("    Audio Hash: ${user.audioHash}")
        println("    Audio Path: ${user.audioPath}")
        println("---- Source Languages ----")
        for (source in user.sourceLanguages) {
            if (verbose) printLanguage(source) else println("    ${source.anglicizedName}")
        }
        println("---- Target Languages ----")
        for (target in user.targetLanguages) {
            if (verbose) printLanguage(target) else println("    ${target.anglicizedName}")
        }
        println("--------------------------")
        println("    Default Source: ${user.userPreferences.sourceLanguage.anglicizedName}")
        println("    Default Target: ${user.userPreferences.targetLanguage.anglicizedName}")

    }

    fun printLanguage(language: Language) {
        println("LANGUAGE")
        println("    Slug: ${language.slug}")
        println("    Name: ${language.name}")
        println("    Anglicized Name: ${language.anglicizedName}")
        println("    Gateway?: ${ if (language.isGateway) "YES" else "NO" }")

    }
}
