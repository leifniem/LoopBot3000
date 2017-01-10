package models;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class LoopProjectExporter {
	
	public static LoopProject askUserToImportLoopProject(){
		LoopProject result = null;
		File file = FileManager.askUserToLoadXMLFile();
		
		if (file != null) {
			result = LoopProjectExporter.importLoopProject(file.getAbsolutePath());	
		}
		
		return result;
	}
	
	public static void askUserToExportLoopProject(LoopProject loopProject){
		File file = FileManager.askUserToSaveXMLFile();
		if (file != null) {
			exportLoopProject(loopProject, file.getAbsolutePath());
		}
	}
	
	public static void exportLoopProject(LoopProject loopProject, String filepath) {
		try {
			SimpleLoopProject simpleLoopProject = new SimpleLoopProject(loopProject);
			FileOutputStream fos = new FileOutputStream(filepath);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			XMLEncoder encoder = new XMLEncoder(bos);
			encoder.writeObject(simpleLoopProject);
			encoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static LoopProject importLoopProject(String filepath) {
		LoopProject loopProject = null;
		
		try {
			FileInputStream fis = new FileInputStream(filepath);
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
		loopProject = new LoopProject(numberOfBeats, noteValue, tempo);
		loopProject.nameProperty().set(simpleLoopProject.getName());
		
		for(SimpleLoop simpleLoop : simpleLoopProject.getLoops()){
			Loop loop = generateLoopForSimpleLoop(loopProject, simpleLoop);
			loopProject.addLoop(loop);
		}
		
		return loopProject;
	}

	private static Loop generateLoopForSimpleLoop(LoopProject loopProject, SimpleLoop simpleLoop) {
		Loop loop = new Loop(simpleLoop.getName(), loopProject);
		loop.setSoundFile(simpleLoop.getSoundFilename());
		
		for(int i = 0; i < simpleLoop.getNoteStatus().size(); i++){
			boolean active = simpleLoop.getNoteStatus().get(i);
			loop.getNoteStatus().get(i).set(active);
		}
		
		return loop;
	}
}
