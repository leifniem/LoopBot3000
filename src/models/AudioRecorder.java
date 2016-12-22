package models;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {
	private final static int SAMPLERATE = 44100;
	private final static int SAMPLE_SIZE_IN_BITS = 16;
	private final static int STEREO = 2;
	private final static int FRAME_SIZE_IN_BYTES = 4;
	private final static int FRAMERATE = 44100;
	private final static String OUTPUT_PATH = "";

	public void record() {
		try {
			AudioFormat format = getDefaultAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("Line not supported!");
			}

			final TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();

			System.out.println("Start recording...");
			targetLine.start();

			Thread recordingThread = getRecordingThread(targetLine);
			recordingThread.start();
			Thread.sleep(5000);
			targetLine.stop();
			targetLine.close();
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	private Thread getRecordingThread(final TargetDataLine targetLine) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				AudioInputStream audioStream = new AudioInputStream(targetLine);
				File audioFile = new File(OUTPUT_PATH + "record.wav");
				try {
					AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, audioFile);
					System.out.println("Stopped recording...");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		return thread;
	}

	private AudioFormat getDefaultAudioFormat() {
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLERATE, SAMPLE_SIZE_IN_BITS, STEREO,
				FRAME_SIZE_IN_BYTES, FRAMERATE, false);
		return format;
	}
}