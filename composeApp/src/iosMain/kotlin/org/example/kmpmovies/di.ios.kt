package org.example.kmpmovies

import org.example.kmpmovies.data.IosRegionDataSource
import org.example.kmpmovies.data.RegionDataSource
import org.example.kmpmovies.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocationManager

actual val nativeModule = module {
    single { getDatabaseBuilder() }
    factoryOf(::IosRegionDataSource) bind RegionDataSource::class
    factoryOf(::CLLocationManager)
    factoryOf(::CLGeocoder)
}