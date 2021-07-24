package com.bemo.withingspicture

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.selectable_picture.view.*


class ImageListAdapter(
    private val context: Context,
    private val imageList: List<String>,
    private val updateValidateButton: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectedImages: ArrayList<String> = arrayListOf()

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
            .error(ColorDrawable(Color.TRANSPARENT))
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(holder.itemView.selectableImageView)
        holder.itemView.imageContainer.setOnClickListener {
            if (holder.itemView.selectedIconImageView.isVisible) {
                selectedImages.remove(imageList[position])
                holder.itemView.selectedIconImageView.visibility = GONE
            } else {
                selectedImages.add(imageList[position])
                holder.itemView.selectedIconImageView.visibility = VISIBLE
            }
            updateValidateButton.invoke(selectedImages.size)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun getSelectedImages() = selectedImages

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.imageContainer
    }
}