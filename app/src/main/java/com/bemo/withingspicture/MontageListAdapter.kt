package com.bemo.withingspicture

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.selectable_picture.view.*


class MontageListAdapter(
    private val context: Context,
    private val imageList: ArrayList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.selectable_picture, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position])
            .fitCenter()
            .placeholder(ColorDrawable(Color.TRANSPARENT))
            .transition(DrawableTransitionOptions.withCrossFade(7200))
            .error(ColorDrawable(Color.TRANSPARENT))
            .into(holder.itemView.selectableImageView)
        val anim = RotateAnimation(0f, 350f, 60f, 60f)
        anim.interpolator = LinearInterpolator()
        anim.repeatCount = Animation.INFINITE
        anim.duration = 700
        holder.itemView.selectableImageView.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.imageContainer
    }
}