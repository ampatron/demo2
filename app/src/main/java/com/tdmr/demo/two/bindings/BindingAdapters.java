package com.tdmr.demo.two.bindings;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tdmr.demo.two.R;

/**
 * Created by abigail on 6/6/2016.
 */
public class BindingAdapters {
    @BindingAdapter("bind:photoUri")
    public static void setPhotoUri(ImageView view, Uri uri) {
        Glide.with(view.getContext()).load(uri).placeholder(R.drawable.ic_person_outline_black_24dp).into(view);
    }
}
