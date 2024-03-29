package com.bijesh.animationdemo.indrive.animations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bijesh.animationdemo.R;

import java.util.ArrayList;

/**
 * Created by bijesh on 9/24/2014.
 */
public class DemoView extends AppCompatImageView implements View.OnTouchListener, DrawablePoints{



    private Context mContext;
    int x = -1;
    int y = -1;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private Handler h;
    private static final int FRAME_RATE = 5;
    private Paint mPaint;
    private int mCounter,mFlashCounter = 0,moveHandCounter=0,messageCount=0;
    private Pt[] myNewNonCircle;
    private ArrayList<TutorialPoint> mListTutorialPoints = new ArrayList<TutorialPoint>();
    private boolean isCircle, isNonCircle, showNonCircleMessage = false;
    private boolean isHandDrawnToNavigationDrawer = false;
    private AnimationType mAnimationType = AnimationType.INTRO_MESSAGE;//AnimationType.GO_TO_DIAGNOSTICS_TAB;//AnimationTypeNew.TO_NAVIGATION_DRAWER; // AnimationTypeNew.INTRO_MESSAGE; AnimationTypeNew.SHOW_GEOFENCE_SCREEN;AnimationType.SHOW_MESSAGE_MOVE_TO_DRIVING_DATA_SCREEN;// AnimationType.SHOW_MESSAGE_MOVE_TO_DRIVING_DATA_SCREEN;;
    private TutorialType mTutorialType = TutorialType.HOME_SCREEN_TUTORIAL;//TutorialType.NAVIGATE_TO_DIAGNOSTICS_SCREEN;//TutorialType.TO_GEO_FENCE_FRAGMENT_TUTORIAL; //TutorialType.HOME_SCREEN_TUTORIAL;TutorialType.INSIDE_GEOFENCE_TUTORIAL; TutorialType.HOME_SCREEN_TUTORIAL; TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN;//TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN;
    private TutorialPoint[] toDiagnosticsNavigation;
    private BitmapDrawable mHand;
    private float mPhoneDensity;
//    private Runnable thisRunnable,nextRunnable;  toDrawPoints, toDrawOptionsPoints
    private TutorialPoint[] mDragHandToNavigationDrawer;
    private TutorialPoint[] mIrregularShapeOne;
    private TutorialPoint[] mThisIrregularCircle;
    private TutorialPoint[] mThisRegularCircle;
    private TutorialPoint[] mDragHandToAddButton;
    private TutorialPoint[] mDragHandToDiagnosticsNavigation;
    private TutorialPoint[] mPointsToVehicleHealthOption;
    private TutorialPoint[] mPointsToDrivingData;
    private TutorialPoint[] mToDrawButtonPoints;
    private TutorialPoint[] mMoveToDrawOptionsPoints;
    private TutorialPoint[] mToDiagonalTabPoints;


    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        h = new Handler();
        mPaint = getLinePaint(isCircle);
//        initializePoints();
//        myNewNonCircle = initializePoints1();
        this.setOnTouchListener(this);
        this.setBackgroundResource(R.drawable.home_screen);
//        this.setBackgroundResource(R.drawable.vehicle_health_screen);
        init();
    }

    private void init(){
        mHand = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.fpdwn);
        BitmapDrawable mArrow = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.arrow_right);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mPhoneDensity = metrics.density;
//        mDragHandToNavigationDrawer =  AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),dragHandToNavigationDrawer,getWidth(),getHeight());
    }

//    private int[] getWidthAndHeight(){
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int width = metrics.widthPixels;
//        int height = metrics.heightPixels;
//        return new int[]{width,height};
//    }


    private Runnable messageNavigation = new Runnable() {
        @Override
        public void run() {
            showToastMessage("Click on the navigation drawer for more options, like how i do",R.drawable.smiley_hi,true);
            h.postDelayed(gotoNavigation,5000);
            mAnimationType = AnimationType.TO_NAVIGATION_DRAWER;
        }
    };

    private Runnable gotoNavigation = new Runnable() {
        @Override
        public void run() {
//
            appendPoints(mDragHandToNavigationDrawer);
            invalidate();
        }
    };




    private int handCounter = 0;
    private boolean isFirst = true;
    private ArrayList<Integer> lstX = new ArrayList<Integer>();
    private ArrayList<Integer> lstY = new ArrayList<Integer>();
    private ArrayList<Pt> lstPt = new ArrayList<Pt>();

    protected void onDraw(Canvas canvas) {
        if(!isPointsInit){
            initPoints();
        }
        BitmapDrawable mIcDrawer = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ic_drawer);
        switch (mTutorialType){
            case HOME_SCREEN_TUTORIAL:
                homeScreenTutorial(canvas);
                break;
            case TO_GEO_FENCE_FRAGMENT_TUTORIAL:
                toGeofenceFragmentTutorial(canvas);
                break;
            case INSIDE_GEOFENCE_TUTORIAL:
                insideGeofenceTutorial(canvas);
                break;
            case DRAW_SHAPE_TUTORIAL:
                drawShapeTutorial(canvas);
                break;
            case NAVIGATE_TO_DIAGNOSTICS_SCREEN:
                navigateToDiagnosticsScreen(canvas);
                break;
            case NAVIGATE_TO_DRIVING_DATA_SCREEN:
                navigateToDrivingDataScreen(canvas);
                break;
        }

    }

    private boolean isPointsInit = false;
    private void initPoints(){
        mDragHandToNavigationDrawer =  AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),dragHandToNavigationDrawer,getWidth(),getHeight());
        mIrregularShapeOne = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),irregularShape1,getWidth(),getHeight());
        mThisIrregularCircle = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),mIrregularCircle,getWidth(),getHeight());
        mThisRegularCircle = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),mRegularCircle,getWidth(),getHeight());
        mDragHandToAddButton = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),dragHandToAddButton,getWidth(),getHeight());
        mDragHandToDiagnosticsNavigation = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),dragHandToDiagnosticsNavigation,getWidth(),getHeight());
        mPointsToVehicleHealthOption = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),pointsToVehicleHealthOption,getWidth(),getHeight());
        mPointsToDrivingData = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),pointsToDrivingData,getWidth(),getHeight());
        TutorialPoint[] mtoGeofenceFragOption = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity), toGeofenceFragOption, getWidth(), getHeight());
        mToDrawButtonPoints = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),toDrawButtonPoints,getWidth(),getHeight());
        mMoveToDrawOptionsPoints = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity),moveToDrawOptionsPoints,getWidth(),getHeight());
        mToDiagonalTabPoints = AnimUtils.getPointsBasedOnDevice(AnimUtils.getPhoneType(mPhoneDensity), toDiagonalTabPoints,getWidth(),getHeight());
        isPointsInit = true;
    }

    private void navigateToDiagnosticsScreen(Canvas canvas){

        BitmapDrawable hand = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.fpdwn);
        switch (mAnimationType){
            case GO_TO_DIAGNOSTICS_TAB:
//                toDiagnosticsTab = getPointsToDiagnosticsTab();
                moveHandToDiagnosticsTab(canvas, hand, mToDiagonalTabPoints);
                break;
            case FLASH_CLICKED_AT_DIAGNOSTICS_TAB:
                flashCircle(canvas, hand, mToDiagonalTabPoints, flashClickedAtDiagnosticsTabRunnable, showDiagnosticsScreenRunnable);
                break;
            case SHOW_DIAGNOSTICS_SCREEN:
                setBackground(R.drawable.diagnostics, retainHandAtDiagnosticsTabRunnable);
                break;
            case RETAIN_HAND_POSITION:
                showHandAtPosition(canvas, hand, mToDiagonalTabPoints[mToDiagonalTabPoints.length - 1], moveHandToDiagnosticsNavigationRunnable);
                break;
            case MOVE_HAND_TO_DIAGNOSTICS_NAVIGATION:
                moveHandFromOnePointToAnother(canvas,hand,toDiagnosticsNavigation, mToDiagonalTabPoints[mToDiagonalTabPoints.length - 1],moveHandToDiagnosticsNavigationRunnable, flashClickedAtDiagnosticsNavigationRunnable);
                break;
            case FLASH_CLICKED_AT_DIAGNOSTICS_NAVIGATION:
                flashCircle(canvas, hand, toDiagnosticsNavigation, flashClickedAtDiagnosticsNavigationRunnable, showDiagnosticsNavOptionsScreenRunnable);
                break;
            case SHOW_DIAGNOSTICS_NAV_OPTION_SCREEN:
                setBackground(R.drawable.diagnostics_nav_options_screen, retainHandAtDiagnosticsNavigationOptionRunnable);
                break;
            case RETAIN_HAND_AT_DIAGNOSTICS_NAVIGATION_OPTION:
                showHandAtPosition(canvas,hand,toDiagnosticsNavigation[toDiagnosticsNavigation.length-1], moveHandToVehicleHealthRunnable);
                break;
            case MOVE_HAND_TO_VEHICLE_HEALTH:
                moveHandFromOnePointToAnother(canvas,hand, mPointsToVehicleHealthOption,toDiagnosticsNavigation[toDiagnosticsNavigation.length - 1],moveHandToVehicleHealthRunnable, flashAtVehicleHealthRunnable);
                break;
            case FLASH_CLICKED_AT_VEHICLE_HEALTH:
                flashCircle(canvas,hand, mPointsToVehicleHealthOption,flashAtVehicleHealthRunnable, showVehicleHealthScreenRunnable);
                break;
            case SHOW_VEHICLE_HEALTH_SCREEN:
                setBackground(R.drawable.vehicle_health_screen, showMessageAtVehicleHealthScreenRunnable);
                break;
            case SHOW_MESSAGE_AT_VEHICLE_HEALTH_SCREEN:
//                DemoRunnable nextRunnable = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.SHOW_MESSAGE_MOVE_TO_DRIVING_DATA_SCREEN);
                showToastMessage("The View lists all the Trouble Codes and their corresponding details using Info Button on the Side.",
                       R.drawable.smiley_hi,true,showMessageToDrivingDataRunnable);
                break;
//            case SHOW_MESSAGE_END_OF_TUTORIAL:
//                showMessageEndTutorial();
//                break;
        }
    }

    private Runnable showMessageToDrivingDataRunnable = new Runnable() {
        @Override
        public void run() {
            mTutorialType = TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN;
            mAnimationType = AnimationType.SHOW_MESSAGE_MOVE_TO_DRIVING_DATA_SCREEN;
            invalidate();
        }
    };

    private void navigateToDrivingDataScreen(Canvas canvas){

         // for testing
        switch (mAnimationType){
            case SHOW_MESSAGE_MOVE_TO_DRIVING_DATA_SCREEN:
//                showMessage(canvas,"Lets move to driving data",showMessageMoveToDrivingDataRunnable,moveHandToDrivingDataTabRunnable);
                showToastMessage("Lets move to driving data",R.drawable.smiley_hi,true,moveHandToDrivingDataTabRunnable);
                break;
            case MOVE_HAND_TO_DRIVING_DATA_TAB:
//                DemoRunnable thisRunnable1 = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.MOVE_HAND_TO_DRIVING_DATA_TAB);
//                DemoRunnable nextRunnable1 = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.FLASH_CLICKED_AT_DRIVING_DATA_TAB);
                moveHand(canvas,mHand, mPointsToDrivingData,moveHandToDrivingDataTabRunnable,flashClickedAtDrivingDataRunnable);
                break;
            case FLASH_CLICKED_AT_DRIVING_DATA_TAB:
//                DemoRunnable thisRunnable2 = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.FLASH_CLICKED_AT_DRIVING_DATA_TAB);
//                DemoRunnable nextRunnable2 = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.SHOW_DRIVING_DATA_SCREEN);
                flashCircle(canvas,mHand, mPointsToDrivingData,flashClickedAtDrivingDataRunnable,showDrivingDataScreenRunnable);
                break;
            case SHOW_DRIVING_DATA_SCREEN:
//                DemoRunnable nextRunnable3 = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.MESSAGE_AT_DRIVING_DATA);
                setBackground(R.drawable.drivingdata_chart_screen,messageAtDrivingDataRunnable);
                break;
            case MESSAGE_AT_DRIVING_DATA:
//                DemoRunnable thisRunnable4 = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.MESSAGE_AT_DRIVING_DATA);
//                DemoRunnable nextRunnable4 = new DemoRunnable(TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN,AnimationType.SHOW_MESSAGE_END_OF_TUTORIAL);
//                showMessage(canvas,"View chart data here",messageAtDrivingDataRunnable,endOfTutorial);
                showToastMessage("View chart data here",R.drawable.smiley_hi,true,endOfTutorial);
                break;
             case SHOW_MESSAGE_END_OF_TUTORIAL:
                showMessageEndTutorial();
                break;
        }
    }



    private Runnable messageAtDrivingDataRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.MESSAGE_AT_DRIVING_DATA;
            invalidate();
        }
    };

    private Runnable showDrivingDataScreenRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.SHOW_DRIVING_DATA_SCREEN;
            invalidate();
        }
    };

    Runnable showMessageMoveToDrivingDataRunnable = new Runnable() {
        @Override
        public void run() {
            mTutorialType = TutorialType.NAVIGATE_TO_DRIVING_DATA_SCREEN;
            mAnimationType = AnimationType.MOVE_HAND_TO_DRIVING_DATA_TAB;
            invalidate();
        }
    };
