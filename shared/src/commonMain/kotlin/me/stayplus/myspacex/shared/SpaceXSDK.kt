package me.stayplus.myspacex.shared

import me.stayplus.myspacex.shared.cache.Database
import me.stayplus.myspacex.shared.cache.DatabaseDriverFactory
import me.stayplus.myspacex.shared.entity.RocketLaunch
import me.stayplus.myspacex.shared.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload)
            cachedLaunches
        else
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
    }
}