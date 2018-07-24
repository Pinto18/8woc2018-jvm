package persistence.mapping

import data.Language
import data.mapping.Mapper
import persistence.tables.pojos.Languageentity

//import javax.inject.Inject

class LanguageMapper /*@Inject*/ constructor(): Mapper<Languageentity, Language> {
    override fun mapFromEntity(type: Languageentity): Language {
        return Language(
                type.id,
                type.slug,
                type.name,
                type.canbesource == 0,
                type.anglicizedname
        )
    }

    override fun mapToEntity(type: Language): Languageentity {
        val languageEntity = Languageentity()
        if (type.id  != 0){
            languageEntity.id = type.id
        }
        languageEntity.slug = type.slug
        languageEntity.name = type.name
        languageEntity.canbesource = if(type.canBeSource) 0 else 1
        languageEntity.anglicizedname = type.anglicizedName
        return languageEntity
    }

}