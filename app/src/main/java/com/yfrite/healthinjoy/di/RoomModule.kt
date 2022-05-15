package com.yfrite.healthinjoy.di

import android.content.Context
import androidx.room.Room
import com.yfrite.healthinjoy.data.eaten.EatenDatabase
import com.yfrite.healthinjoy.data.messages.MessagesDatabase
import com.yfrite.healthinjoy.data.notifications.NotificationsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideEatenDao(database: EatenDatabase) = database.eatenDao()

    @Provides
    @Singleton
    fun provideEatenDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        EatenDatabase::class.java,
        "eaten_database"
    ).build()


    @Provides
    fun provideMessagesDao(database: MessagesDatabase) = database.messagesDao()

    @Provides
    @Singleton
    fun provideMessagesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        MessagesDatabase::class.java,
        "messages_database"
    ).build()

    @Provides
    fun provideNotificationsDao(database: NotificationsDatabase) = database.notificationsDao()

    @Provides
    @Singleton
    fun provideNotificationsDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        NotificationsDatabase::class.java,
        "notifications_database"
    ).build()
}