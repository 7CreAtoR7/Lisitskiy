package ru.lisitskiy.films.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.lisitskiy.films.domain.model.Film

class FilmDiffCallback: DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.filmID == newItem.filmID
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}