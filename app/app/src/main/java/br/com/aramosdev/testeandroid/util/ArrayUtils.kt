@file:JvmName("ArrayUtils")

package br.com.aramosdev.testeandroid.util

import br.com.aramosdev.testeandroid.model.movie.Movie

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

fun getMoviesVoteAverage(products: List<Movie>, voteAverage: Int): List<Movie> =
        products.filter { it.voteAverage >= voteAverage }