package org.vntu.practice;

/**
 * @author sergey
 */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;


public class FileDiff {
    
	public static List<String> compareFiles(File first, File second) throws IOException {
		
		List<String> diffs = new ArrayList<>();
		
        LineNumberReader reader1 = new LineNumberReader(new FileReader(first));
        LineNumberReader reader2 = new LineNumberReader(new FileReader(second));
        
        try {
	        String line1 = reader1.readLine();
	        String line2 = reader2.readLine();
	
	        while (line1 != null && line2 != null) {
	            if (!line1.equals(line2)) {
	                diffs.add("" + reader1.getLineNumber() + ":\n-" + line1 + "\n+" + line2);
	            }
	        
	            line1 = reader1.readLine();
	            line2 = reader2.readLine();
	        }
	        
	        while(line2 != null) {
	        	diffs.add(reader2.getLineNumber() + ":\n+" + line2);
	            line2 = reader2.readLine();
	        }
	        
	        while(line1 != null) {
	            diffs.add(reader1.getLineNumber() + ":\n-" + line1);
	            line1 = reader1.readLine();
	        }
	        
	        return diffs;
	        
        } finally {
        	reader1.close();
        	reader2.close();
        }
    }
    
	private static File choseFile() {
		JFileChooser fChooser1 = new JFileChooser();
		
		if(fChooser1.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		
		return fChooser1.getSelectedFile();		
	}
	
    public static void main(String[] args) throws IOException {
		List<String> diffs = compareFiles(choseFile(), choseFile());
    	
		for(String diff : diffs) {
			System.out.println(diff);
		}
		
	}
}