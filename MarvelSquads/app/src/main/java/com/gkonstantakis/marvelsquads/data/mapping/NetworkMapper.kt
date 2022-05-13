package com.gkonstantakis.marvelsquads.data.mapping

import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.data.network.entities.HeroDataEntity
import com.gkonstantakis.marvelsquads.data.network.entities.HeroesNetworkEntity

class NetworkMapper {

    fun mapFromEntity(entity: HeroDataEntity): Hero {
        return Hero(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = "${entity.thumbnail.path}.${entity.thumbnail.extension}"
        )
    }

    fun mapFromEntityList(entity: HeroesNetworkEntity): List<Hero> {
        return entity.data.results.map {
            mapFromEntity(it)
        }
    }
}