package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import Search.DumbSearch;
import Search.FollowingSearch;
import Search.HipsterSearch;
import Search.PopularitySearch;
import Search.RandomSearch;
import Search.Search;
import Search.SimilarLikeSearch;
import Search.UserPopularitySearch;
/**
 *
 * This Window allows the end user to view what search method is selected, how a producer is acting
 * and what a Document or User is following or being followed by.
 * 
 * @author Justin Fleming
 * @author Reid Cain-Mondoux
 * @author Noah Segal
 *
 */
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
	private JPanel comboPanel; // Put all your stuff in this panel and rename
	private JPanel buttonPanel;
	private JComboBox<Search> strategiesBox;
	private Search[] searches;
	
	private JLabel selectSearch;
	private JRadioButton actAsConsRadio;
	private JRadioButton actAsProdRadio;
	private ButtonGroup radioGroup;
	private JComboBox<String> tagsBox;
	private final String SET_ALT = "Set Alt Tag";
	
	private JButton setChangesButton;
	private JButton cancelChangesButton;
	
	public DoubleClickWindow(Document doc) {
		this.doc = doc;
		this.setTitle("Document " + doc.getName());
		documentModel 	= new DefaultListModel<String>();
		documentList 	= new JList<String>(documentModel);
		documentPane	= new JScrollPane(documentList);
		JSplitPane split	= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,comboPanel, buildPanel(documentPane, " Users Who Like This Document "));
		this.populateDocModel();
		getContentPane().add(split);
		setSize(400,400);
		pack();
		setVisible(true);
	}
	
	public DoubleClickWindow(User user, String[] tags) {
		this.user = user;
		this.setTitle(user.getClass().getSimpleName() + " " + user.getID());
		
		followingModel 	= new DefaultListModel<String>();
		followerModel 	= new DefaultListModel<String>();
		followingList	= new JList<String>(followingModel);
		followerList	= new JList<String>(followerModel);
		followerPane	= new JScrollPane(followerList);
		followingPane	= new JScrollPane(followingList);
		selectSearch	= new JLabel("Select Search Method:");
		setChangesButton = new JButton("Ok");
		cancelChangesButton = new JButton("Cancel");
		selectSearch.setHorizontalAlignment(JLabel.CENTER);
		searches = new Search[] {
				new DumbSearch(),
				new HipsterSearch(),
				new PopularitySearch(),
				new RandomSearch(),
				new FollowingSearch(),
				new UserPopularitySearch(),
				new SimilarLikeSearch()
			};
		comboPanel = new JPanel(new GridLayout(6,1));
		buttonPanel = new JPanel(new GridLayout(1,2));
		strategiesBox = new JComboBox<Search>(searches);
		JSplitPane leftCenterPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, comboPanel, buildPanel(followerPane,"Followers"));
		JSplitPane rightPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCenterPanel, buildPanel(followingPane, "Following"));


		strategiesBox.setSelectedItem(user.getSearchMethod());
		
		comboPanel.add(selectSearch);
		comboPanel.add(strategiesBox);
		
		// Set whether Producer acts as a Consumer or Producer
		if (user instanceof Producer) {
			Producer prod = (Producer) user;
			buildProducerSpecificGUI(prod, tags);
			
			setChangesButton.addActionListener(ae -> {
				prod.setSearchMethod((Search)strategiesBox.getSelectedItem());
				prod.setAltTag((String) tagsBox.getSelectedItem());
				prod.setActAsConsumer(actAsConsRadio.isSelected());
				this.dispose();
			});
		}
		else {
			setChangesButton.addActionListener(ae -> {
				user.setSearchMethod((Search)strategiesBox.getSelectedItem());
				this.dispose();
			});
		}
		
		cancelChangesButton.addActionListener(ae -> {
			this.dispose();
		});

		
		buttonPanel.add(setChangesButton);
		buttonPanel.add(cancelChangesButton);
		comboPanel.add(buttonPanel);
		
		this.populateUserModels();
		getContentPane().add(rightPanel);
		setSize(400,400);
		pack();
		setVisible(true);
	}
	
	/**
	 * populate the user models with the appropriate data
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
	
	/**
	 * Set up the GUI with Producer-specific components:
	 * Toggle ActAsConsumer/Producer
	 * Change Producer's altTag 
	 * @param prod Producer that was double-clicked in the MainWindow
	 */
	private void buildProducerSpecificGUI(Producer prod, String[] tags) {
		actAsConsRadio = new JRadioButton("Act as Consumer");
		actAsProdRadio = new JRadioButton("Act as Producer (Select Tag)");
		
		if (prod.getActAsConsumer()) actAsConsRadio.setSelected(true);
		else actAsProdRadio.setSelected(true);
		
		radioGroup = new ButtonGroup();
		radioGroup.add(actAsConsRadio);
		radioGroup.add(actAsProdRadio);
		
		tagsBox = new JComboBox<String>(tags);
		tagsBox.addItem(SET_ALT);
		tagsBox.setSelectedItem(prod.getAltTag());
		
		comboPanel.add(actAsConsRadio);
		comboPanel.add(actAsProdRadio);
		comboPanel.add(tagsBox);
	}
	
	

}
