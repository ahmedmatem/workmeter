package com.ahmedmatem.android.workmeter.data.site.local

import androidx.lifecycle.LiveData
import org.koin.java.KoinJavaComponent.inject

class SiteLocalDataSource {
    private val siteDao: SiteDao by inject(SiteDao::class.java)

    fun getSites(): LiveData<List<Site>>{
        return siteDao.getSites()
    }

    fun save(vararg sites: Site){
        siteDao.insert(*sites)
    }
}