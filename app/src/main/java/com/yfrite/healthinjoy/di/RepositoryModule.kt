package com.yfrite.healthinjoy.di

import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.data.eaten.EatenRepositoryImpl
import com.yfrite.healthinjoy.data.messages.MessagesRepository
import com.yfrite.healthinjoy.data.messages.MessagesRepositoryImpl
import com.yfrite.healthinjoy.data.notifications.NotificationsRepository
import com.yfrite.healthinjoy.data.notifications.NotificationsRepositoryImpl
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideEatenRepository(repository: EatenRepositoryImpl): EatenRepository

    @Binds
    fun provideMessagesRepository(repository: MessagesRepositoryImpl): MessagesRepository

    @Binds
    fun provideNotificationsRepository(repository: NotificationsRepositoryImpl): NotificationsRepository
}