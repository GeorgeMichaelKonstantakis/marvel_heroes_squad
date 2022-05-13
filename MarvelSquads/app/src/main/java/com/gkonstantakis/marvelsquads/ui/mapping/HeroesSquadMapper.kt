package com.gkonstantakis.marvelsquads.ui.mapping

import android.util.Log
import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.ui.list_models.HeroesListItem
import com.gkonstantakis.marvelsquads.ui.list_models.HeroesSquadItem

class HeroesSquadMapper {
    fun mapFromEntity(entity: Hero): HeroesSquadItem {
        return HeroesSquadItem(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = entity.imageUrl,
        )
    }

    fun mapToEntity(entity: HeroesSquadItem): Hero {
        return Hero(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = entity.imageUrl,
        )
    }

    fun mapFromEntityList(entities: List<Hero>): List<HeroesSquadItem> {
        return entities.map {
            mapFromEntity(it)
        }
    }
}