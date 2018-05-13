package edu.ucsc.cordz;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.SparseArray;

import java.io.InputStream;

public class AudioSoundPlayer {

    private SparseArray<PlayThread> threadMap = null;
    private Context context;
    private static final SparseArray<String> SOUND_MAP = new SparseArray<>();
    public static final int MAX_VOLUME = 100, CURRENT_VOLUME = 90;

    static{
        SOUND_MAP.put(1, "Piano.ff.A0");
        SOUND_MAP.put(2, "Piano.ff.A1");
        SOUND_MAP.put(3, "Piano.ff.A2");
        SOUND_MAP.put(4, "Piano.ff.A3");
        SOUND_MAP.put(5, "Piano.ff.A4");
        SOUND_MAP.put(6, "Piano.ff.A5");
        SOUND_MAP.put(7, "Piano.ff.A6");
        SOUND_MAP.put(8, "Piano.ff.A7");
        SOUND_MAP.put(9, "Piano.ff.B0");
        SOUND_MAP.put(10, "Piano.ff.B1");
        SOUND_MAP.put(11, "Piano.ff.B2");
        SOUND_MAP.put(12, "Piano.ff.B3");
        SOUND_MAP.put(13, "Piano.ff.B4");
        SOUND_MAP.put(14, "Piano.ff.B5");

        SOUND_MAP.put(15, "Piano.ff.Ab1");
        SOUND_MAP.put(16, "Piano.ff.Ab2");
        SOUND_MAP.put(17, "Piano.ff.Ab3");
        SOUND_MAP.put(18, "Piano.ff.Ab4");
        SOUND_MAP.put(19, "Piano.ff.Ab5");
        SOUND_MAP.put(20, "Piano.ff.Ab6");
        SOUND_MAP.put(21, "Piano.ff.Ab7");
        SOUND_MAP.put(22, "Piano.ff.Bb0");
        SOUND_MAP.put(23, "Piano.ff.Bb1");
        SOUND_MAP.put(24, "Piano.ff.Bb2");
    }

    public AudioSoundPlayer(Context context){
        this.context = context;
        threadMap = new SparseArray<>();
    }

    public void playNote(int note){
        if (!isNotePlaying(note)){
            PlayThread thread = new PlayThread(note);
            thread.start();
            threadMap.put(note, thread);
        }
    }

    public void stopNote(int note){
        PlayThread thread = threadMap.get(note);

        if(thread != null){
            threadMap.remove(note);
        }
    }

    public boolean isNotePlaying(int note){
        return threadMap.get(note) != null;
    }

    private class PlayThread extends Thread {
        int note;
        AudioTrack audioTrack;

        public PlayThread(int note){
            this.note = note;
        }

        @Override
        public void run() {
            try {
                String path = SOUND_MAP.get(note) + ".wav";
                AssetManager assetManager = context.getAssets();
                AssetFileDescriptor ad = assetManager.openFd(path);
                long fileSize = ad.getLength();
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];

                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);

                float logVolume = (float) (1 - (Math.log(MAX_VOLUME - CURRENT_VOLUME) / Math.log(MAX_VOLUME)));
                audioTrack.setStereoVolume(logVolume, logVolume);

                audioTrack.play();
                InputStream audioStream = null;
                int headerOffset = 0x2C;
                long bytesWritten = 0;
                int bytesRead = 0;

                audioStream = assetManager.open(path);
                audioStream.read(buffer, 0, headerOffset);

                while (bytesWritten < fileSize - headerOffset){
                    bytesRead = audioStream.read(buffer, 0, bufferSize);
                    bytesWritten += audioTrack.write(buffer, 0, bytesRead);
                }
                audioTrack.stop();
                audioTrack.release();

            } catch (Exception e) {

            }finally {
                if(audioTrack != null){
                    audioTrack.release();
                }
            }
            }
        }
}
