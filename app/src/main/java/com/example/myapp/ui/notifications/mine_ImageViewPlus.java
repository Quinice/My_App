package com.example.myapp.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 * 带边框的圆形图片
 * Created by LICHENGLONG on 2017-10-09.
 */

@SuppressLint("AppCompatCustomView")
public class mine_ImageViewPlus extends ImageView{
    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);//
    private Bitmap mRawBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();
    private float mBorderWidth = dip2px();
    private int mBorderColor = 0xFFFFFF;//外边框的颜色

    public mine_ImageViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap rawBitmap = getBitmap(getDrawable());
        if (rawBitmap != null){
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            int viewMinSize = Math.min(viewWidth, viewHeight);
            if (mShader == null || !rawBitmap.equals(mRawBitmap)){
                mRawBitmap = rawBitmap;
                mShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            }
            mMatrix.setScale(((float) viewMinSize - mBorderWidth * 2) / rawBitmap.getWidth(), ((float) viewMinSize - mBorderWidth * 2) / rawBitmap.getHeight());
            mShader.setLocalMatrix(mMatrix);
            mPaintBitmap.setShader(mShader);
            mPaintBorder.setStyle(Paint.Style.STROKE);
            mPaintBorder.setStrokeWidth(mBorderWidth / 3.0f);//外边框的大小
            mPaintBorder.setColor(mBorderColor);//添加外边框
            float radius = viewMinSize / 2.0f;
            canvas.drawCircle(radius, radius, radius - mBorderWidth / 2.0f, mPaintBorder);
            canvas.translate(mBorderWidth, mBorderWidth);
            canvas.drawCircle(radius - mBorderWidth, radius - mBorderWidth, radius, mPaintBitmap);
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getBitmap(Drawable drawable){
        if (drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable){
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable)drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(0, 0, 0, 0);
            return bitmap;
        } else {
            return null;
        }
    }

    private int dip2px() {
        float scale = getResources().getDisplayMetrics().density;
        return (int)(1 * scale);
    }
}