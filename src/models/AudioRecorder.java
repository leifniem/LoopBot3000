package models;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
	private final static int RECORDING_TIMEOUT_IN_SECONDS = 10;

	private DataLine.Info info;
	private TargetDataLine targetLine = null;
	private boolean isRecording = false;
	private Timer timeoutTimer;

	public AudioRecorder() {
		AudioFormat format = getDefaultAudioFormat();
		info = new DataLine.Info(TargetDataLine.class, format);
		timeoutTimer = new Timer();

		if (!AudioSystem.isLineSupported(info)) {
			throw new UnsupportedOperationException("line not supported!");
		}
	}

	public void startRecording() {
		try {
			startTargetLine();
			startRecordingThread();
			startRecordingTimeoutThread();
			isRecording = true;

			System.out.println("Start recording...");
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}
	}

	private void startTargetLine() throws LineUnavailableException {
		targetLine = (TargetDataLine) AudioSystem.getLine(info);
		targetLine.open();
		targetLine.start();
	}

	private void startRecordingThread() {
		Thread recordingThread = getRecordingThread(targetLine);
		recordingThread.start();
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

	private void startRecordingTimeoutThread() {
		timeoutTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				stopRecording();
			}
		}, RECORDING_TIMEOUT_IN_SECONDS * 1000);
	}

	private AudioFormat getDefaultAudioFormat() {
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLERATE, SAMPLE_SIZE_IN_BITS, STEREO,
				FRAME_SIZE_IN_BYTES, FRAMERATE, false);
		return format;
	}

	public void stopRecording() {
		if (isRecording) {
			timeoutTimer.cancel();
			targetLine.stop();
			targetLine.close();
			isRecording = false;
		}
	}
}