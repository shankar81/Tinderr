package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yuyakaido.android.cardstackview.*

class SwipeFragment : Fragment() {

    private lateinit var cardStackView: CardStackView
    private lateinit var layoutManager: CardStackLayoutManager

    private lateinit var rewind: ImageView
    private lateinit var swipeNope: ImageView
    private lateinit var swipeSuper: ImageView
    private lateinit var swipeLike: ImageView

    private val centerCrop = CenterCrop()
    private val roundedCorners = RoundedCorners(12)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe, container, false)

        setUpLayoutManager()

        rewind = view.findViewById(R.id.rewind)
        swipeNope = view.findViewById(R.id.swipeNope)
        swipeSuper = view.findViewById(R.id.swipeSuper)
        swipeLike = view.findViewById(R.id.swipeLike)

        cardStackView = view.findViewById(R.id.cardStackView)
        cardStackView.layoutManager = layoutManager
        cardStackView.adapter = CardStackAdapter()

        return view
    }

    override fun onStart() {
        super.onStart()
        swipeLike.setOnClickListener {
            cardStackView.swipe()
        }
        swipeNope.setOnClickListener {
            cardStackView.swipe()
        }
        rewind.setOnClickListener {
            cardStackView.rewind()
        }
    }

    private fun setUpLayoutManager() {
        layoutManager = CardStackLayoutManager(requireContext())

        layoutManager.setTranslationInterval(4.0f)
        layoutManager.setStackFrom(StackFrom.Bottom)
        layoutManager.setVisibleCount(3)
        layoutManager.setMaxDegree(85.0f)
        layoutManager.setOverlayInterpolator(DecelerateInterpolator())
    }

    private inner class CardStackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind() {
            Glide.with(requireContext())
                .asBitmap()
                .transform(centerCrop, roundedCorners)
                .load(R.drawable.sample)
                .into(imageView)
        }

    }

    private inner class CardStackAdapter :
        RecyclerView.Adapter<CardStackViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CardStackViewHolder {
            return CardStackViewHolder(
                layoutInflater.inflate(
                    R.layout.swiper_list_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: CardStackViewHolder, position: Int) {
            holder.bind()
        }

        override fun getItemCount() = 10
    }
}