//
private Runnable moveHandToDrivingDataTabRunnable = new Runnable() {
    @Override
    public void run() {
        mAnimationType = AnimationType.MOVE_HAND_TO_DRIVING_DATA_TAB;
        invalidate();
    }
};

    private Runnable flashClickedAtDrivingDataRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.FLASH_CLICKED_AT_DRIVING_DATA_TAB;
            invalidate();
        }
    };

    private Runnable showMessageAtVehicleHealthScreenRunnable = new Runnable() {
        @Override
        public void run() {
             mAnimationType = AnimationType.SHOW_MESSAGE_AT_VEHICLE_HEALTH_SCREEN;
            invalidate();
        }
    };

    private Runnable showVehicleHealthScreenRunnable = new Runnable() {
        @Override
        public void run() {
             mAnimationType = AnimationType.SHOW_VEHICLE_HEALTH_SCREEN;
             invalidate();
        }
    };

    private Runnable flashAtVehicleHealthRunnable = new Runnable() {
        @Override
        public void run() {
             mAnimationType = AnimationType.FLASH_CLICKED_AT_VEHICLE_HEALTH;
             invalidate();
        }
    };

    private Runnable moveHandToVehicleHealthRunnable = new Runnable() {
        @Override
        public void run() {
             mFlashCounter = 0;
             mAnimationType = AnimationType.MOVE_HAND_TO_VEHICLE_HEALTH;
             invalidate();
        }
    };

    private Runnable retainHandAtDiagnosticsNavigationOptionRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.RETAIN_HAND_AT_DIAGNOSTICS_NAVIGATION_OPTION;
            invalidate();
        }
    };

    private Runnable showDiagnosticsNavOptionsScreenRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.SHOW_DIAGNOSTICS_NAV_OPTION_SCREEN;
            invalidate();
        }
    };

    private Runnable flashClickedAtDiagnosticsNavigationRunnable = new Runnable() {
        @Override
        public void run() {
             moveHandCounter = 0;
            mAnimationType = AnimationType.FLASH_CLICKED_AT_DIAGNOSTICS_NAVIGATION;
            invalidate();
        }
    };

    private Runnable moveHandToDiagnosticsNavigationRunnable = new Runnable() {
        @Override
        public void run() {
              initCounters();
              toDiagnosticsNavigation = getPointsToDiagnosticsNavigation1();
              mAnimationType = AnimationType.MOVE_HAND_TO_DIAGNOSTICS_NAVIGATION;
              invalidate();
        }
    };

//    private void retainHandAtPosition(Canvas canvas,BitmapDrawable hand,TutorialPoint point){
//        showHandAtPosition()
//    }

    private void setBackground(int drawable,Runnable nextRunnable){
        this.setBackgroundResource(drawable);
        h.postDelayed(nextRunnable,500);
    }

    private Runnable retainHandAtDiagnosticsTabRunnable = new Runnable() {
        @Override
        public void run() {
             initCounters();
             mAnimationType = AnimationType.RETAIN_HAND_POSITION;
             invalidate();
        }
    };

    private void initCounters(){
        mCounter = 0;
        mFlashCounter = 0;

    }

    private Runnable showDiagnosticsScreenRunnable = new Runnable() {
        @Override
        public void run() {
              mFlashCounter = 0;
              mAnimationType = AnimationType.SHOW_DIAGNOSTICS_SCREEN;
              invalidate();
        }
    };
