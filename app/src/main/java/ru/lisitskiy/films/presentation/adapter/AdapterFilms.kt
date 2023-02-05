package ru.lisitskiy.films.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import ru.lisitskiy.films.R
import ru.lisitskiy.films.databinding.CardWithStarBinding
import ru.lisitskiy.films.databinding.CardWithoutStarBinding
import ru.lisitskiy.films.domain.model.Film

class AdapterFilms : ListAdapter<Film, FilmViewHolder>(FilmDiffCallback()) {

    var onShopItemLongClickListener: ((Film) -> Unit)? = null
    var onShopItemClickListener: ((Film) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layout = when (viewType) {
            // устанавливаем макет для карточки как избранный фильм или обычный
            VIEWTYPE_STAR -> R.layout.card_with_star
            VIEWTYPE_WITHOUT_STAR -> R.layout.card_without_star
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return FilmViewHolder(binding) // ViewHolder принимает binding в конструкторе
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        val binding = holder.binding

        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(film)
            true
        }

        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(film)

        }

        when (binding) {
            is CardWithStarBinding -> {
                Glide.with(holder.itemView.context).load(film.posterURL).into(binding.moviePoster)
                val genre = film.genres?.map { it.genre.replaceFirstChar(Char::titlecase) }
                    ?.get(FIRST_GENRE)
                val year = film.year ?: YEAR.toString()

                binding.movieReleaseDate.text = String.format("%s (%s)", genre, year)
                binding.movieTitle.text = film.nameRu

            }
            is CardWithoutStarBinding -> {
                Glide.with(holder.itemView.context).load(film.posterURL).into(binding.moviePoster)
                val genre = film.genres?.map { it.genre.replaceFirstChar(Char::titlecase) }
                    ?.get(FIRST_GENRE)
                val year = film.year ?: YEAR.toString()

                binding.movieReleaseDate.text = String.format("%s (%s)", genre, year)
                binding.movieTitle.text = film.nameRu

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.favourite) {
            VIEWTYPE_STAR
        } else {
            VIEWTYPE_WITHOUT_STAR
        }
    }

    companion object {
        const val FIRST_GENRE = 0

        const val VIEWTYPE_STAR = 100
        const val VIEWTYPE_WITHOUT_STAR = 101

        const val MAX_POOL_SIZE = 15

        const val YEAR = 2022
    }
}
