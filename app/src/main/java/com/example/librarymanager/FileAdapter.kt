package com.example.librarymanager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FileAdapter(
    private val context: Context,
    private val files: List<FileItem>,
    private val itemClickListener: (FileItem) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        fun bind(fileItem: FileItem) {
            nameTextView.text = fileItem.name
            descriptionTextView.text = fileItem.description
            priceTextView.text = fileItem.price

            // Load image using Glide
            Glide.with(context).load(fileItem.imageUrl).into(imageView)
               /* .placeholder(R.drawable.placeholder_image) // Optional: placeholder while loading
                .error(R.drawable.error_image) // Optional: error image if loading fails*/


            itemView.setOnClickListener {
                // Pass the clicked item to the click listener
                itemClickListener(fileItem)

                // Intent to navigate to BookDetailsActivity
                val intent = Intent(context, BookDetailsActivity::class.java).apply {
                    putExtra("title", fileItem.name)
                    putExtra("description", fileItem.description)
                    putExtra("price", fileItem.price)
                    putExtra("imageUrl", fileItem.imageUrl)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = files[position]
        holder.bind(fileItem)
    }

    override fun getItemCount(): Int {
        return files.size
    }
}
