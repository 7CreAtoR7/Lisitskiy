package ru.lisitskiy.films.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.lisitskiy.films.data.network.ApiFactory
import ru.lisitskiy.films.databinding.FragmentDetailBinding
import ru.lisitskiy.films.presentation.adapter.AdapterFilms

class DetailedInfoFilmFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailBinding == null")

    private var filmItemId: Int = UNKNOWN


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun apiRequest() {
        ApiFactory.getApiService().getDetailInfo(filmItemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Glide.with(requireActivity()).load(it.posterURL).into(binding.moviePoster)
                    val genre = it.genres.map { it.genre.replaceFirstChar(Char::titlecase) }
                        .get(AdapterFilms.FIRST_GENRE)
                    val country = it.countries.map { it.country.replaceFirstChar(Char::titlecase) }
                        .get(AdapterFilms.FIRST_GENRE)

                    binding.movieTitle.text = it.nameRu
                    binding.description.text = it.description
                    binding.countries.text = country
                    binding.genres.text = genre
                },
                { Log.d("DetailedInfoFilmFragment", "Failure: ${it.message}") }
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiRequest()
    }

    private fun parseParams() {
        val args = requireArguments() // переданные аргументы, приложение крашнется, если их нет
        if (!args.containsKey(EXTRA_FILM_ITEM_ID)) {
            throw RuntimeException("Param EXTRA_FILM_ITEM_ID mode is absent!")
        }
        val id = args.getLong(EXTRA_FILM_ITEM_ID, -1L)
        filmItemId = id.toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        const val UNKNOWN = -1
        private const val EXTRA_FILM_ITEM_ID = "extra_film_item_id"

        fun newInstanceFilmItem(filmId: Long): DetailedInfoFilmFragment {
            return DetailedInfoFilmFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_FILM_ITEM_ID, filmId)
                }
            }
        }
    }

}