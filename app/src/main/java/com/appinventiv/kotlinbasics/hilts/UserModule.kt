package com.appinventiv.kotlinbasics.hilts

import com.appinventiv.kotlinbasics.ApiInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class UserModule {

    @Provides
    @ActivityScoped
    @Named("firebase")
    fun provideUserRepository(): UserRepository {
        return FirebaseRepository()
    }

    @Provides
    @FirebaseQualifier
    fun provideSQLRepository(sqlRepository: SQLRepository): UserRepository {
        return sqlRepository
    }
/*    @Binds
    abstract fun bindUserRepository(sqlRepository: SQLRepository): UserRepository*/

    @Provides
    @ActivityScoped
    fun provideAnalyticsService(
        // Potential dependencies of this type
    ): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://example.com")
            .build()
            .create(ApiInterface::class.java)
    }

}