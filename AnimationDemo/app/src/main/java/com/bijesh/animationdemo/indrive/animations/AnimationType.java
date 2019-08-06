package com.bijesh.animationdemo.indrive.animations;

/**
 * Created by Bijesh on 20-09-2014.
 */
public enum AnimationType {

//    For home screen animations
    INTRO_MESSAGE,
    MESSAGE_MOVE_TO_NAVIGATION_DRAWER,
    TO_NAVIGATION_DRAWER,
    FLASH_CIRCLE_AT_NAVIGATION_DRAWER,
    FLASH_CIRCLE,
    SHOW_RIGHT_ARROW,
    SHOW_LEFT_ARROW,
//    MOVE_HAND_RIGHT_OR_LEFT,
    MOVE_HAND_FROM_RIGHT_TO_CENTER,
    MOVE_HAND_FROM_CENTER_TO_RIGHT,

//    for to geo fence fragment tutorial
    MESSAGE_START_GEO_FENCE_TUTORIAL,
//    TO NAVIGATION DRAWER
//    FLASH_CIRCLE
    RETAIN_HAND,
    MOVE_HAND_TO_GEOFENCE_FRAGMENT,
    FLASH_CLICKED,
    MOVE_HAND_TO_ADD_BUTTON,
    FLASH_CLICKED_AT_ADD_BUTTON,


//    for INSIDE_GEOFENCE_TUTORIAL
    SHOW_GEOFENCE_SCREEN,
    MOVE_HAND_TO_DRAW_BUTTON,
    FLASH_CLICKED_AT_DRAW_BUTTON,
    SHOW_DRAW_OPTION_SCREEN,
    MOVE_HAND_TO_DRAW_OPTION,
    FLASH_CLICKED_AT_DRAW_OPTION,

//   FOR DRAW_SHAPE_TUTORIAL
    SHOW_GEOFENCE_SCREEN_AGAIN,
    SHOW_MESSAGE_LEARN_DRAW_SHAPE,
    DRAW_INCORRECT_SHAPE_1,
    SHOW_MESSAGE_DOES_NOT_LOOK_LIKE_CIRCLE,
    DRAW_INCORRECT_SHAPE_2,
    SHOW_MESSAGE_STILL_DOEST_NOT_LOOK_LIKE_A_CIRCLE,
    DRAW_CORRECT_SHAPE,
    SHOW_MESSAGE_THAT_IS_PERFECT,

// FOR NAVIGATE_TO_DIAGNOSTICS_SCREEN
    GO_TO_DIAGNOSTICS_TAB,
    FLASH_CLICKED_AT_DIAGNOSTICS_TAB,
    SHOW_DIAGNOSTICS_SCREEN,
    RETAIN_HAND_POSITION,
    MOVE_HAND_TO_DIAGNOSTICS_NAVIGATION,
    FLASH_CLICKED_AT_DIAGNOSTICS_NAVIGATION,
    SHOW_DIAGNOSTICS_NAV_OPTION_SCREEN,
    RETAIN_HAND_AT_DIAGNOSTICS_NAVIGATION_OPTION,
    MOVE_HAND_TO_VEHICLE_HEALTH,
    FLASH_CLICKED_AT_VEHICLE_HEALTH,
    SHOW_VEHICLE_HEALTH_SCREEN,
    SHOW_MESSAGE_AT_VEHICLE_HEALTH_SCREEN,

//    FOR NAVIGATE_TO_DRIVING_DATA_SCREEN
    SHOW_MESSAGE_MOVE_TO_DRIVING_DATA_SCREEN,
    MOVE_HAND_TO_DRIVING_DATA_TAB,
    FLASH_CLICKED_AT_DRIVING_DATA_TAB,
    SHOW_DRIVING_DATA_SCREEN,
    MESSAGE_AT_DRIVING_DATA,

    SHOW_MESSAGE_END_OF_TUTORIAL,

    SHOW_INDICATORS
}
