package cohen.dafna.weatherfinalexam.util.imageloader

import android.widget.ImageView

interface ImageLoader {
    fun load(imageResource: String, target: ImageView)
}