package com.gkonstantakis.marvelsquads.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkonstantakis.marvelsquads.R
import com.gkonstantakis.marvelsquads.data.repository.MainRepository
import com.gkonstantakis.marvelsquads.ui.intents.Intents
import com.gkonstantakis.marvelsquads.ui.list_models.HeroesListItem
import com.gkonstantakis.marvelsquads.ui.mapping.HeroesListMapper
import com.gkonstantakis.marvelsquads.ui.utils.Utils
import kotlinx.coroutines.runBlocking

class HeroesListAdapter(var listHeroes: List<HeroesListItem>,val mainRepository: MainRepository) :
    RecyclerView.Adapter<HeroesListAdapter.HeroListViewHolder>() {

    inner class HeroListViewHolder(heroListView: View) : RecyclerView.ViewHolder(heroListView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.heroes_list_item, parent, false)
        return HeroListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroListViewHolder, position: Int) {
        holder.itemView.apply {
            val heroItem = listHeroes[position]
            val heroUrl = heroItem.imageUrl
            var heroAcceptedUrl = Utils().safeUrl(heroUrl)

            Glide.with(context).load(heroAcceptedUrl).apply(RequestOptions.circleCropTransform())
                .into(this.findViewById<ImageButton>(R.id.list_hero_image));

            var isHeroInSquad = false
            runBlocking {
                isHeroInSquad = mainRepository.isHeroInSquad(heroItem.id)
            }

            this.findViewById<ImageButton>(R.id.list_hero_image).setOnClickListener {
                Intents().heroActivityIntent(
                    HeroesListMapper().mapToEntity(heroItem),
                    isHeroInSquad, context
                )
            }

            this.findViewById<TextView>(R.id.hero_title).text = heroItem.name
        }
    }

    override fun getItemCount(): Int {
        return listHeroes.size
    }
}