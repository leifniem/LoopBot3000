package models;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class LoopProjectExporter {
	public static void exportLoopProject(LoopProject loopProject) {
		try {
			SimpleLoopProject simpleLoopProject = new SimpleLoopProject(loopProject);
			FileOutputStream fos = new FileOutputStream("blubb.xml");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			XMLEncoder encoder = new XMLEncoder(bos);
			encoder.writeObject(simpleLoopProject);
			encoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static LoopProject importLoopProject() {
		LoopProject loopProject = null;
		
		try {
			FileInputStream fis = new FileInputStream("blubb.xml");
			BufferedInputStream bis = new BufferedInputStream(fis);
			XMLDecoder decoder = new XMLDecoder(bis);
			SimpleLoopProject simpleLoopProject = (SimpleLoopProject) decoder.readObject();
			decoder.close();
			
			loopProject = generateLoopProjectForSimpleLoopProject(simpleLoopProject);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return loopProject;
	}

	private static LoopProject generateLoopProjectForSimpleLoopProject(SimpleLoopProject simpleLoopProject) {
		LoopProject loopProject;
		int numberOfBeats = simpleLoopProject.getNumberOfBeats();
		int noteValue = simpleLoopProject.getNoteValue();
		int tempo = simpleLoopProject.getTempo();
		TimeSignature timeSignature = new TimeSignature(numberOfBeats, noteValue, tempo);
		loopProject = new LoopProject(numberOfBeats, noteValue, tempo);
		loopProject.nameProperty().set(simpleLoopProject.getName());
		
		for(SimpleLoop simpleLoop : simpleLoopProject.getLoops()){
			Loop loop = generateLoopForSimpleLoop(timeSignature, simpleLoop);
			loopProject.addLoop(loop);
		}
		
		return loopProject;
	}

	private static Loop generateLoopForSimpleLoop(TimeSignature timeSignature, SimpleLoop simpleLoop) {
		Loop loop = new Loop(simpleLoop.getName(), timeSignature);
		loop.setSoundFile(simpleLoop.getSoundFilename());
		
		for(int i = 0; i < simpleLoop.getNoteStatus().size(); i++){
			boolean active = simpleLoop.getNoteStatus().get(i);
			loop.getNoteStatus().get(i).set(active);
		}
		
		return loop;
	}
}
