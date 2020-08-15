package ie.corktrainingcentre.canvasmenutest;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvasView extends View{
    //components
    private Paint mPaint;
    private Path mPath;
    private Canvas mExtraCanvas;
    public static Bitmap mExtraBitmap;

    private Bitmap largeIcon;
    private Notification.Builder notBuilder;
    //color
    private int mDrawColor;
    private int mBackgroundColor;
    private int c1;
    private int c2;
    private int c3;
    private int c4;
    private int c5;
    private int c6;
    private int c7;
    private int BLACK;
    private int WHITE;
    private int backcolo;

    //pen size
    private int ps = 5;
    public int penColor;

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context);

        mBackgroundColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_orange, null);

        mDrawColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_yellow, null);

        c1 = ResourcesCompat.getColor(getResources(),
                R.color.c1, null);
        c2 = ResourcesCompat.getColor(getResources(),
                R.color.c2, null);
        c3 = ResourcesCompat.getColor(getResources(),
                R.color.c3, null);
        c4 = ResourcesCompat.getColor(getResources(),
                R.color.c4, null);
        c5 = ResourcesCompat.getColor(getResources(),
                R.color.c5, null);
        c6 = ResourcesCompat.getColor(getResources(),
                R.color.c6, null);
        c7 = ResourcesCompat.getColor(getResources(),
                R.color.c7, null);
        penColor = ResourcesCompat.getColor(getResources(),
                R.color.BLACK, null);
        backcolo = ResourcesCompat.getColor(getResources(),
                R.color.WHITE, null);

        // Holds the path we are currently drawing.
        mPath = new Path();
        // Set up the paint with which to draw.
        mPaint = new Paint();
        mPaint.setColor(penColor);
        // Smoothes out edges of what is drawn without affecting shape.
        mPaint.setAntiAlias(true);
        // Dithering affects how colors with higher-precision device
        // than the are down-sampled.
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE); // default: FILL
        mPaint.setStrokeJoin(Paint.Join.ROUND); // default: MITER
        mPaint.setStrokeCap(Paint.Cap.ROUND); // default: BUTT
        mPaint.setStrokeWidth(ps); // default: Hairline-width (really thin)
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight){
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        // create bit map
        mExtraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        //largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.red_arrow);

        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(backcolo);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(mExtraBitmap, 0,0, null);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
                case MotionEvent.ACTION_MOVE:
                    touchMove(x, y);
                    invalidate();
                    break;
                    case MotionEvent.ACTION_UP:
                        touchUp();
                        break;
                        default:
        }
        return true;
    }

    private void touchUp() {
        mPath.reset();
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            // Reset mX and mY to the last drawn point.
            mX = x;
            mY = y;
            // Save the path in the extra bitmap,
            // which we access through its canvas.
            mExtraCanvas.drawPath(mPath, mPaint);
        }
    }

    private void touchStart(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    public void draw(Bitmap bitmap) {
        mExtraCanvas.drawBitmap(bitmap, 0,0, null);
    }
}
