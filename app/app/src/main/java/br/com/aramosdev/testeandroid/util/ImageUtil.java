package br.com.aramosdev.testeandroid.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import br.com.aramosdev.testeandroid.R;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class ImageUtil {

    public static void loadImageInto(String path, ImageView imageView, Context context) {
        String image = context.getString(R.string.base_image) + path;
        DrawableTypeRequest load = Glide.with(context)
                .load(image);
        load.into(imageView);
    }
}
