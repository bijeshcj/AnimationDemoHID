package com.bijesh.animationdemo.animate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bijesh C J on 06,August,2019
 */
public class AnimationUtils {

        public static AnimatePoint[] getVerifierUpwardMovementPoints(float startX,float startY){
            AnimatePoint startPoint = new AnimatePoint(startX,startY);
            AnimatePoint[] returnPoints = new AnimatePoint[100];
            for(int i =0;i<returnPoints.length;i++){
                returnPoints[i] = new AnimatePoint((startX-70f),startY);
                startY = startY - 5;
            }
            return returnPoints;
        }

        public static AnimatePoint[] getCircleDownwardMovementPoints(float startX,float startY){
            AnimatePoint[] returnPoints = new AnimatePoint[100];
            for(int i =0;i<returnPoints.length;i++){
                returnPoints[i] = new AnimatePoint((startX-70f),startY);
                startY = startY + 5;
            }
            return returnPoints;
        }



}
