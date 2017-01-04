package com.caishifu.myrecyclerview.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.caishifu.myrecyclerview.R;


/**
 * Created by wp on 2016/12/30.
 *
 * @description
 */

public class MyRecyclerView extends RelativeLayout {
    /**
     * 滑动需要
     */
    private Scroller mScroller ;

    private View mTopView ;

    private View mContentView ;

    private int mTopViewHeight ;


    private boolean topIsShown = true ;


    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.id_scroll_top);
        mContentView =  findViewById(R.id.id_scroll_content);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //设置头部分
        mTopView.layout(0 , 0 , getWidth() , mTopView.getMeasuredHeight());
        //设置内容部分
        mContentView.layout(0 , mTopViewHeight , getWidth() ,
                mTopViewHeight + mContentView.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
    }

    float  mLastY ;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float yDown ;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN :

                yDown = ev.getRawY() ;
                mLastY = yDown ;
                break;
            case MotionEvent.ACTION_MOVE :
                yDown = ev.getRawY() ;

                float dy = yDown - mLastY;
                //如果头部现实,则直接拦截，进行头部滑动
                if(topIsShown){
                    return true;
                }
                //如果头部隐藏，则判断手势进行第二次拦截，如果向下则现实头部，并且显示的是第一个item的position是0，进行拦截
                if(dy > 0  &&
                        ((LinearLayoutManager)((RecyclerView)mContentView).getLayoutManager())
                                .findFirstVisibleItemPosition() == 0){
                    //有可能第一个只显示一部分，让其进行全部显示，进行滑动
                    ((RecyclerView)mContentView).scrollToPosition(0);
                    topIsShown = true ;
                    return true;
                }
                mLastY = yDown ;
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float yDown ;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_MOVE :
                yDown = event.getRawY() ;
                float dy = (yDown - mLastY) ;
                //已经全部显示
                if(getScrollY()+ (-dy) < 0){
                    scrollTo(0 , 0);
                    topIsShown = true ;
                    return true ;
                }
                //全部隐藏
                if(getScrollY() + (-dy) > mTopViewHeight){
                    scrollTo(0 , mTopViewHeight);
                    topIsShown = false ;
                    return true ;
                }
                scrollBy(0 , (int)(-dy));
                mLastY = yDown ;
                break;
            case MotionEvent.ACTION_UP :
                yDown = event.getRawY();
                float d = (yDown - mLastY) ;
                //判断向下还是向上
                if(getScrollY()+ (-d) < (mTopViewHeight / 2) ){
                    mScroller.startScroll(0,getScrollY(),0, -getScrollY());
                    topIsShown = true ;
                }else {
                    mScroller.startScroll(0,getScrollY(),0, (mTopViewHeight - getScrollY()));
                    topIsShown = false ;
                }
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(0 , mScroller.getCurrY());
            postInvalidate();
        }
    }
}
