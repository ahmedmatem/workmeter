package com.ahmedmatem.android.workmeter.data.local.site

import androidx.lifecycle.LiveData
import com.ahmedmatem.android.workmeter.data.model.Site
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