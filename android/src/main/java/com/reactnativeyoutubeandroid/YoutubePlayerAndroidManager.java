package com.reactnativeyoutubeandroid;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class YoutubePlayerAndroidManager extends SimpleViewManager<YoutubePlayerAndroidView> {
    private final static String REACT_CLASS = "YoutubePlayerAndroid";
    private static final String TAG = "YoutubePlayerAndroid";

    public YoutubePlayerAndroidView youtubePlayerAndroidView;

    public static final String PROP_VIDEO_ID = "videoId";
    public static final String PROP_API_KEY = "apiKey";
    public static final String PROP_INLINE = "playsInline";
    public static final String PROP_SHOW_INFO = "showinfo";
    public static final String PROP_MODESTBRANDING = "modestbranding";
    public static final String PROP_CONTROLS = "controls";
    public static final String PROP_PLAY = "play";
    public static final String PROP_HIDDEN = "hidden";
    public static final String PROP_REL = "rel";
    public static final String PROP_LOOP = "loop";
    public static final String PROP_FULLSCREEN = "fs";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected YoutubePlayerAndroidView createViewInstance(ThemedReactContext themedReactContext) {
        Log.i(TAG, "at createViewInstance");
        return new YoutubePlayerAndroidView(themedReactContext);
    }

    @Override
    public
    @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                "error",
                MapBuilder.of("registrationName", "onYoutubeVideoError"),
                "ready",
                MapBuilder.of("registrationName", "onYoutubeVideoReady"),
                "state",
                MapBuilder.of("registrationName", "onYoutubeVideoChangeState"),
                "quality",
                MapBuilder.of("registrationName", "onYoutubeVideoChangeQuality")
        );
    }

    @ReactMethod
    public void seekTo(Integer seconds) {
        youtubePlayerAndroidView.seekTo(seconds);
    }

    @ReactProp(name = PROP_VIDEO_ID)
    public void setPropVideoId(
            YoutubePlayerAndroidView view, @Nullable String param) {
        //Log.e(PROP_VIDEO_ID,""+param);
        Log.i(TAG, "at setPropVideoId "+param);
        view.setVideoId(param);
    }

    @ReactProp(name = PROP_API_KEY)
    public void setApiKey(
            YoutubePlayerAndroidView view, @Nullable String param) {
        //Log.e(PROP_API_KEY,""+param);

        Log.i(TAG, "at setPropApiKey "+param);
        view.setApiKey(param);
    }

    @ReactProp(name = PROP_PLAY)
    public void setPropPlay(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        Log.e(PROP_PLAY,""+param);
        view.setPlay(param);
    }

    @ReactProp(name = PROP_HIDDEN)
    public void setPropHidden(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        //Log.e(PROP_HIDDEN,""+param);
        view.setHidden(param);
    }

    @ReactProp(name = PROP_INLINE)
    public void setPropInline(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        //Log.e(PROP_INLINE,""+param);
        view.setInline(param);
    }

    @ReactProp(name = PROP_REL)
    public void setPropRel(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        //Log.e(PROP_REL,""+param);
        view.setRelated(param);
    }

    @ReactProp(name = PROP_MODESTBRANDING)
    public void setPropModestbranding(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        //Log.e(PROP_MODESTBRANDING,""+param);
        view.setModestbranding(param);
    }

    @ReactProp(name = PROP_LOOP)
    public void setPropLoop(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        //Log.e(PROP_LOOP,""+param);
        view.setLoop(param);
    }

    @ReactProp(name = PROP_CONTROLS)
    public void setPropControls(
            YoutubePlayerAndroidView view, @Nullable Integer param) {
        //Log.e(PROP_CONTROLS,""+param);
        view.setControls(param);
    }

    @ReactProp(name = PROP_SHOW_INFO)
    public void setPropShowInfo(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        //Log.e(PROP_SHOW_INFO,""+param);
        view.setShowInfo(param);
    }

    @ReactProp(name = PROP_FULLSCREEN)
    public void setPropFullscreen(
            YoutubePlayerAndroidView view, @Nullable Boolean param) {
        //Log.e(PROP_FULLSCREEN,""+param);
        view.setFullscreen(param);
    }
}
