package com.tsafundzic.e_autoskola.presentation

interface BasePresenter<in T> {

    fun setView(view: T)
}
