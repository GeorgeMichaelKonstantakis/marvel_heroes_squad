package com.gkonstantakis.marvelsquads.data.utils

sealed class DataState<out R> {

    data class SuccessNetwork<out T>(val data: T) : DataState<T>()

    data class SuccessDatabaseHeroList<out T>(val data: T) : DataState<T>()

    data class SuccessDatabaseSquadList<out T>(val data: T) : DataState<T>()

    data class SuccessHireOrFireHero<out T>(val data: T) : DataState<T>()

    data class Error(val exception: Exception) : DataState<Nothing>()

    object Loading : DataState<Nothing>()
}
