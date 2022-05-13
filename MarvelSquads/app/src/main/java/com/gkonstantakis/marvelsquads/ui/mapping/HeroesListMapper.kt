package com.gkonstantakis.marvelsquads.ui.mapping

import android.util.Log
import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.ui.list_models.HeroesListItem

class HeroesListMapper {

    fun mapFromEntity(entity: Hero): HeroesListItem {
        return HeroesListItem(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = entity.imageUrl,
        )
    }

    fun mapToEntity(entity: HeroesListItem): Hero {
        return Hero(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = entity.imageUrl,
        )
    }

    fun mapFromEntityList(entities: List<Hero>): List<HeroesListItem>{
        return entities.map {
            mapFromEntity(it) }
    }
}