//    private int moveHandCounter = 0;
    private void moveHandFromOnePointToAnother(Canvas canvas,BitmapDrawable hand,TutorialPoint[] points,TutorialPoint point,Runnable moveRunnable,Runnable nextRunnable){
        if (moveHandCounter++ < getIterationLength(points)-1) {
            canvas.drawBitmap(hand.getBitmap(), points[moveHandCounter].x, points[moveHandCounter].y, null);
            h.postDelayed(moveRunnable, 5);
        }else{
            h.postDelayed(nextRunnable,100);
        }
    }


    private int moveHandToDiagnosticsTabCounter = 0;
    private void moveHandToDiagnosticsTab(Canvas canvas,BitmapDrawable hand,TutorialPoint[] points){

        if (moveHandToDiagnosticsTabCounter++ < getIterationLength(points)-1) {
            canvas.drawBitmap(hand.getBitmap(), points[moveHandToDiagnosticsTabCounter].x, points[moveHandToDiagnosticsTabCounter].y, null);
            h.postDelayed(moveHandToDiagnosticsTabRunnable, 5);
        }else{
            h.postDelayed(flashClickedAtDiagnosticsTabRunnable,100);
        }

    }

    private Runnable flashClickedAtDiagnosticsTabRunnable = new Runnable() {
        @Override
        public void run() {
             mAnimationType = AnimationType.FLASH_CLICKED_AT_DIAGNOSTICS_TAB;
             invalidate();
        }
    };



    private Runnable moveHandToDiagnosticsTabRunnable = new Runnable() {
        @Override
        public void run() {
             mTutorialType = TutorialType.NAVIGATE_TO_DIAGNOSTICS_SCREEN;
             mAnimationType = AnimationType.GO_TO_DIAGNOSTICS_TAB;
             invalidate();
        }
    };



    private void drawShapeTutorial(Canvas canvas){
        BitmapDrawable hand = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.fpdwn);

        switch (mAnimationType){
            case SHOW_GEOFENCE_SCREEN_AGAIN:
                showGeofenceScreen(canvas);
                break;
            case SHOW_MESSAGE_LEARN_DRAW_SHAPE:
                showMessageLearnDrawShape();
                break;
            case DRAW_INCORRECT_SHAPE_1:
                drawShapeWithPath(canvas,hand, mThisIrregularCircle);
                break;
            case SHOW_MESSAGE_DOES_NOT_LOOK_LIKE_CIRCLE:
                showInCorrectMessage();
                break;
            case DRAW_INCORRECT_SHAPE_2:
                drawShapeWithPath(canvas,hand, mIrregularShapeOne);
                break;
            case SHOW_MESSAGE_STILL_DOEST_NOT_LOOK_LIKE_A_CIRCLE:
                showIncorrectMessage1();
                break;
            case DRAW_CORRECT_SHAPE:
                drawShapeWithPath(canvas,hand, mThisRegularCircle);
                break;
            case SHOW_MESSAGE_THAT_IS_PERFECT:
                showMessagePerfect();
                break;

        }
    }

    /**
     * Main Runnable used to control all animations
     */
    private class DemoRunnable implements Runnable{
        TutorialType tutType;
        AnimationType animType;
        private DemoRunnable(TutorialType tutType,AnimationType animType){
            this.tutType = tutType;
            this.animType = animType;
        }

        @Override
        public void run() {
            mTutorialType = tutType;
            mAnimationType = animType;
            if(mAnimationType == AnimationType.TO_NAVIGATION_DRAWER){
                appendPoints(mDragHandToNavigationDrawer);
            }
            invalidate();
        }
    }

    private void showMessageEndTutorial(){
        showToastMessage("End of Demo",R.drawable.smiley_hi,true);

    }

    private void showMessagePerfect(){
        showToastMessage("Perfect, it looks like a circle",R.drawable.smiley_hi,true);
        h.postDelayed(navigateToDiagnosticsScreenRunnable,6000);
    }



    private Runnable navigateToDiagnosticsScreenRunnable = new Runnable() {
        @Override
        public void run() {
            mTutorialType = TutorialType.NAVIGATE_TO_DIAGNOSTICS_SCREEN;
            mAnimationType = AnimationType.GO_TO_DIAGNOSTICS_TAB;
            invalidate();
        }
    };

    private Runnable endOfTutorial = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.SHOW_MESSAGE_END_OF_TUTORIAL;
            invalidate();
        }
    };

    private void showIncorrectMessage1(){
        showToastMessage("That still does n't look like a circle, Shall we try again",R.drawable.smiley_hi,true);
        mListTutorialPoints = new ArrayList<TutorialPoint>();
        mCounter = 0;
        h.postDelayed(drawCorrectShape,6000);
    }

    private Runnable drawCorrectShape = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.DRAW_CORRECT_SHAPE;
            invalidate();
        }
    };

    private void showInCorrectMessage(){
        showToastMessage("That does n't look like a circle, Shall we try again",R.drawable.smiley_hi,true);
        mListTutorialPoints = new ArrayList<TutorialPoint>();
        mCounter = 0;
        h.postDelayed(drawIncorrectShapeRunnable1,6000);
    }

    private int drawShapeHandCounter = 0;
    private void drawShapeWithPath(Canvas canvas,BitmapDrawable image,TutorialPoint[] points){
        float x = points[0].x;
        float y = points[0].y;
        Path path = new Path();
        path.moveTo(x, y);
//        System.out.println("mListTutorialPoints size "+mListTutorialPoints.size());
        for (TutorialPoint p : mListTutorialPoints) {
            path.lineTo(p.x, p.y);
        }
        if(isCircle){
            mPaint = getLinePaint(isCircle);
        }
        canvas.drawPath(path, mPaint);
        if (drawShapeHandCounter < getIterationLength(points)) {
            TutorialPoint tutPoint = points[drawShapeHandCounter];//getShapePoints(isCircle,handCounter);
            canvas.drawBitmap(image.getBitmap(),tutPoint.x, tutPoint.y, null);
            drawShapeHandCounter++;
        }
//        System.out.println("drawShapeHandCounter "+drawShapeHandCounter);
        if(drawShapeHandCounter < getIterationLength(points)){
            h.postDelayed(drawRunnable,10);
        }else{
//            mListTutorialPoints = new ArrayList<TutorialPoint>();
            drawShapeHandCounter = 0;
            if(mAnimationType == AnimationType.DRAW_INCORRECT_SHAPE_2){
                h.postDelayed(inCorrectMessageRunnable1,300);
            }else if(mAnimationType == AnimationType.DRAW_INCORRECT_SHAPE_1){
                h.postDelayed(incorrectMessageRunnable, 300);
            }else{
                h.postDelayed(showMessagePerfectRunnable,300);
            }

        }

    }

    private Runnable showMessagePerfectRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.SHOW_MESSAGE_THAT_IS_PERFECT;
            invalidate();
        }
    };

    private Runnable inCorrectMessageRunnable1 = new Runnable() {
        @Override
        public void run() {
            mAnimationType =  AnimationType.SHOW_MESSAGE_STILL_DOEST_NOT_LOOK_LIKE_A_CIRCLE;
            invalidate();
        }
    };

    private Runnable incorrectMessageRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.SHOW_MESSAGE_DOES_NOT_LOOK_LIKE_CIRCLE;
            invalidate();
        }
    };

    private Runnable drawRunnable = new Runnable() {
        @Override
        public void run() {
            if(mAnimationType == AnimationType.DRAW_INCORRECT_SHAPE_1){
                appendIrregularCirclePoints(mThisIrregularCircle);
            }else if(mAnimationType == AnimationType.DRAW_INCORRECT_SHAPE_2){
                appendIrregularCirclePoints(mIrregularShapeOne);
            }else{
                appendIrregularCirclePoints(mThisRegularCircle);
            }
            invalidate();
        }
    };

    private void appendIrregularCirclePoints(TutorialPoint[] points){
//        System.out.println("mCounter "+mCounter);
        if(mCounter < getIterationLength(points))
            mListTutorialPoints.add(points[mCounter++]);
    }



    private Runnable showMessageLearnDrawRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.SHOW_MESSAGE_LEARN_DRAW_SHAPE;
            invalidate();
        }
    };

    private void showMessageLearnDrawShape(){
        showToastMessage("Lets learn how to draw a geofence",R.drawable.smiley_hi,true);
        mCounter = 0;
        mListTutorialPoints = new ArrayList<TutorialPoint>();
        h.postDelayed(drawIncorrectShapeRunnable,7000);
    }

    private Runnable drawIncorrectShapeRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.DRAW_INCORRECT_SHAPE_1;

            invalidate();
        }
    };

    private Runnable drawIncorrectShapeRunnable1 = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.DRAW_INCORRECT_SHAPE_2;

            invalidate();
        }
    };

    private TutorialPoint getRightArrowPoint(){
        TutorialPoint point = null;
        float centerX = getWidth()  / 2;
        float centerY = getHeight() / 2;
        point = new TutorialPoint(centerX,centerY + 100);
        return point;
    }

    private void insideGeofenceTutorial(Canvas canvas){
        BitmapDrawable hand = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.fpdwn);

        switch (mAnimationType){
            case SHOW_GEOFENCE_SCREEN:
                showGeofenceScreen(canvas);
                break;
            case MOVE_HAND_TO_DRAW_BUTTON:
//                toDrawPoints = getPointsToDrawButton();
                moveHandToDrawButton(canvas,hand, mToDrawButtonPoints);
                break;
            case FLASH_CLICKED_AT_DRAW_BUTTON:
                flashClickedAtDrawButton(canvas,hand);
                break;
            case SHOW_DRAW_OPTION_SCREEN:
                setShowDrawOptionScreen();
                break;
            case MOVE_HAND_TO_DRAW_OPTION:
//                toDrawOptionsPoints = getPointsToDrawOptions(mToDrawButtonPoints[mToDrawButtonPoints.length-1]);
                moveHandToDrawOption(canvas,hand,mMoveToDrawOptionsPoints);
                break;
            case FLASH_CLICKED_AT_DRAW_OPTION:
                flashClickedAtDrawOption(canvas,hand);
                break;
        }
    }

    private void homeScreenTutorial(Canvas canvas){
        BitmapDrawable hand = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.fpdwn);

        switch (mAnimationType) {
            case INTRO_MESSAGE:
                introMessage();
                break;
            case TO_NAVIGATION_DRAWER:
                showHand(canvas, hand);
                break;
            case FLASH_CIRCLE:
                flashCircle(canvas, hand, mDragHandToNavigationDrawer);
                break;
            case SHOW_RIGHT_ARROW:
                BitmapDrawable arrow = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.arrow_right);
                showRightArrow(canvas, arrow);
                break;
            case SHOW_LEFT_ARROW:
                BitmapDrawable leftArrow = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.arrow_left);
                showLeftArrow(canvas, leftArrow);
                break;
