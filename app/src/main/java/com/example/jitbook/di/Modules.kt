package com.example.jitbook.di

import com.example.jitbook.book.data.network.KtorRemoteBookDataSource
import com.example.jitbook.book.data.network.RemoteBookDataSource
import com.example.jitbook.book.data.repository.DefaultBookRepository

import com.example.jitbook.book.domain.BookRepository
import com.example.jitbook.book.theme.BookDetailState
import com.example.jitbook.book.theme.viewmodel.AppThemeViewModel
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import com.example.jitbook.book.theme.viewmodel.BookDetailViewModel
import com.example.jitbook.book.theme.viewmodel.BookListViewModel
import com.example.jitbook.book.theme.viewmodel.FavoriteViewModel
import com.example.jitbook.book.theme.viewmodel.SelectedBookViewModel
import com.example.jitbook.book.theme.viewmodel.SubjectBooksViewModel
import com.example.jitbook.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val sharedModule = module {
    single<HttpClientEngine> { OkHttp.create() }

    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()
    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::SubjectBooksViewModel)
    viewModelOf(::AppThemeViewModel)
    viewModelOf(::FavoriteViewModel)

}