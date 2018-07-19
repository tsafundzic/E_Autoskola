package com.tsafundzic.e_autoskola

interface BasePresenter<in T> {

    fun setView(view: T)
}
