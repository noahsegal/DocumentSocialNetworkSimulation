/**
 * @author Justin Fleming
 * @author Reid Cain-Mondoux
 */
package main;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class DoubleClickWindow extends JFrame {
	private Document doc;
	private User user;
	private DefaultListModel<String> followingModel;
	private DefaultListModel<String> followerModel;
	private DefaultListModel<String> documentModel;
	private JList<String> followingList;
	private JList<String> followerList;
	private JList<String> documentList;
	private JScrollPane followerPane;
	private JScrollPane followingPane;
	private JScrollPane documentPane;
	private JPanel reidsPanel; // Put all your stuff in this panel and rename
	
	public DoubleClickWindow(Document doc) {
		this.doc = doc;
		documentModel 	= new DefaultListModel<String>();
		documentList 	= new JList<String>(documentModel);
		documentPane	= new JScrollPane(documentList);
		JSplitPane split	= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,reidsPanel, buildPanel(documentPane,"Like This"));
		this.populateDocModel();
		getContentPane().add(split);
		setSize(400,400);
		pack();
		setVisible(true);
	}
	
	public DoubleClickWindow(User user) {
		this.user = user;
		followingModel 	= new DefaultListModel<String>();
		followerModel 	= new DefaultListModel<String>();
		followingList	= new JList<String>(followingModel);
		followerList	= new JList<String>(followerModel);
		followerPane	= new JScrollPane(followerList);
		followingPane	= new JScrollPane(followingList);
		JSplitPane leftCenterPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, reidsPanel, buildPanel(followerPane,"Followers"));
		JSplitPane rightPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCenterPanel, buildPanel(followingPane, "Following"));
		
		this.populateUserModels();
		getContentPane().add(rightPanel);
		setSize(400,400);
		pack();
		setVisible(true);
	}
	
	/**
	 * populate the user models with the appropiate data
	 */
	private void populateUserModels(){
		if(user == null) 
			return;
		ArrayList<String> following = user.listOfFollowing();
		ArrayList<String> follower = user.listOfFollowers();
		populateList(following, followingModel);
		populateList(follower, followerModel);
		
	}
	
	/**
	 * populate the document model with the data
	 */
	private void populateDocModel(){
		if(doc == null)
			return;
		ArrayList<String> likes = doc.listOfLikers();
		populateList(likes, documentModel);
	}
	
	/**
	 * populate the model from the list provided
	 * @param list, the list to copy from
	 * @param model, the model to copy to
	 */
	private void populateList(ArrayList<String> list, DefaultListModel<String> model) {
		list.forEach((item)->{model.addElement(item);});
	}
	
	/**
	 * Return JPanel containing the scollPane and a title
	 * @param scroll, scrollPane to add title
	 * @param title, title of Scroll pane
	 * @return
	 */
	private JPanel buildPanel(JScrollPane scroll, String title) {
		JLabel label = new JLabel(title);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(label, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}
	
	

}