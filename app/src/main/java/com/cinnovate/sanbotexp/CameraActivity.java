package com.cinnovate.sanbotexp;

import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.widget.VideoView;

import com.qihancloud.opensdk.function.unit.MediaManager;
import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.StreamOption;
import com.sanbot.opensdk.function.unit.interfaces.media.MediaStreamListener;

import java.io.IOException;
import java.nio.ByteBuffer;

public class CameraActivity extends BindBaseActivity implements SurfaceHolder.Callback {
    VideoView videoView;
    MediaCodec videoDecoder;
    ByteBuffer[] videoInputBuffers;
    final static String videoMimeType = "video/avc";
    final String TAG = getClass().getName();
    AudioTrack audioTrack;
    Surface surface;
    MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
    long decodeTimeout = 16000;
    int i = 0;

    MediaManager mediaManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(CameraActivity.class);
        super.onCreate(savedInstanceState);
        onMainServiceConnected();
        setContentView(R.layout.camera_activity);
        videoView = findViewById(R.id.stream_video);

        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);

        mediaManager.setMediaListener(new MediaStreamListener() {
            @Override
            public void getVideoStream(byte[] data) {
                drawVideoSample(ByteBuffer.wrap(data));
            }

            @Override
            public void getAudioStream(byte[] data) {

            }
        });
        videoView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surface = holder.getSurface();
        startDecoding(1280, 720);
        StreamOption streamOption = new StreamOption();
        streamOption.setChannel(StreamOption.SUB_STREAM);
        String result = mediaManager.openStream(streamOption).getResult();
        Log.e("result", result);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mediaManager.closeStream();
        stopDecoding();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void drawVideoSample(ByteBuffer sampleData) {

        try {
            // put sample data
            int inIndex = videoDecoder.dequeueInputBuffer(decodeTimeout);
            if (inIndex >= 0) {
                ByteBuffer buffer = videoInputBuffers[inIndex];
                int sampleSize = sampleData.limit();
                buffer.clear();
                buffer.put(sampleData);
                buffer.flip();
                // Log.i("DecodeActivity", "" + buffer.toString());
                videoDecoder.queueInputBuffer(inIndex, 0, sampleSize, 0, 0);
            }
            // output, 1 microseconds = 100,0000 / 1 second
            int ret = videoDecoder.dequeueOutputBuffer(videoBufferInfo, decodeTimeout);
            if (ret < 0) {
                onDecodingError(ret);
                return;
            }
            videoDecoder.releaseOutputBuffer(ret, true);
        } catch (Exception e) {
            Log.e("abc", "chinese", e);
        }
    }

    private boolean startDecoding(int width, int height) {
        try {
            if (videoInputBuffers != null) {

                return false;

            } else if (videoDecoder != null) {

                return false;

            }
            // format
            MediaFormat format = MediaFormat.createVideoFormat(
                    videoMimeType, width, height);
            Log.i("abc", "" + format);

            videoDecoder = MediaCodec.createDecoderByType(videoMimeType);
            videoDecoder.configure(format, surface, null, 0);
            videoDecoder.start();

            videoInputBuffers = videoDecoder.getInputBuffers();
        } catch (IOException e) {

        } finally {
            Log.e("CODEC", "onCreateCodec");
        }
        return true;
    }

    @Override
    protected void onMainServiceConnected() {

    }


    private void onDecodingError(int index) {
        switch (index) {
            case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                Log.e("abc", "onDecodingError: The output buffers have changed");
                // The output buffers have changed, the client must refer to the
                // new
                // set of output buffers returned by getOutputBuffers() from
                // this
                // point on.
                // outputBuffers = decoder.getOutputBuffers();
                break;

            case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                Log.d("abc", "New format: " + videoDecoder.getOutputFormat());
                // The output format has changed, subsequent data will follow
                // the
                // new format. getOutputFormat() returns the new format.
                break;

            case MediaCodec.INFO_TRY_AGAIN_LATER:
                Log.d("abc", "dequeueOutputBuffer timed out!");
                // If a non-negative timeout had been specified in the call to
                // dequeueOutputBuffer(MediaCodec.BufferInfo, long), indicates
                // that
                // the call timed out.
                break;

            default:
                break;
        }
    }

    public void stopDecoding() {
        if (videoDecoder != null) {
            videoDecoder.stop();
            videoDecoder.release();
            videoDecoder = null;
            Log.i(TAG, "stopDecoding");
        }
        videoInputBuffers = null;
    }

}
