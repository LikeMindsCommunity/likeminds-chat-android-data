package com.likeminds.likemindschat.di.search

import com.likeminds.likemindschat.search.SearchClient
import dagger.Subcomponent

@Subcomponent
interface SearchSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchSubComponent
    }

    fun inject(searchClient: SearchClient)
}