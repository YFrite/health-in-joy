package com.yfrite.healthinjoy.di

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
class GlideModule {

    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestBuilder<Bitmap> = Glide.with(context)
        .asBitmap()
        .skipMemoryCache(true)
}