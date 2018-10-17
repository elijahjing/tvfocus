package com.example.administrator.myapplication;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FocusView extends View {
    public int outPadding = 20;
    public static final int TRANSFER_DURATION = 200;

    private Point currentPoint = new Point(0, 0, 0, 0);
    public FocusView(Context context) {
        this(context, null);
    }
    public FocusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setFocusable(false);
        setClickable(false);
        setBackgroundResource(R.drawable.bg_focus_box);
    }

    public void setFocusImage(int imgResID){
        setBackgroundResource(imgResID);
        invalidate();
    }

    public void animate(View v){
        animate(v, TRANSFER_DURATION);
    }
    public void animate(View v, int duration){
        if(v == null){
            return;
        }
        final int[] coors = new int[2];
        v.getLocationInWindow(coors);
        start(new Point(coors[0], coors[1], v.getWidth(), v.getHeight()), duration);
    }

    public void animate(float x, float y, int width, int height){
        start(new Point(x, y, width, height), TRANSFER_DURATION);
    }

    public void animate(float x, float y, int width, int height, int duration){

        start(new Point(x, y, width, height), duration);
    }

    public void start(Point targetPoint, int duration){
        targetPoint.setX(targetPoint.getX() - outPadding);
        targetPoint.setY(targetPoint.getY() - outPadding);
        targetPoint.setWidth(targetPoint.getWidth() + 2 * outPadding);
        targetPoint.setHeight(targetPoint.getHeight() + 2* outPadding);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), currentPoint, targetPoint);

        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                //optimize render
                if(getX() != currentPoint.getX()){

                    setX(currentPoint.getX());
                }
                if(getY() != currentPoint.getY()){

                    setY(currentPoint.getY());
                }
                ViewGroup.LayoutParams params = getLayoutParams();
                if(params.width != (int)currentPoint.getWidth() || params.height != (int)currentPoint.getHeight()){
                    params.width = (int) currentPoint.getWidth();
                    params.height = (int) currentPoint.getHeight();
                    setLayoutParams(params);

                }
            }
        });
        animator.start();
    }

    public class Point{
        float x;
        float y;
        float width;
        float height;

        public Point(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }
    }

    private class PointEvaluator implements TypeEvaluator{

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
            float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
            float w = startPoint.getWidth() + fraction * (endPoint.getWidth() - startPoint.getWidth());
            float h = startPoint.getHeight() + fraction * (endPoint.getHeight() - startPoint.getHeight());
            return new Point(x, y, w, h);
        }
    }

}
