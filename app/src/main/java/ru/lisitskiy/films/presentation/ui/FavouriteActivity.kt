package ru.lisitskiy.films.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.lisitskiy.films.R
import ru.lisitskiy.films.databinding.ActivityFavouriteBinding
import ru.lisitskiy.films.presentation.viewmodels.FavouriteActivityViewModel
import ru.lisitskiy.films.presentation.adapter.AdapterFilms

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var viewModel: FavouriteActivityViewModel


    private lateinit var adapterFilms: AdapterFilms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setUpClickListeners()
        markButtonDisable()

        viewModel = ViewModelProvider(this).get(FavouriteActivityViewModel::class.java)

//        if (savedInstanceState == null)
//            viewModel.getDataFromApi(viewModel.PAGE_PAGINATION++)

        viewModel.favouriteFilms.observe(this) {
            adapterFilms.submitList(it)
        }

//        viewModel.responseFilms.observe(this) {
//            adapterFilms.submitList(it.toList())
//        }
//
//        viewModel.filmList.observe(this) {
//            adapterFilms.submitList(it)
//        }

//        if (savedInstanceState == null) {
//            // значит активность не пересоздавалась (создаем впервые, + фрагмент)
//            // чтобы не создавать фрагмент при смене конфигурации, сис-ма его создаст сама
//            // при повороте система сама создаст фрагмент
//            //launchRightMode()
//        }
    }

    fun markButtonDisable() {
        binding.popularBtn.isEnabled = true
        binding.favouriteBtn.isEnabled = false
    }

    fun setUpClickListeners() {
        binding.popularBtn.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvFilmList) { // настраиваем recycler и устанавливаем адаптер
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterFilms = AdapterFilms()
            adapter = adapterFilms

            recycledViewPool.setMaxRecycledViews(
                AdapterFilms.VIEWTYPE_WITHOUT_STAR,
                AdapterFilms.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                AdapterFilms.VIEWTYPE_STAR,
                AdapterFilms.MAX_POOL_SIZE
            )

        }
    }

//
//    private fun setUpButtonsClickListeners() {
//        binding.favouriteBtn.setOnClickListener {
//            val intent = FavouriteActivity.newIntentFavourite(this)
//            startActivity(intent)
//        }
//    }
//
//    private fun launchRightMode() {
//        val fragment = DetailedInfoFilmFragment.newInstanceFilmItem(filmId)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.film_item_container, fragment) // activity_detailed_film.xml
//            .commit()
//    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_FILM_ITEM_ID = "extra_film_item_id"
        private const val MODE_OPEN = "mode_open"
        private const val MODE_ADD = "mode_add"
        private const val UNKNOWN = -1L

        fun newIntentFavourite(context: Context): Intent {
            val intent = Intent(context, FavouriteActivity::class.java)
            return intent
        }
    }

}