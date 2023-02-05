package ru.lisitskiy.films.presentation.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lisitskiy.films.R
import ru.lisitskiy.films.databinding.FilmListActivityBinding
import ru.lisitskiy.films.presentation.viewmodels.FilmViewModel
import ru.lisitskiy.films.presentation.adapter.AdapterFilms
import ru.lisitskiy.films.presentation.fragments.DetailedInfoFilmFragment
import ru.lisitskiy.films.presentation.util.Constants.Companion.QUERY_PAGE_SIZE


class FilmsListActivity : AppCompatActivity() {

    private lateinit var binding: FilmListActivityBinding
    private lateinit var viewModel: FilmViewModel


    private lateinit var adapterFilms: AdapterFilms

    var isScrolling = false
    var isLoading = false
    var isLastPage = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilmListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        markButtonDisable()

        viewModel = ViewModelProvider(this).get(FilmViewModel::class.java)

        if (savedInstanceState == null)
            viewModel.getDataFromApi(viewModel.PAGE_PAGINATION++)

        viewModel.filmList.observe(this) {
            adapterFilms.submitList(it)
        }


        viewModel.isInternetConnection.observe(this) {
            if (!it) {
                Toast.makeText(
                    this,
                    "Произошла ошибка при загрузке данных, проверьте подключение к сети, т.к. данные о фильмах могут быть устаревшими",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.isError.observe(this) {
            if (it) {
                Toast.makeText(
                    this,
                    "Произошла ошибка, перезапустите приложение",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    fun markButtonDisable() {
        binding.popularBtn.isEnabled = false
        binding.favouriteBtn.isEnabled = true
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    // для пагинации recyclerview
    var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getDataFromApi(viewModel.PAGE_PAGINATION++) // передавать страницу
                if (viewModel.PAGE_PAGINATION > viewModel.MAX_PAGE_COUNT)
                    isLastPage = true
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvFilmList) { // настраиваем recycler и устанавливаем адаптер
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterFilms = AdapterFilms()
            adapter = adapterFilms
            addOnScrollListener(this@FilmsListActivity.scrollListener)

            recycledViewPool.setMaxRecycledViews(
                AdapterFilms.VIEWTYPE_WITHOUT_STAR,
                AdapterFilms.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                AdapterFilms.VIEWTYPE_STAR,
                AdapterFilms.MAX_POOL_SIZE
            )

        }

        // установка слушателей
        setupClickListener()
        setupLongClickListener()
        setUpButtonsClickListeners()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.filmItemContainer == null // в landscape filmItemContainer = второй экран
    }

    private fun launchLandScapeFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack() // удалит старый фрагмент из стека перед добавлением нового
        supportFragmentManager.beginTransaction()
            .replace(R.id.film_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupClickListener() {
        adapterFilms.onShopItemClickListener = {
            if (viewModel.hasInternetConnection()) {
                if (isOnePaneMode()) {
                    val intent = DetailedFilmActivity.newIntentFilmItem(this, it.filmID)
                    startActivity(intent)
                } else {
                    // альбомная ориентация
                    launchLandScapeFragment(DetailedInfoFilmFragment.newInstanceFilmItem(it.filmID))
                }
            } else {
                Toast.makeText(
                    this,
                    "Произошла ошибка при загрузке данных, проверьте подключение к сети, т.к. данные о фильмах могут быть устаревшими",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun setUpButtonsClickListeners() {
        binding.favouriteBtn.setOnClickListener {
            val intent = FavouriteActivity.newIntentFavourite(this)
            startActivity(intent)
        }
    }


    private fun setupLongClickListener() {
        adapterFilms.onShopItemLongClickListener = {
            Log.e("FilmListActivity", "работа с конекртным фильмом: $it")

            if (!it.favourite) {
                // тостик, что фильм добавлен в избранное (обновляем статус в бд)
                Toast.makeText(this, "Фильм ${it.nameRu} добавлен в избранное!", Toast.LENGTH_SHORT)
                    .show()
                viewModel.changeEnableState(it)
            } else {
                // тостик, что фильм удалён из избранного (обновляем статус в бд)
                Toast.makeText(this, "Фильм ${it.nameRu} удалён из избранного!", Toast.LENGTH_SHORT)
                    .show()
                viewModel.changeEnableState(it)
            }
        }
    }

}