//            case MOVE_HAND_RIGHT_OR_LEFT:
////                BitmapDrawable indicators = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.indicators);
////                showIndicators(canvas, indicators);
//                  dragHands(canvas,hand);
//                break;
            case MOVE_HAND_FROM_RIGHT_TO_CENTER:
                moveHandFromRightToCenter(canvas,hand);
                BitmapDrawable rightArrow = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.arrow_right);
                showRightArrowAtPosition(canvas,rightArrow,getRightArrowPoint());
                break;
            case MOVE_HAND_FROM_CENTER_TO_RIGHT:
               moveHandFromCenter(canvas,hand);
               BitmapDrawable leftArrowNew = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.arrow_left);
              showLeftArrowAtPosition(canvas, leftArrowNew,getRightArrowPoint());
               break;
        }
    }

    private void toGeofenceFragmentTutorial(Canvas canvas){
        System.out.println("animation type " + mAnimationType);
        BitmapDrawable hand = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.fpdwn);
        switch (mAnimationType){
            case TO_NAVIGATION_DRAWER:
                showHand(canvas, hand);
                break;
            case FLASH_CIRCLE:
                flashCircle(canvas, hand, mDragHandToNavigationDrawer);
                break;
            case RETAIN_HAND:
                retainHand(canvas,hand);
            case SHOW_RIGHT_ARROW:
                BitmapDrawable arrow = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.arrow_right);
                showRightArrow(canvas, arrow);
                break;
            case MOVE_HAND_TO_GEOFENCE_FRAGMENT:
                if(!isMovingToGeofenceOption) {
//                    mtoGeofenceFragOption = getPointsToGeofenceAlerts(mDragHandToNavigationDrawer[mDragHandToNavigationDrawer.length - 1]);
                    isMovingToGeofenceOption = true;
                }
                moveHandToGeofenceFrag(canvas,hand,toGeofenceFragOption);
                break;
            case FLASH_CLICKED:
                showHandAtPosition(canvas,hand,toGeofenceFragOption[toGeofenceFragOption.length-1]);
                flashAroundClicked(canvas,hand);
                break;
            case MOVE_HAND_TO_ADD_BUTTON:
                moveHandToAddButton(canvas,hand, mDragHandToAddButton);
                break;
            case FLASH_CLICKED_AT_ADD_BUTTON:
                flashClickedAtAddButton(canvas,hand, mDragHandToAddButton[mDragHandToAddButton.length-1]);
                break;

        }
    }

    private void showGeofenceScreen(Canvas canvas){
        this.setBackgroundResource(R.drawable.alerts_map_screen);
        if(mAnimationType == AnimationType.SHOW_GEOFENCE_SCREEN_AGAIN){
            h.postDelayed(showMessageLearnDrawRunnable,500);
        }else{
            h.postDelayed(moveHandToDrawButtonRunnable,500);
        }
    }

    private Runnable moveHandToDrawButtonRunnable = new Runnable() {
        @Override
        public void run() {
            mTutorialType = TutorialType.INSIDE_GEOFENCE_TUTORIAL;
            mAnimationType = AnimationType.MOVE_HAND_TO_DRAW_BUTTON;
            invalidate();
        }
    };

    private void flashCircle(Canvas canvas,BitmapDrawable img,TutorialPoint[] points,Runnable flashRunnable,Runnable nextRunnable){
        if (mFlashCounter == 0 || mFlashCounter == 2 || mFlashCounter == 4 || mFlashCounter == 6 || mFlashCounter == 8
                || mFlashCounter == 10 || mFlashCounter == 12) {
            canvas.drawBitmap(img.getBitmap(), points[getIterationLength(points) - 1].x, points[getIterationLength(points) - 1].y, null);
            canvas.drawCircle(points[getIterationLength(points) - 1].x, points[getIterationLength(points) - 1].y, 20f, getMarkerPaint(true));
            if (mFlashCounter <= 12) {
                mFlashCounter++;
                h.postDelayed(flashRunnable, 400);
            }

        }else if (mFlashCounter == 1 || mFlashCounter == 3 || mFlashCounter == 5 || mFlashCounter == 7 || mFlashCounter == 9
                || mFlashCounter == 11) {
            canvas.drawBitmap(img.getBitmap(), points[getIterationLength(points) - 1].x, points[getIterationLength(points) - 1].y, null);
            mFlashCounter++;
            h.postDelayed(flashRunnable, 400);
        }else{

            h.postDelayed(nextRunnable, 400);
        }
    }


    private int mFlashClickedAtAddButtonCounter = 0;
    private void flashClickedAtAddButton(Canvas canvas,BitmapDrawable img,TutorialPoint point){
        if (mFlashClickedAtAddButtonCounter == 0 || mFlashClickedAtAddButtonCounter == 2 || mFlashClickedAtAddButtonCounter == 4 || mFlashClickedAtAddButtonCounter == 6 || mFlashClickedAtAddButtonCounter == 8
                || mFlashClickedAtAddButtonCounter == 10 || mFlashClickedAtAddButtonCounter == 12) {
            canvas.drawBitmap(img.getBitmap(), mDragHandToAddButton[getIterationLength(mDragHandToAddButton) - 1].x, mDragHandToAddButton[getIterationLength(mDragHandToAddButton) - 1].y, null);
            canvas.drawCircle(mDragHandToAddButton[getIterationLength(mDragHandToAddButton) - 1].x, mDragHandToAddButton[getIterationLength(mDragHandToAddButton) - 1].y, 20f, getMarkerPaint(true));
            if (mFlashClickedAtAddButtonCounter <= 12) {
                mFlashClickedAtAddButtonCounter++;
                h.postDelayed(flashClickedAtAddButtonRunnable, 400);
            }

        }else if (mFlashClickedAtAddButtonCounter == 1 || mFlashClickedAtAddButtonCounter == 3 || mFlashClickedAtAddButtonCounter == 5 || mFlashClickedAtAddButtonCounter == 7 || mFlashClickedAtAddButtonCounter == 9
                || mFlashClickedAtAddButtonCounter == 11) {
            canvas.drawBitmap(img.getBitmap(), mDragHandToAddButton[getIterationLength(mDragHandToAddButton) - 1].x, mDragHandToAddButton[getIterationLength(mDragHandToAddButton) - 1].y, null);
            mFlashClickedAtAddButtonCounter++;
            h.postDelayed(flashClickedAtAddButtonRunnable, 400);
        }else{
            h.postDelayed(showGeofenceMapScreenRunnable, 400);
        }
    }

    private Runnable showGeofenceMapScreenRunnable = new Runnable() {
        @Override
        public void run() {
            mTutorialType = TutorialType.INSIDE_GEOFENCE_TUTORIAL;
            mAnimationType = AnimationType.SHOW_GEOFENCE_SCREEN;
            invalidate();
        }
    };



    private int mFlashClickedCount = 0;
    private void flashAroundClicked(Canvas canvas,BitmapDrawable img){

        if (mFlashClickedCount == 0 || mFlashClickedCount == 2 || mFlashClickedCount == 4 || mFlashClickedCount == 6 || mFlashClickedCount == 8
                || mFlashClickedCount == 10 || mFlashClickedCount == 12) {

            canvas.drawBitmap(img.getBitmap(), toGeofenceFragOption[getIterationLength(toGeofenceFragOption) - 1].x, toGeofenceFragOption[getIterationLength(toGeofenceFragOption) - 1].y, null);
            canvas.drawCircle(toGeofenceFragOption[getIterationLength(toGeofenceFragOption) - 1].x, toGeofenceFragOption[getIterationLength(toGeofenceFragOption) - 1].y, 20f, getMarkerPaint(true));
            if (mFlashClickedCount <= 12) {
                mFlashClickedCount++;
                h.postDelayed(flashClickedRunnable, 400);
            }

        }else if (mFlashClickedCount == 1 || mFlashClickedCount == 3 || mFlashClickedCount == 5 || mFlashClickedCount == 7 || mFlashClickedCount == 9
                || mFlashClickedCount == 11) {
            canvas.drawBitmap(img.getBitmap(), toGeofenceFragOption[getIterationLength(toGeofenceFragOption) - 1].x, toGeofenceFragOption[getIterationLength(toGeofenceFragOption) - 1].y, null);
            mFlashClickedCount++;
            h.postDelayed(flashClickedRunnable, 400);
        }else {
            this.setBackgroundResource(R.drawable.geo_fence_fragment);
//            mFlashClickedCount = 0;
            h.postDelayed(moveHandToAddButtonRunnable,500); // move the hands to the point
        }
    }

    private Runnable moveHandToAddButtonRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.MOVE_HAND_TO_ADD_BUTTON;
            invalidate();
        }
    };

    private boolean isMovingToGeofenceOption = false;
    private TutorialPoint[] getPointsToGeofenceAlerts(TutorialPoint endPoint){
        TutorialPoint[] retPoints = new TutorialPoint[50];
        for(int i=0;i<50;i++){
            if(i == 0) {
                retPoints[i] = endPoint;
            }else{
                TutorialPoint points = new TutorialPoint(endPoint.x,retPoints[i-1].y+7.00f);
                retPoints[i] = points;
            }
        }
        return retPoints;
    }

    private TutorialPoint[] getPointsToDrawButton(){
        TutorialPoint[] retPoints = new TutorialPoint[50];
        float x = getWidth() / 2;
        float y = getHeight() - 70;
        for(int i=0;i<50;i++){
            x = x+7.00f;
            TutorialPoint point = new TutorialPoint(x+3.00f,y);
            retPoints[i] = point;
        }
        return retPoints;
    }

    private TutorialPoint[] getPointsToDiagnosticsTab(){
        ArrayList<TutorialPoint> listPoints = new ArrayList<TutorialPoint>();

        float x = getWidth() / 2;
        float y = getHeight() / 2;

        float endX = getWidth();
        float endY = 300;
//        System.out.println("endX "+endX+" endY "+endY);
        boolean iteration = true, reachedX = false, reachedY = false;
//        for(int i=0;i<50;i++){
           while(iteration) {
//               System.out.println("x " + x + " y " + y);
               if (x < endX - 80) {
                   x += 7.00f;
               }else{
                   reachedX = true;
               }
               if (y > endY) {
                   y -= 7.00f;
               }else{
                   reachedY = true;
               }
               if(reachedX & reachedY){
                   iteration = false;
               }
               TutorialPoint point = new TutorialPoint(x, y);
               listPoints.add(point);
           }
//        }

        TutorialPoint[] retPoints = new TutorialPoint[listPoints.size()];
        listPoints.toArray(retPoints);
//        for(TutorialPoint p:retPoints)
//          System.out.println("$$$ poi "+p);
        return retPoints;
    }

    private TutorialPoint[] getPointsToDiagnosticsNavigation(TutorialPoint lastPoint,BitmapDrawable icDrawer){
    //commenting the below code as it seems to be a dead code

        float drawerImageWidth = icDrawer.getBitmap().getWidth();
        float drawerImageHeight = icDrawer.getBitmap().getHeight();

        //commenting the infinite loop as it doesn't seems to be getting used anywhere
        //it is declared locally and then used in infinite while

        return null;
    }

    private TutorialPoint[] getPointsToDiagnosticsNavigation1(){
        return mDragHandToDiagnosticsNavigation;
    }


    private TutorialPoint[] getPointsToDrawOptions(TutorialPoint startPoint){
        TutorialPoint[] retPoints = new TutorialPoint[90];
        float x = startPoint.x;
        float y = startPoint.y;
//        x reduces y reduces
        for(int i=0;i<90;i++){
            x -= 7.00f;
            y -= 7.00f;
            TutorialPoint point = new TutorialPoint(x,y);
            retPoints[i] = point;
        }
        return retPoints;
    }

    private void retainHand(Canvas canvas,BitmapDrawable hand){
        showHandAtPosition(canvas,hand, mDragHandToNavigationDrawer[mDragHandToNavigationDrawer.length-1]);
    }

    private void introMessage() {
        showToastMessage("Let me walk you through the app", R.drawable.smiley_hi,true);
        h.postDelayed(messageNavigation, 5000);
    }

    private void dragHands(Canvas canvas,BitmapDrawable hand){
        showHandAtCenter(canvas, hand);
        h.postDelayed(moveHandFromCenterToRightRunnable,200);
        mAnimationType = AnimationType.MOVE_HAND_FROM_RIGHT_TO_CENTER;
    }

    private Runnable moveHandFromCenterToRightRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    private void showHand(Canvas canvas, BitmapDrawable hand) {
        float[] startPoints = null;
        if (isFirst) {
            startPoints = getStartPoints(mDragHandToNavigationDrawer);
            float startX = startPoints[0];
            float startY = startPoints[1];
            isFirst = false;
            canvas.drawBitmap(hand.getBitmap(), startX, startY, null);
        }
        moveHand(canvas, hand, mDragHandToNavigationDrawer[0], mDragHandToNavigationDrawer);
    }

    private void showHandAtPosition(Canvas canvas,BitmapDrawable hand,TutorialPoint point){
        canvas.drawBitmap(hand.getBitmap(),point.x,point.y,null);
    }

    private void showHandAtPosition(Canvas canvas,BitmapDrawable hand,TutorialPoint point,Runnable nextRunnable){
        canvas.drawBitmap(hand.getBitmap(),point.x,point.y,null);
        h.postDelayed(nextRunnable,300);
    }

    private void showHandAtCenter(Canvas canvas,BitmapDrawable hand){
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        canvas.drawBitmap(hand.getBitmap(), x, y, null);
    }
    //    float moveXFromCenterToRight = getWidth() / 2;
