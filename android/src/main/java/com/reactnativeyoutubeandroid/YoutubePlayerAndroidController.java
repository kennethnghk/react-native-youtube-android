package com.reactnativeyoutubeandroid;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class YoutubePlayerAndroidController implements
        YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener, YouTubePlayer.OnFullscreenListener {

    String videoId = null;

    YouTubePlayer youtubePlayer;
    YoutubePlayerAndroidView youtubePlayerView;

    private boolean isLoaded = false;
    private boolean play = false;
    private boolean hidden = false;
    private boolean related = false;
    private boolean modestBranding = false;
    private int controls = 1;
    private boolean showInfo = true;
    private boolean loop = false;
    private boolean playInline = false;
    private boolean fullscreen = true;


    public YoutubePlayerAndroidController(YoutubePlayerAndroidView youtubePlayerView) {
        this.youtubePlayerView = youtubePlayerView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {

            // Intall listeners on the youtube player
            youtubePlayer = youTubePlayer;
            youtubePlayer.setPlayerStateChangeListener(this);
            youtubePlayer.setPlaybackEventListener(this);
            youtubePlayer.setOnFullscreenListener(this);

            // Update config
            youtubePlayer.setShowFullscreenButton(fullscreen);

            // Emit 'onReady' event for player
            youtubePlayerView.playerViewDidBecomeReady();
            setLoaded(true);

            // Load/start the video in case it was initially provided
            if (videoId != null) {
                if (isPlay()) {
                    youtubePlayer.loadVideo(videoId);
//                    if (!isPlayInline()) {
//                        youtubePlayer.setFullscreen(true);
//                    }
                    youtubePlayer.setFullscreen(true);
                    youtubePlayer.play();
                }
                else {
                    youtubePlayer.cueVideo(videoId);
                }
            }
            updateControls();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        youtubePlayerView.receivedError(youTubeInitializationResult.toString());
    }
    
    @Override
    public void onPlaying() {
        youtubePlayerView.didChangeToState("playing");

        // When inline playback is not allowed, transition the
        // player to full-screen.
        if (!isPlayInline()) {
            youtubePlayer.setFullscreen(true);
        }
    }

    @Override
    public void onPaused() {
        youtubePlayerView.didChangeToState("paused");
    }

    @Override
    public void onStopped() {
        youtubePlayerView.didChangeToState("stopped");
    }


    @Override
    public void onBuffering(boolean b) {
        if (b)
            youtubePlayerView.didChangeToState("buffering");

        //Trick to remove when YouTube will patch it
        ProgressBar progressBar;
        try {
            // As of 2016-02-16, the ProgressBar is at position 0 -> 3 -> 2 in the view tree of the Youtube Player Fragment
            ViewGroup child1 = (ViewGroup)youtubePlayerView.getChildAt(0);
            ViewGroup child2 = (ViewGroup)child1.getChildAt(3);
            progressBar = (ProgressBar)child2.getChildAt(2);
        } catch (Throwable t) {
            // As its position may change, we fallback to looking for it
            progressBar = findProgressBar(youtubePlayerView);
        }

        int visibility = b ? View.VISIBLE : View.INVISIBLE;
        if (progressBar != null) {
            progressBar.setVisibility(visibility);
        }
    }

    private ProgressBar findProgressBar(View view) {
        if (view instanceof ProgressBar) {
            return (ProgressBar)view;
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                ProgressBar res = findProgressBar(viewGroup.getChildAt(i));
                if (res != null) return res;
            }
        }
        return null;
    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {
        youtubePlayerView.didChangeToState("loading");
    }

    @Override
    public void onLoaded(String s) {
        youtubePlayerView.didChangeToState("loaded");
    }

    @Override
    public void onAdStarted() {
        youtubePlayerView.didChangeToState("adStarted");
    }

    @Override
    public void onVideoStarted() {
        youtubePlayerView.didChangeToState("videoStarted");
    }

    @Override
    public void onVideoEnded() {
        youtubePlayerView.didChangeToState("ended");
        if (isLoop()) {
            youtubePlayer.loadVideo(videoId);
            youtubePlayer.play();
        }
        else {
            youtubePlayer.setFullscreen(false);
        }
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        youtubePlayerView.receivedError(errorReason.toString());
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        youtubePlayerView.didChangeToState(isFullscreen ? "fullscreenMode" : "windowMode");

        // When exiting full-screen mode and inline playback is not enabled
        // then pause the video playback.
        if (!isPlayInline() && !isFullscreen) {
            youtubePlayer.pause();
        }
    }

    public void seekTo(int second) {
        if (isLoaded()) {
            youtubePlayer.seekToMillis(second * 1000);
        }
    }

    public void updateControls() {
        switch (controls) {
            case 0:
                youtubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                break;
            case 1:
                youtubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                break;
            case 2:
                youtubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                break;
        }
    }


    /**
     * GETTER &SETTER
     **/

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     * PROPS
     */

    public void setVideoId(String str) {
        videoId = str;
        if (isLoaded()) {
            if (videoId == null) {
                youtubePlayer.pause();
            }
            else if (isPlay()) {
                youtubePlayer.loadVideo(videoId);
                youtubePlayer.play();
            }
            else {
                youtubePlayer.cueVideo(videoId);
            }
        }
    }

    public void setPlay(boolean play) {
        this.play = play;

        if (isLoaded()) {
            if (this.play && !youtubePlayer.isPlaying()) {
                youtubePlayer.play();
                if (!isPlayInline()) {
                    youtubePlayer.setFullscreen(true);
                }
            }
            else if (!this.play && youtubePlayer.isPlaying()){
                youtubePlayer.pause();
                youtubePlayer.setFullscreen(false);
            }
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setControls(Integer controls) {
        if (controls >= 0 && controls <= 2) {
            this.controls = Integer.valueOf(controls);
            if (isLoaded())
                updateControls();
        }
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        if (isLoaded()) {
            youtubePlayer.setShowFullscreenButton(fullscreen);
        }
    }

    //TODO
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    //TODO
    public void setShowInfo(boolean showInfo) {
        this.showInfo = showInfo;
    }

    //TODO
    public void setRelated(boolean related) {
        this.related = related;
    }

    //TODO
    public void setModestBranding(boolean modestBranding) {
        this.modestBranding = modestBranding;
    }

    //TODO
    public void setPlayInline(boolean playInline) {
        this.playInline = playInline;
    }


    public boolean isPlay() {
        return play;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isRelated() {
        return related;
    }

    public boolean isModestBranding() {
        return modestBranding;
    }

    public int getControls() {
        return controls;
    }

    public boolean isShowInfo() {
        return showInfo;
    }

    public boolean isLoop() {
        return loop;
    }

    public boolean isPlayInline() {
        return playInline;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }
}