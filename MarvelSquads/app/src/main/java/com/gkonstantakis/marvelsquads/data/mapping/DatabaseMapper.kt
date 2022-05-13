package com.gkonstantakis.marvelsquads.data.mapping

import com.gkonstantakis.marvelsquads.data.database.entities.HeroCacheEntity
import com.gkonstantakis.marvelsquads.data.database.entities.HeroSquadEntity
import com.gkonstantakis.marvelsquads.data.model.Hero

class DatabaseMapper {
    fun mapFromHeroCacheEntity(entity: HeroCacheEntity): Hero {
        return Hero(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = entity.imageUrl
        )
    }

    fun mapToHeroCacheEntity(entity: Hero): HeroCacheEntity {
        return HeroCacheEntity(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = entity.imageUrl,
        )
    }

    fun mapToHeroSquadEntity(entity: Hero): HeroSquadEntity {
        return HeroSquadEntity(
            id = entity.id
        )
    }

    fun mapFromHeroCacheEntityEntityList(entities: List<HeroCacheEntity>): List<Hero> {
        return entities.map {
            mapFromHeroCacheEntity(it)
        }
    }
}