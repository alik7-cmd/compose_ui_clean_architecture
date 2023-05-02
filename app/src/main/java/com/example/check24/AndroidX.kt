package com.example.check24

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction.actionId, direction.arguments)
}

fun ImageView.loadImageFromWeb(imageUrl : String){

    Glide.with(this.context).load(imageUrl).into(this)
}

fun Fragment.openWebPageWith(url : String){
    val uri: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}