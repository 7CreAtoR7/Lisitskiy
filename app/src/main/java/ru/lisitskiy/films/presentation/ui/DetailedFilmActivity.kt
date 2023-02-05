package ru.lisitskiy.films.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.lisitskiy.films.R
import ru.lisitskiy.films.presentation.fragments.DetailedInfoFilmFragment

class DetailedFilmActivity : AppCompatActivity() {

    private var filmID = UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_film)
        parseIntent()

        if (savedInstanceState == null) {
            // значит активность не пересоздавалась (создаем впервые, + фрагмент)
            // чтобы не создавать фрагмент при смене конфигурации, сис-ма его создаст сама
            // при повороте система сама создаст фрагмент
            launchRightMode(filmID)
        }
    }

    private fun launchRightMode(filmId: Long) {
        val fragment = DetailedInfoFilmFragment.newInstanceFilmItem(filmId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_item_container, fragment) // activity_detailed_film.xml
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_FILM_ITEM_ID)) {
            throw RuntimeException("Param EXTRA_FILM_ITEM_ID mode is absent!")
        }
        val filmId = intent.getLongExtra(EXTRA_FILM_ITEM_ID, 0L)
        filmID = filmId
    }

    companion object {

        private const val EXTRA_FILM_ITEM_ID = "extra_film_item_id"
        private const val UNKNOWN = -1L

        fun newIntentFilmItem(context: Context, filmId: Long?): Intent {
            val intent = Intent(context, DetailedFilmActivity::class.java)
            intent.putExtra(EXTRA_FILM_ITEM_ID, filmId)
            return intent
        }
    }
}