//    float moveYFromCenterToRight = getHeight() / 2;
    private int movementCounter = 0;
    private void moveHandFromCenter(Canvas canvas,BitmapDrawable hand){
//        System.out.println("$$$ movementCou "+movementCounter+" x "+moveXFromCenterToRight+" Y "+moveYFromCenterToRight+" center x "+(getWidth() /2)+" center y"+(getHeight() / 2));
        if(movementCounter++ < 5) {
            canvas.drawBitmap(hand.getBitmap(), getMovementXFromCenter(getWidth() / 2, movementCounter), getHeight()/2, null);
//            moveXFromCenterToRight += 50;
//            moveYFromCenterToRight += 50;
            h.postDelayed(moveHandFromCenterToRightRunnable, 500);
        }else if(movementCounter == 6 && screenCounter >= 0){
            movementCounter = 0;
            h.postDelayed(moveHandFromCenterToRightRunnable,1000);
            setScreenFromCenterToRight(screenCounter--);
        }else{
            h.postDelayed(getStartToGeoFenceFragmentTutorialMessage,2000);
        }
    }

    private Runnable getStartToGeoFenceFragmentTutorialMessage = new Runnable() {
        @Override
        public void run() {
            showToastMessage("Now lets learn how to draw geo fencing",R.drawable.smiley_hi,true);
            h.postDelayed(startToGeoFenceFragmentTutorial,4000);
        }
    };

    private Runnable startToGeoFenceFragmentTutorial = new Runnable() {
        @Override
        public void run() {
            mTutorialType = TutorialType.TO_GEO_FENCE_FRAGMENT_TUTORIAL;
            mAnimationType = AnimationType.TO_NAVIGATION_DRAWER;
            invalidate();
        }
    };

    Runnable endDemoMessageRunnable = new Runnable() {
        @Override
        public void run() {
            showToastMessage("That's it for now, see you again",R.drawable.smiley_hi,true);
        }
    };
    //        float moveXFromRightToCenter = getWidth() + 250;
