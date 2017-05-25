package newsdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import davidtgnewsproject.newsdemo.com.davidtgnewsproject.R;

public class PicassoWithImageUtils {

    public static void withImageView(Context context, String imageUrl, ImageView imageView) {
        if (imageView != null && context != null && imageUrl != null) {

            Picasso.with(context).load(imageUrl)
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        }

    }

    public static void withImageViewResize(Context context, String imageUrl, ImageView imageView, int width, int height) {
        if (imageView != null && context != null && imageUrl != null) {
            Picasso.with(context)
                    .load(imageUrl)
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .resize(width, height)
                    .centerCrop()
                    .into(imageView);
        }

    }

    public static void withImageViewCustomResize(Context context, String imageUrl, ImageView imageView, int width, int height) {
        if (imageView != null && context != null && imageUrl != null) {
            Picasso.with(context).load(imageUrl)
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .transform(new CropSquareTransformation()).into(imageView);
        }

    }

    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }


}
