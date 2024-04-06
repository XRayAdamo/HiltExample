package com.rayadams.hiltexample.di

import com.rayadams.hiltexample.repositories.ContactsRepository
import com.rayadams.hiltexample.repositories.ContactsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactsRepositoryModule {
    @Singleton
    @Provides
    fun provideContactsRepository(): ContactsRepository = ContactsRepositoryImpl()
}