//        float moveYFromCenterToRight = getHeight() / 2;
    private int screenCounter = 1;
    private void moveHandFromRightToCenter(Canvas canvas,BitmapDrawable hand){
//        System.out.println("$$$ movementCou "+movementCounter+" ScreenCounter "+screenCounter);
        if(movementCounter++ < 3) {
//            System.out.println("center "+getWidth()/2+" x "+getMovementXFromRightToCenter((float)(getWidth() + 250),movementCounter));
            canvas.drawBitmap(hand.getBitmap(),getMovementXFromRightToCenter((float)((getWidth() /2) + 250),movementCounter),getHeight()/2,null);
//            canvas.drawBitmap(hand.getBitmap(),970.0f,getHeight()/2,null);
//            setScreenFromRightToCenter(screenCounter++);
            h.postDelayed(moveHandFromRightToCenterRunnable,500);
        }
        else if(movementCounter == 4 && screenCounter < 3){
            movementCounter = 0;
            h.postDelayed(moveHandFromRightToCenterRunnable,1000);
            setScreenFromRightToCenter(screenCounter++);

        }
        else if(screenCounter == 3){
//            System.out.println("inside else if screen counter is 3");
            h.postDelayed(moveHandFromCenterToRightRunnable,1000);
            mAnimationType = AnimationType.MOVE_HAND_FROM_CENTER_TO_RIGHT;
           // h.postDelayed(getStartToGeoFenceFragmentTutorialMessage,2000);
            movementCounter = 0;
            //screenCounter = 4;
        }
    }

    private Runnable moveHandFromRightToCenterRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    private void setScreenFromRightToCenter(int counter){

        if(counter == 1){
            setBackgroundResource(R.drawable.location_screen);
        }else if(counter == 2){
            setBackgroundResource(R.drawable.driving_data_screen);
        }else if(counter == 3){
            setBackgroundResource(R.drawable.diagnostics);
        }else if(counter == 4){
            setBackgroundResource(R.drawable.help_screen);
        }

    }

    private void setScreenFromCenterToRight(int counter){
        if(counter == 5){
            setBackgroundResource(R.drawable.help_screen);
        }
        else if(counter == 4){
            setBackgroundResource(R.drawable.diagnostics);
        }else if(counter == 3){
            setBackgroundResource(R.drawable.driving_data_screen);
        }else if(counter == 2){
            setBackgroundResource(R.drawable.location_screen);
        }else if(counter == 1){
            setBackgroundResource(R.drawable.home_screen);
        }
    }

    private float getMovementXFromCenter(float fl,int count){
        if(count == 1){
            fl += 50;
        }else if(count == 2){
            fl += 100;
        }else if(count == 3){
            fl += 150;
        }else if(count == 4){
            fl += 200;
        }else if(count == 5){
            fl += 250;
        }
        return fl;
    }

    private float getMovementXFromRightToCenter(float fl,int count){
        if(count == 1){
            fl -= 50;
        }else if(count == 2){
            fl -= 100;
        }else if(count == 3){
            fl -= 150;
        }else if(count == 4){
            fl -= 200;
        }else if(count == 5){
            fl -= 250;
        }
        return fl;
    }

    private void moveHand(Canvas canvas, BitmapDrawable img, TutorialPoint startPoint, TutorialPoint[] points) {
        if (handCounter < getIterationLength(points)) {
            TutorialPoint tutPoint = getShapePoints(mDragHandToNavigationDrawer, handCounter);
            canvas.drawBitmap(img.getBitmap(), tutPoint.x, tutPoint.y, null);
            handCounter++;
        }
        if (mCounter < getIterationLength(mDragHandToNavigationDrawer)) {
//            block which draws shape
            h.postDelayed(gotoNavigation, FRAME_RATE);
        } else if (mCounter == getIterationLength(mDragHandToNavigationDrawer)) {
            showSelectedAnim(canvas, img, mDragHandToNavigationDrawer);
            mCounter = 0;
            handCounter = 0;
//            isHandDrawnToNavigationDrawer = true;
//            if(mTutorialType == TutorialType.HOME_SCREEN_TUTORIAL) {
            mAnimationType = AnimationType.FLASH_CIRCLE;
            h.postDelayed(flashRunnable, FRAME_RATE);
//            }
        }
    }
    private int moveHandToButtonCounter = 0;
    private void moveHandToAddButton(Canvas canvas,BitmapDrawable img,TutorialPoint[] points){
        if (moveHandToButtonCounter++ < getIterationLength(points)-1) {
            canvas.drawBitmap(img.getBitmap(), points[moveHandToButtonCounter].x, points[moveHandToButtonCounter].y, null);
            h.postDelayed(moveHandToAddButtonRunnable, 2);
        }else{
            h.postDelayed(flashClickedAtAddButtonRunnable,100);
        }
    }



    private int moveHandToDrawButtonCounter = 0;
    private void moveHandToDrawButton(Canvas canvas,BitmapDrawable img,TutorialPoint[] points){
        if (moveHandToDrawButtonCounter++ < getIterationLength(points)-1) {
            canvas.drawBitmap(img.getBitmap(), points[moveHandToDrawButtonCounter].x, points[moveHandToDrawButtonCounter].y, null);
            h.postDelayed(moveHandToDrawButtonRunnable, 50);
        }else{
            h.postDelayed(flashClickedAtDrawButtonRunnable,100);
        }
    }

    private Runnable flashClickedAtDrawButtonRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.FLASH_CLICKED_AT_DRAW_BUTTON;
            invalidate();
        }
    };
    private int mFlashClickedAtDrawButtonCounter = 0;
    private void flashClickedAtDrawButton(Canvas canvas,BitmapDrawable img){
        if (mFlashClickedAtDrawButtonCounter == 0 || mFlashClickedAtDrawButtonCounter == 2 || mFlashClickedAtDrawButtonCounter == 4 || mFlashClickedAtDrawButtonCounter == 6 || mFlashClickedAtDrawButtonCounter == 8
                || mFlashClickedAtDrawButtonCounter == 10 || mFlashClickedAtDrawButtonCounter == 12) {

            canvas.drawBitmap(img.getBitmap(), mToDrawButtonPoints[getIterationLength(mToDrawButtonPoints) - 1].x, mToDrawButtonPoints[getIterationLength(mToDrawButtonPoints) - 1].y, null);
            canvas.drawCircle(mToDrawButtonPoints[getIterationLength(mToDrawButtonPoints) - 1].x, mToDrawButtonPoints[getIterationLength(mToDrawButtonPoints) - 1].y, 20f, getMarkerPaint(true));
            if (mFlashClickedAtDrawButtonCounter <= 12) {
                mFlashClickedAtDrawButtonCounter++;
                h.postDelayed(flashClickedAtDrawButtonRunnable, 400);
            }

        }else if (mFlashClickedAtDrawButtonCounter == 1 || mFlashClickedAtDrawButtonCounter == 3 || mFlashClickedAtDrawButtonCounter == 5 || mFlashClickedAtDrawButtonCounter == 7 || mFlashClickedAtDrawButtonCounter == 9
                || mFlashClickedAtDrawButtonCounter == 11) {
            canvas.drawBitmap(img.getBitmap(), mToDrawButtonPoints[getIterationLength(mToDrawButtonPoints) - 1].x, mToDrawButtonPoints[getIterationLength(mToDrawButtonPoints) - 1].y, null);
            mFlashClickedAtDrawButtonCounter++;
            h.postDelayed(flashClickedAtDrawButtonRunnable, 400);
        }else{
            h.postDelayed(showDrawOptionScreenRunnable,400);
        }
    }

    private Runnable showDrawOptionScreenRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.SHOW_DRAW_OPTION_SCREEN;
            invalidate();
        }
    };

    private int mFlashClickedAtDrawOptionCounter = 0;
    private void flashClickedAtDrawOption(Canvas canvas,BitmapDrawable img){
        if (mFlashClickedAtDrawOptionCounter == 0 || mFlashClickedAtDrawOptionCounter == 2 || mFlashClickedAtDrawOptionCounter == 4 || mFlashClickedAtDrawOptionCounter == 6 || mFlashClickedAtDrawOptionCounter == 8
                || mFlashClickedAtDrawOptionCounter == 10 || mFlashClickedAtDrawOptionCounter == 12) {

            canvas.drawBitmap(img.getBitmap(), mMoveToDrawOptionsPoints[getIterationLength(mMoveToDrawOptionsPoints) - 1].x, mMoveToDrawOptionsPoints[getIterationLength(mMoveToDrawOptionsPoints) - 1].y, null);
            canvas.drawCircle(mMoveToDrawOptionsPoints[getIterationLength(mMoveToDrawOptionsPoints) - 1].x, mMoveToDrawOptionsPoints[getIterationLength(mMoveToDrawOptionsPoints) - 1].y, 20f, getMarkerPaint(true));
            if (mFlashClickedAtDrawOptionCounter <= 12) {
                mFlashClickedAtDrawOptionCounter++;
                h.postDelayed(flashClickedAtDrawOptionsRunnable, 400);
            }

        }else if (mFlashClickedAtDrawOptionCounter == 1 || mFlashClickedAtDrawOptionCounter == 3 || mFlashClickedAtDrawOptionCounter == 5 || mFlashClickedAtDrawOptionCounter == 7 || mFlashClickedAtDrawOptionCounter == 9
                || mFlashClickedAtDrawOptionCounter == 11) {
            canvas.drawBitmap(img.getBitmap(), mMoveToDrawOptionsPoints[getIterationLength(mMoveToDrawOptionsPoints) - 1].x, mMoveToDrawOptionsPoints[getIterationLength(mMoveToDrawOptionsPoints) - 1].y, null);
            mFlashClickedAtDrawOptionCounter++;
            h.postDelayed(flashClickedAtDrawOptionsRunnable, 400);
        }else{
            h.postDelayed(showGeofenceScreenAgainRunnable,400);
        }
    }

    private Runnable showGeofenceScreenAgainRunnable = new Runnable() {
        @Override
        public void run() {
            mTutorialType = TutorialType.DRAW_SHAPE_TUTORIAL;
            mAnimationType = AnimationType.SHOW_GEOFENCE_SCREEN_AGAIN;
            invalidate();
        }
    };

    private void setShowDrawOptionScreen(){
        this.setBackgroundResource(R.drawable.draw_option_screen);
        h.postDelayed(moveHandToDrawOptionRunnable,400);
    }

    private Runnable moveHandToDrawOptionRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.MOVE_HAND_TO_DRAW_OPTION;
            invalidate();
        }
    };


    private int moveHandToDrawOptionCounter = 0;
    private void moveHandToDrawOption(Canvas canvas,BitmapDrawable img,TutorialPoint[] points){
        if (moveHandToDrawOptionCounter++ < getIterationLength(points)-1) {
            canvas.drawBitmap(img.getBitmap(), points[moveHandToDrawOptionCounter].x, points[moveHandToDrawOptionCounter].y, null);
            h.postDelayed(moveHandToDrawOptionRunnable, 50);
        }else{
            h.postDelayed(flashClickedAtDrawOptionsRunnable,100);
        }
    }

    private Runnable flashClickedAtDrawOptionsRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.FLASH_CLICKED_AT_DRAW_OPTION;
            invalidate();
        }
    };

    private void moveHandToGeofenceFrag(Canvas canvas, BitmapDrawable img,  TutorialPoint[] points) {
//     System.out.println("inside moveHandToGeofenceFrag "+mCounter);
        if (mCounter++ < getIterationLength(points)-1) {
//            block which draws shape
            canvas.drawBitmap(img.getBitmap(), points[mCounter].x, points[mCounter].y, null);
            h.postDelayed(goToGeoFenceFragOptionRunnable, 50);
        }else{
            h.postDelayed(flashClickedRunnable,100);
        }
    }

