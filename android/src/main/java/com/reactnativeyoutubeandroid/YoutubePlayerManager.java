package com.reactnativeyoutubeandroid;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class YoutubePlayerManager extends SimpleViewManager<YoutubePlayerView> {
    private final static String REACT_CLASS = "YoutubePlayerAndroid";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected YoutubePlayerView createViewInstance(ThemedReactContext themedReactContext) {
        return new YoutubePlayerView(themedReactContext);
    }

    @ReactProp(name = "text")
    public void propSetText(YoutubePlayerView view, String text) {
        view.setText(text);
    }
}
