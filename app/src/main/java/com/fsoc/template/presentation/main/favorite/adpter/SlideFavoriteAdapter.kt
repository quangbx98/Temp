package com.fsoc.template.presentation.main.favorite.adpter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fsoc.template.R
import kotlinx.android.synthetic.main.item_slide_favorite.view.*


class SlideFavoriteAdapter(private val context: Context) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    private val images = arrayOf(R.drawable.cheese_1, R.drawable.cheese_2, R.drawable.cheese_3)

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater!!.inflate(R.layout.item_slide_favorite, null)

        loadBackdrop(images[position], view.imageViewMain)
        val vp: ViewPager = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    private fun loadBackdrop(int: Int, imageView: ImageView) {
        Glide.with(imageView)
            .load(int)
            .apply(RequestOptions.centerCropTransform())
            .into(imageView)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp: ViewPager = container as ViewPager
        val view = `object` as View?
        vp.removeView(view)
    }

}