//    private void moveHandToGeofenceFrag(Canvas canvas, BitmapDrawable img,  TutorialPoint[] points) {
////     System.out.println("inside moveHandToGeofenceFrag "+mCounter);
//        if (mCounter++ < getIterationLength(points)-1) {
////            block which draws shape
//            canvas.drawBitmap(img.getBitmap(), points[mCounter].x, points[mCounter].y, null);
//            if(mAnimationType == AnimationTypeNew.MOVE_HAND_TO_ADD_BUTTON){
//                h.postDelayed(moveHandToAddButtonRunnable,10);
//            }else{
//                h.postDelayed(goToGeoFenceFragOptionRunnable, 50);
//            }
//
//        }else{
//            if(mAnimationType == AnimationTypeNew.MOVE_HAND_TO_ADD_BUTTON){
//               h.postDelayed(flashClickedAtAddButtonRunnable,100);
//            }else {
//                h.postDelayed(flashClickedRunnable, 100);
//            }
//            mCounter = 0;
//        }
//    }

    private Runnable flashClickedAtAddButtonRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.FLASH_CLICKED_AT_ADD_BUTTON;
            invalidate();
        }
    };

    private Runnable flashClickedRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.FLASH_CLICKED;
            invalidate();
        }
    };

    private Runnable goToGeoFenceFragOptionRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

//    private int mFlashCounter = 0;

    private void flashCircle(Canvas canvas, BitmapDrawable img, TutorialPoint[] points) {
        if (mFlashCounter == 0 || mFlashCounter == 2 || mFlashCounter == 4 || mFlashCounter == 6 || mFlashCounter == 8
                || mFlashCounter == 10 || mFlashCounter == 12) {
//            canvas.drawBitmap(img.getBitmap(), points[getIterationLength(dragHandToNavigationDrawer) - 1].x, points[getIterationLength(dragHandToNavigationDrawer) - 1].y, null);
            showSelectedAnim(canvas, img, points);
            if (mFlashCounter < 12) {
                mFlashCounter++;
                h.postDelayed(flashRunnable, 200);
            } else {
                if(mTutorialType == TutorialType.HOME_SCREEN_TUTORIAL) {
                    mAnimationType = AnimationType.SHOW_RIGHT_ARROW;
                    mFlashCounter = 0;
                    h.postDelayed(rightArrowRunnable, 500);
                }else{
                    mFlashCounter = 0;
                    h.postDelayed(retainHandRunnable,500);
                }
            }
        } else if (mFlashCounter == 1 || mFlashCounter == 3 || mFlashCounter == 5 || mFlashCounter == 7 || mFlashCounter == 9
                || mFlashCounter == 11) {
            canvas.drawBitmap(img.getBitmap(), points[getIterationLength(mDragHandToNavigationDrawer) - 1].x, points[getIterationLength(mDragHandToNavigationDrawer) - 1].y, null);
            mFlashCounter++;
            h.postDelayed(flashRunnable, 200);
        }

    }

    private Runnable retainHandRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.RETAIN_HAND;
            invalidate();
        }
    };

    private void showRightArrow(Canvas canvas, BitmapDrawable img) {
//        Display display = ((WindowManager)this.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float x = 5.0f;
        float y = this.getHeight() / 2;
        if (mFlashCounter == 0 || mFlashCounter == 2 || mFlashCounter == 4 || mFlashCounter == 6 || mFlashCounter == 8
                || mFlashCounter == 10 || mFlashCounter == 12) {
//            canvas.drawBitmap(img.getBitmap(), x, y, null);
            if (mFlashCounter < 12) {
                canvas.drawBitmap(img.getBitmap(), x, y, null);
                mFlashCounter++;
                h.postDelayed(rightArrowRunnable, 200);
            } else {
                mFlashCounter = 0;
                this.setBackgroundResource(R.drawable.navigation_drawer);
                if(mTutorialType.equals(TutorialType.HOME_SCREEN_TUTORIAL)){
                    h.postDelayed(greatJobRunnable, 3000);
                }else if(mTutorialType.equals(TutorialType.TO_GEO_FENCE_FRAGMENT_TUTORIAL)){
                    // bijesh
                    h.postDelayed(toGeofenceAlertsRunnable,2000);
                }

//                mAnimationType = AnimationTypeNew.SHOW_LEFT_ARROW;
            }

        } else if (mFlashCounter == 1 || mFlashCounter == 3 || mFlashCounter == 5 || mFlashCounter == 7 || mFlashCounter == 9
                || mFlashCounter == 11) {
            mFlashCounter++;
            h.postDelayed(rightArrowRunnable, 200);
        }
    }

    private Runnable toGeofenceAlertsRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimationType = AnimationType.MOVE_HAND_TO_GEOFENCE_FRAGMENT;
            invalidate();
        }
    };


    private boolean isFlashing = true;
    private void showRightArrowAtPosition(Canvas canvas,BitmapDrawable img,TutorialPoint point){
        if(isFlashing) {
            canvas.drawBitmap(img.getBitmap(), point.x, point.y, null);
            isFlashing = false;
        }else{
            isFlashing = true;
        }
    }

    private void showLeftArrowAtPosition(Canvas canvas,BitmapDrawable img,TutorialPoint point){
        if(isFlashing) {
            canvas.drawBitmap(img.getBitmap(), point.x, point.y, null);
            isFlashing = false;
        }else{
            isFlashing = true;
        }
    }

    private Runnable greatJobRunnable = new Runnable() {
        @Override
        public void run() {
            showToastMessage("Good one huh!!!",R.drawable.smiley_hi,true);
            h.postDelayed(slideLeftMessageRunnable, 5000);
        }
    };

    private Runnable slideLeftMessageRunnable = new Runnable() {
        @Override
        public void run() {
            showToastMessage("Now slide left to close the navigation drawer",R.drawable.smiley_hi,true);
            h.postDelayed(leftArrowRunnable, 3000);
            mAnimationType = AnimationType.SHOW_LEFT_ARROW;
        }
    };

    private void showLeftArrow(Canvas canvas, BitmapDrawable drawable) {
// x 663.0791 y 549.45667
        float x = this.getWidth() - 100;
        float y = this.getHeight() / 2;
        if (mFlashCounter == 0 || mFlashCounter == 2 || mFlashCounter == 4 || mFlashCounter == 6 || mFlashCounter == 8
                || mFlashCounter == 10 || mFlashCounter == 12) {
//            canvas.drawBitmap(img.getBitmap(), x, y, null);
            if (mFlashCounter < 12) {
                canvas.drawBitmap(drawable.getBitmap(), x, y, null);
                mFlashCounter++;
                h.postDelayed(leftArrowRunnable, 200);
            } else {
                mFlashCounter = 0;
                this.setBackgroundResource(R.drawable.home_screen);
                h.postDelayed(slideLeftOrRightMessageRunnable, 3000);//getStartToGeoFenceFragmentTutorialMessage
//                h.postDelayed(getStartToGeoFenceFragmentTutorialMessage, 3000);
//                mAnimationType = AnimationTypeNew.SHOW_INDICATORS;
            }

        } else if (mFlashCounter == 1 || mFlashCounter == 3 || mFlashCounter == 5 || mFlashCounter == 7 || mFlashCounter == 9
                || mFlashCounter == 11) {
            mFlashCounter++;
            h.postDelayed(leftArrowRunnable, 200);
        }
    }

    private Runnable slideLeftOrRightMessageRunnable = new Runnable() {
        @Override
        public void run() {
            showToastMessage("Slide right or left and navigate to other tabs, like this",R.drawable.smiley_hi,true);
            h.postDelayed(moveHandFromRightToCenterRunnable, 4000);
            mAnimationType = AnimationType.MOVE_HAND_FROM_RIGHT_TO_CENTER;
        }
    };

    Runnable moveHandsRightOrLeftRunnable = new Runnable() {
        @Override
        public void run() {

            invalidate();
        }
    };




    private void showIndicators(Canvas canvas, BitmapDrawable drawable) {
        float x = this.getWidth() / 2;
        float y = this.getHeight() / 2;
        if (mFlashCounter == 0 || mFlashCounter == 2 || mFlashCounter == 4 || mFlashCounter == 6 || mFlashCounter == 8
                || mFlashCounter == 10 || mFlashCounter == 12) {
//            canvas.drawBitmap(img.getBitmap(), x, y, null);
            if (mFlashCounter < 12) {
                canvas.drawBitmap(drawable.getBitmap(), x, y, null);
                mFlashCounter++;
                h.postDelayed(indicatorRunnable, 200);
            } else {
                mFlashCounter = 0;
//                this.setBackgroundResource(R.drawable.home_screen);
//                h.postDelayed(indicatorRunnable,5000);
//                mAnimationType = AnimationTypeNew.SHOW_INDICATORS;
            }

        } else if (mFlashCounter == 1 || mFlashCounter == 3 || mFlashCounter == 5 || mFlashCounter == 7 || mFlashCounter == 9
                || mFlashCounter == 11) {
            mFlashCounter++;
            h.postDelayed(indicatorRunnable, 200);
        }
    }


    private void showSelectedAnim(Canvas canvas, BitmapDrawable img, TutorialPoint[] points) {
        canvas.drawBitmap(img.getBitmap(), points[getIterationLength(mDragHandToNavigationDrawer) - 1].x, points[getIterationLength(mDragHandToNavigationDrawer) - 1].y, null);
        canvas.drawCircle(points[getIterationLength(mDragHandToNavigationDrawer) - 1].x, points[getIterationLength(mDragHandToNavigationDrawer) - 1].y, 20f, getMarkerPaint(true));

    }

    private Runnable indicatorRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    private Runnable leftArrowRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    private Runnable rightArrowRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    private Runnable flashRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    Runnable introMessageRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    private void endTutorial() {
        h.postDelayed(removeRunnable, 5000);
    }

    private Runnable removeRunnable = new Runnable() {
        @Override
        public void run() {
            DemoView.this.setVisibility(View.GONE);
        }
    };


    private String getMessage(boolean isCircle) {
        StringBuilder builder = new StringBuilder();
//        "The Shape is Incorrect, it should be closer to circle"
        if (!isCircle) {
            builder.append("The Shape is Incorrect,").append("\r\n").append("it should be closer to circle");
        } else {
            builder.append("The Shape is closer to circle").append("\r\n").append("Great job.");
        }
        return builder.toString();
    }

    private String getMessage1(boolean isCircle) {
        if (!isCircle) {
            return Html.fromHtml("The Shape is Incorrect,<br />it should be closer to circle").toString();
        } else {
            return Html.fromHtml("The Shape is closer to circle,<br />Great job.").toString();
        }
    }

