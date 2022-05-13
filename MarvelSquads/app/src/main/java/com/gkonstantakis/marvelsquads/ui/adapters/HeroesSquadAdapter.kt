package com.gkonstantakis.marvelsquads.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkonstantakis.marvelsquads.R
import com.gkonstantakis.marvelsquads.ui.intents.Intents
import com.gkonstantakis.marvelsquads.ui.list_models.HeroesSquadItem
import com.gkonstantakis.marvelsquads.ui.mapping.HeroesSquadMapper
import com.gkonstantakis.marvelsquads.ui.utils.Utils

class HeroesSquadAdapter(var squadHeroes: List<HeroesSquadItem>, context: Context) :
    RecyclerView.Adapter<HeroesSquadAdapter.HeroSquadViewHolder>() {

    inner class HeroSquadViewHolder(heroSquadView: View) : RecyclerView.ViewHolder(heroSquadView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeroesSquadAdapter.HeroSquadViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.heroes_squad_item, parent, false)
        return HeroSquadViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroesSquadAdapter.HeroSquadViewHolder, position: Int) {
        holder.itemView.apply {
            val heroItem = squadHeroes[position]
            val heroUrl = heroItem.imageUrl
            var heroAcceptedUrl = Utils().safeUrl(heroUrl)

            Glide.with(context).load(heroAcceptedUrl).apply(RequestOptions.circleCropTransform())
                .into(this.findViewById<ImageButton>(R.id.squad_hero_image));

            this.findViewById<ImageButton>(R.id.squad_hero_image).setOnClickListener {
                Intents().heroActivityIntent(
                    HeroesSquadMapper().mapToEntity(heroItem),
                    true, context
                )
            }

            this.findViewById<TextView>(R.id.squad_hero_title).text = heroItem.name
        }
    }

    override fun getItemCount(): Int {
        return squadHeroes.size
    }
}