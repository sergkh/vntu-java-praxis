package org.vntu.practice;
import java.awt.BorderLayout;
import java.io.File;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class FileTreeBrowser extends JFrame {
	
	private static final long serialVersionUID = -8472257440095891869L;

	public FileTreeBrowser(File root) {
		super("File Browser");
		setSize(300, 450);
		setLayout(new BorderLayout());
		add(new JScrollPane(new JTree(new FilesTreeModel(root))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new FileTreeBrowser(new File("/")).setVisible(true);
	}
}

class FilesTreeModel implements TreeModel {
	
	private EventListenerList listeners = new EventListenerList();
	
	private File rootNode;
	
	public FilesTreeModel(File root) {
		rootNode = root;
	}
	
	@Override
	public Object getRoot() {
		return rootNode;
	}

	@Override
	public Object getChild(Object parent, int index) {
		File file = (File) parent;
		return file.listFiles()[index];
	}

	@Override
	public int getChildCount(Object parent) {
		File file = (File) parent;
		String[] files = file.list();
		return files != null ? files.length : 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		return !((File) node).isDirectory();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		File file = (File) parent;
		return Arrays.asList(file.listFiles()).indexOf(child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l){
		listeners.add(TreeModelListener.class, l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l){
		listeners.remove(TreeModelListener.class, l);
	}
	
	@Override
	public void valueForPathChanged(TreePath path, Object newValue){}
}
