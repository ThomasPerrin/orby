package com.tomp.orby.utils.component

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.tomp.orby.R

class CountDrawable(context: Context) : Drawable() {

    private val mBadgePaint: Paint
    private val mTextPaint: Paint
    private val mTxtRect = Rect()

    private var mCount = ""
    private var mWillDraw: Boolean = false

    init {
        val mTextSize = context.resources.getDimension(R.dimen.badge_count_textsize)

        mBadgePaint = Paint()
        mBadgePaint.color = ContextCompat.getColor(context.applicationContext, R.color.purple_200)
        mBadgePaint.isAntiAlias = true
        mBadgePaint.style = Paint.Style.FILL

        mTextPaint = Paint()
        mTextPaint.color = ContextCompat.getColor(context.applicationContext, R.color.white)
        mTextPaint.typeface = Typeface.DEFAULT
        mTextPaint.textSize = mTextSize
        mTextPaint.isAntiAlias = true
        mTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {

        if (!mWillDraw) {
            return
        }
        val bounds = bounds
        val width = (bounds.right - bounds.left).toFloat()
        val height = (bounds.bottom - bounds.top).toFloat()

        // Position the badge in the top-right quadrant of the icon.

        /*Using Math.max rather than Math.min */

        val radius = Math.max(width, height) / 2 / 2
        val centerX = width - radius - 1f + 5
        val centerY = radius - 5
        if (mCount.length <= 2) {
            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, (radius + 5.5).toInt().toFloat(), mBadgePaint)
        } else {
            canvas.drawCircle(centerX, centerY, (radius + 6.5).toInt().toFloat(), mBadgePaint)
        }
        // Draw badge count text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length, mTxtRect)
        mTextPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        val textHeight = (mTxtRect.bottom - mTxtRect.top).toFloat()
        val textY = centerY + textHeight / 2f
        if (mCount.length > 2)
            canvas.drawText("99+", centerX, textY, mTextPaint)
        else
            canvas.drawText(mCount, centerX, textY, mTextPaint)
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    fun setVisibility(isVisible: Boolean) {
        mCount = if(isVisible) "1" else ""

        // Only draw a badge if there are notifications.
        mWillDraw = isVisible
        invalidateSelf()
    }

    fun setCount(count: String) {
        mCount = count

        // Only draw a badge if there are notifications.
        mWillDraw = !count.equals("0", ignoreCase = true)
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        // do nothing
    }

    override fun setColorFilter(cf: ColorFilter?) {
        // do nothing
    }

    override fun getOpacity(): Int =
        PixelFormat.UNKNOWN

}