//    private void showToastMessage(String message,boolean isCircle){
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        Toast toast = new Toast(mContext);
//        View toastView = inflater.inflate(R.layout.toast_layout,null);
//        ImageView imgView = (ImageView)toastView.findViewById(R.id.toast_image);
//        TextView txtView = (TextView)toastView.findViewById(R.id.toast_txt_message);
//        if(!isCircle)
//            imgView.setBackgroundResource(R.drawable.wrong);
//        else
//            imgView.setBackgroundResource(R.drawable.rightshape);
//        txtView.setText(message);
//        toast.setView(toastView);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.show();
//        //          toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
////                0, 0);
//    }

    private void showMessage(Canvas canvas, boolean show, String message, boolean color) {

//        if(show) {
        canvas.drawText(message, 216f, 945f, getTextPaint(!color));
//            showNonCircleMessage = false;
//        }
    }


    private TutorialPoint getShapePoints(boolean isCircle, int whichOrdinal) {
        if (isCircle) {
            return mThisRegularCircle[whichOrdinal];
        } else {
            return mThisIrregularCircle[whichOrdinal];
        }
    }

    private TutorialPoint getShapePoints(TutorialPoint[] points, int whichOrdinal) {
        return points[whichOrdinal];
    }


    private float[] getStartPoints(boolean isCircle) {
        float[] startXY = new float[2];
        if (isCircle) {
            startXY[0] = mThisRegularCircle[0].x;
            startXY[1] = mThisRegularCircle[0].y;
        } else {
            startXY[0] = mThisIrregularCircle[0].x;
            startXY[1] = mThisIrregularCircle[0].y;
        }
        return startXY;
    }

    private float[] getStartPoints(TutorialPoint[] points) {
        float[] startXY = new float[2];

        startXY[0] = points[0].x;
        startXY[1] = points[0].y;
        return startXY;
    }

/*
    private void appendRegularCirclePoints() {
        if (mCounter < getIterationLength(mThisRegularCircle))
            mListTutorialPoints.add(mThisRegularCircle[mCounter++]);
    }

    private void appendIrregularCirclePoints() {
        if (mCounter < getIterationLength(mThisIrregularCircle))
            mListTutorialPoints.add(mThisIrregularCircle[mCounter++]);
    }
*/
    private void appendPoints(TutorialPoint[] points) {
        if (mCounter < getIterationLength(points)) {
            mListTutorialPoints.add(points[mCounter++]);
        }
    }


    private int getIterationLength(TutorialPoint[] arr) {
        return arr.length;
    }

    private int getIterationLengthOne(boolean isCircle) {
        if (isCircle)
            return mThisRegularCircle.length;
        else
            return mThisIrregularCircle.length;
    }


    private void showToastMessage(String message, int imageId,boolean isCenter) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        Toast toast = new Toast(mContext);
        View toastView = inflater.inflate(R.layout.toast_layout, null);
        ImageView imgView = (ImageView) toastView.findViewById(R.id.toast_image);
        TextView txtView = (TextView) toastView.findViewById(R.id.toast_txt_message);
        imgView.setBackgroundResource(imageId);

        txtView.setText(message);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        if(isCenter) {
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
        }
    }

    private void showToastMessage(String message, int imageId,boolean isCenter,Runnable nextRunnable) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        Toast toast = new Toast(mContext);
        View toastView = inflater.inflate(R.layout.toast_layout, null);
        ImageView imgView = (ImageView) toastView.findViewById(R.id.toast_image);
        TextView txtView = (TextView) toastView.findViewById(R.id.toast_txt_message);
        imgView.setBackgroundResource(imageId);

        txtView.setText(message);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        if(isCenter) {
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
        }
        h.postDelayed(nextRunnable,6000);
    }




    private Paint getLinePaint(boolean isCircle) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        if (isCircle) {
            paint.setColor(Color.parseColor("#339933"));
        } else {
            paint.setColor(Color.parseColor("#CD5C5C"));
        }
        paint.setStrokeWidth(6f);
        return paint;
    }

    private Paint getMarkerPaint(boolean isFill) {
        Paint paint = new Paint();
        if(isFill) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }else{
            paint.setStyle(Paint.Style.STROKE);
        }
//        if(isCircle) {
        paint.setColor(Color.parseColor("#FFFFFF"));//339933
//        }else{
//            paint.setColor(Color.parseColor("#CD5C5C"));
//        }
        paint.setStrokeWidth(6f);
        return paint;
    }

    private Paint getTextPaint(boolean color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        if (color) {
            paint.setColor(Color.parseColor("#339933"));
        } else {
            paint.setColor(Color.parseColor("#CD5C5C"));
        }
        paint.setStrokeWidth(3f);
        paint.setTextSize(35f);
        return paint;
    }

    private Paint getTextPaint1(){
//
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#0033FF"));
        paint.setStrokeWidth(3f);
        paint.setTextSize(35f);
        return paint;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }

    class Pt {
        int x, y;

        Pt(int _x, int _y) {
            x = _x;
            y = _y;
        }
    }

//    class TutorialPoint{
//        float x,y;
//        TutorialPoint(float x,float y){
//            this.x = x;
//            this.y = y;
//        }
//    }

    public DemoView(Context context) {
        super(context);
        init();
    }


    /**
     * Use this method to move hand from one point to another
     *
     * @param canvas
     * @param hand
     * @param points
     * @param moveRunnable
     * @param nextRunnable
     */
    private void moveHand(Canvas canvas,BitmapDrawable hand,TutorialPoint[] points,Runnable moveRunnable,Runnable nextRunnable){
        if (moveHandCounter++ < getIterationLength(points)-1) {
            canvas.drawBitmap(hand.getBitmap(), points[moveHandCounter].x, points[moveHandCounter].y, null);
            h.postDelayed(moveRunnable, 5);
        }else{
            h.postDelayed(nextRunnable,100);
            moveHandCounter = 0;
        }
    }



    /**
     * Use this method to show the message on the canvas. his method will calculate its position dynamically to show on center of the screen.
     * @param canvas
     * @param message
     * @param thisRunnable
     * @param nextRunnable
     */
    private void showMessage(Canvas canvas,String message,Runnable thisRunnable,Runnable nextRunnable){

        if(messageCount++ < 50){
            float centerX = getStringPosition(message);
            canvas.drawText(message, centerX, getHeight() / 2, getTextPaint(message));
            h.postDelayed(thisRunnable,100);
        }else {
            messageCount = 0;
            h.postDelayed(nextRunnable, 3000);
        }
    }

    private Paint getTextPaint(String message){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#006600"));
        paint.setStrokeWidth(6f);
        paint.setTextSize(60f);
        return paint;
    }



    private float getStringPosition(String message){
        Paint paint = getTextPaint1();
        float x = getWidth();
        float txtLen = paint.measureText(message);
        float centerX = getWidth() / 2;
//        System.out.println("x "+x+" txtLen "+txtLen+" cenX "+centerX);
        if(txtLen < x){
            float diff = x - txtLen;
            centerX = diff/2;
        }else
            centerX = 10;
//        System.out.println("cen x now "+centerX);
        return centerX;
    }


}
