package inc.appscode0.actionbound;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;

public class TestLayouts extends AppCompatActivity implements EasyVideoCallback {
    VideoView videoView;
    private static final String TEST_URL = "http://192.168.43.180/ariana.mp4";
    private EasyVideoPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layouts);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.sda);
        RelativeLayout text=(RelativeLayout) LayoutInflater.from(TestLayouts.this).inflate(R.layout.layout_text, null);
        RelativeLayout image=(RelativeLayout) LayoutInflater.from(TestLayouts.this).inflate(R.layout.layout_image, null);
        RelativeLayout videolayout=(RelativeLayout) LayoutInflater.from(TestLayouts.this).inflate(R.layout.layout_video, null);
        RelativeLayout audio=(RelativeLayout) LayoutInflater.from(TestLayouts.this).inflate(R.layout.layout_audio, null);
        player = (EasyVideoPlayer)videolayout.findViewById(R.id.player);
        player.setCallback(this);
        player.setSource(Uri.parse(TEST_URL));
        linearLayout.addView(text);
        linearLayout.addView(videolayout);
        linearLayout.addView(image);
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {

    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

    }
}
