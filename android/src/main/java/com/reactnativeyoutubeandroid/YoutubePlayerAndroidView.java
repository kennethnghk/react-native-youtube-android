package com.reactnativeyoutubeandroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class YoutubePlayerAndroidView extends RelativeLayout {

    YoutubePlayerAndroidController youtubePlayerController;
    private YouTubePlayerFragment youtubePlayerFragment;
    public static String youtube_key;

    public YoutubePlayerAndroidView(ReactContext context) {
        super(context);
        init();
    }

    private ReactContext getReactContext() {
        return (ReactContext)getContext();
    }

    public void init() {
        inflate(getContext(), R.layout.youtube_player_layout, this);
        FragmentManager fragmentManager = getReactContext().getCurrentActivity().getFragmentManager();
        youtubePlayerFragment = (YouTubePlayerFragment) fragmentManager.findFragmentById(R.id.youtube_player_fragment);
        youtubePlayerController = new YoutubePlayerAndroidController(YoutubePlayerAndroidView.this);
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            FragmentManager fragmentManager = getReactContext().getCurrentActivity().getFragmentManager();
            youtubePlayerFragment = (YouTubePlayerFragment)
                    fragmentManager.findFragmentById(R.id.youtube_player_fragment);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(youtubePlayerFragment);
            ft.commit();
        } catch (Exception e) {
        }
        super.onDetachedFromWindow();
    }

    public void seekTo(int second) {
        youtubePlayerController.seekTo(second);
    }

    public void playerViewDidBecomeReady() {
        WritableMap event = Arguments.createMap();
        ReactContext reactContext = (ReactContext) getContext();
        event.putInt("target", getId());
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "ready", event);
    }

    public void didChangeToState(String param) {
        WritableMap event = Arguments.createMap();
        event.putString("state", param);
        event.putInt("target", getId());
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "state", event);
    }

    public void didChangeToQuality(String param) {
        WritableMap event = Arguments.createMap();
        event.putString("quality", param);
        event.putInt("target", getId());
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "quality", event);
    }

    public void didPlayTime(String current, String duration) {
        WritableMap event = Arguments.createMap();
        event.putString("currentTime", current);
        event.putString("duration", duration);
        event.putInt("target", getId());
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "progress", event);
    }

    public void receivedError(String param) {
        WritableMap event = Arguments.createMap();
        ReactContext reactContext = (ReactContext) getContext();
        event.putString("error", param);
        event.putInt("target", getId());
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "error", event);
    }

    public void setVideoId(String str) {
        youtubePlayerController.setVideoId(str);
    }

    public void setInline(Boolean bool) {
        youtubePlayerController.setPlayInline(bool);
    }

    public void setShowInfo(Boolean bool) {
        youtubePlayerController.setShowInfo(bool);
    }

    public void setModestbranding(Boolean bool) {
        youtubePlayerController.setModestBranding(bool);
    }

    public void setControls(Integer nb) {
        youtubePlayerController.setControls(nb);
    }

    public void setPlay(Boolean bool) {
        youtubePlayerController.setPlay(bool);
    }

    public void setHidden(Boolean bool) {
        youtubePlayerController.setHidden(bool);
    }

    public void setApiKey(String apiKey) {
        youtube_key = apiKey;
        youtubePlayerFragment.initialize(youtube_key, youtubePlayerController);
    }

    public void setLoop(Boolean loop) {
        youtubePlayerController.setLoop(loop);
    }

    public void setRelated(Boolean related) {
        youtubePlayerController.setRelated(related);
    }

    public void setFullscreen(Boolean bool) {
        youtubePlayerController.setFullscreen(bool);
    }
}