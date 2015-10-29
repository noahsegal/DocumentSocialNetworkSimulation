package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Justin Fleming
 * Create and maintain the GUI for the user
 */
public class MainWindow extends JFrame {
	private JTextField numberOfTurnsField; // text field to get # of turns in sim
	private JTextField numberOfProdsField; // text field to get # producers in sim
	private JTextField numberOfConsField; // text field to get # of consumers in sim
	private JTextField numberOfTagsField; // text field to get # of tags in sim
	private JTextField numberOfSearchResultsField; // text field to get # of search results returned each turn

	private JButton startButton;

	private DefaultTableModel documentsTableModel;
	private DefaultTableModel consumersTableModel;
	private DefaultTableModel producersTableModel;

	private Simulation sim;

	private int numberOfTurns;			// number of turns in the sim
	private int numberOfProds;  		// number of Producers in sim
	private int numberOfCons;         	// number of Consumers in sim
	private int numberOfTags;   		// number of Tags in sim
	private int numberOfSearchResults;  // number of Search Results each turn
	private boolean initialized;		// has the simulation been initialized

	public MainWindow(Simulation sim){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.BOTH;
		JPanel panel = new JPanel(layout);
		this.sim = sim;
		initialized = false;

		// Initialize Components
		numberOfTurnsField 			= new JTextField(80);
		numberOfProdsField 			= new JTextField(80);
		numberOfConsField 			= new JTextField(80);
		numberOfTagsField 			= new JTextField(80);
		numberOfSearchResultsField 	= new JTextField(80);
		startButton					= new JButton("Start");
		documentsTableModel			= new DefaultTableModel(5, 4);
		consumersTableModel			= new DefaultTableModel();
		producersTableModel			= new DefaultTableModel();

		// Setting up ActionListioner
		startButton.addActionListener(ae -> {
			try{
				if(!initialized){
					numberOfTurns 			= new Integer(numberOfTurnsField.getText());
					numberOfProds 			= new Integer(numberOfProdsField.getText());
					numberOfCons  			= new Integer(numberOfConsField.getText());
					numberOfTags  			= new Integer(numberOfTagsField.getText());
				}
				numberOfSearchResults = new Integer(numberOfSearchResultsField.getText());
			}catch (Exception e) {
				System.out.println("Invalid Field");
				return;
			}
			if(!initialized) {
				sim.startGame(numberOfTurns, numberOfTags, numberOfProds, numberOfCons);
				startButton.setText("Step");
				initialized = true;
				numberOfTurnsField.setEnabled(false);
				numberOfProdsField.setEnabled(false);
				numberOfConsField.setEnabled(false);
				numberOfTagsField.setEnabled(false);
			}
			sim.takeTurn(numberOfSearchResults);
		});

		// Other Components		-- Ones we arn't keeping track of
		JLabel documentTableLabel 	= new JLabel("Documents");
		JLabel consumerTableLabel	= new JLabel("Consumers");
		JLabel producerTableLabel 	= new JLabel("Producer");
		String documentTableHeader[] = {"Name", "Tag(s)", "Likes", "ProducerID"};
		String consumerTableHeader[] = {"ID", "Tag(s)", "Followers", "Following", "Payoff"};
		String producerTableHeader[] = {"ID", "Tag(s)", "Followers", "Following", "Payoff", "Documents"};

		// Build Top Display Bar -- First Row
		con.weightx = 0.0;
		con.gridwidth = 1;
		panel.add(buildLabelField("Turns:", numberOfTurnsField), con);
		panel.add(buildLabelField("Tags:", numberOfTagsField), con);
		panel.add(buildLabelField("Producers:", numberOfProdsField), con);
		panel.add(buildLabelField("Consumers:", numberOfConsField), con);
		panel.add(buildLabelField("Search Results:", numberOfSearchResultsField), con);

		con.gridwidth = GridBagConstraints.REMAINDER;	// End of Row
		panel.add(startButton, con);

		// Build Table Headers -- Second Row
		con.weightx = 0.33;
		con.gridwidth = 2;
		panel.add(documentTableLabel, con);
		panel.add(consumerTableLabel, con);

		con.gridwidth = GridBagConstraints.REMAINDER;	// End of row
		panel.add(producerTableLabel, con);

		//Build Tables 	-- Third to eighth rows
		con.gridheight = 5;
		con.gridwidth = 2;
		panel.add(buildTable(documentsTableModel, documentTableHeader), con);
		panel.add(buildTable(consumersTableModel, consumerTableHeader), con);

		con.gridwidth = GridBagConstraints.REMAINDER;  // End of row
		panel.add(buildTable(producersTableModel, producerTableHeader), con);

		//Build Graph   -- Ninth to  rows
		//con.gridheight = 4;

		getContentPane().add(panel);
		setSize(600,400);
		setVisible(true);
	}

	/**
	 * Update the tables displayed in the GUI
	 * @param docs, Documents to add
	 * @param users, Users in sim to add
	 */
	public void updateTables (List<Document> docs, List<User> users) {
		clearTableModels();
		System.out.println(users.size());

		for(int i = 0; i < users.size(); i ++) {
			if(users.get(i) instanceof Producer) {
				updateProducerTable((Producer) users.get(i));
				continue;
			}
			else if(users.get(i) instanceof Consumer){
				updateConsumerTable((Consumer) users.get(i));
				continue;
			}
		}

		for(int i = 0; i < docs.size(); i ++) {
			updateDocumentTable(docs.get(i));
		}
	}

	private void clearTableModels() {
		while(producersTableModel.getRowCount() > 0)
			producersTableModel.removeRow(0);

		while(consumersTableModel.getRowCount() > 0) 
			consumersTableModel.removeRow(0);

		while(documentsTableModel.getRowCount() > 0)
			documentsTableModel.removeRow(0);
	}

	/**
	 * add a row to the producer table
	 * @param prod, the producer to be added
	 */
	private void updateProducerTable(Producer prod) {
		int producerId	= prod.getID();
		String tag 		= prod.getTag();
		int followers 	= prod.getNumberOfFollowers();
		int following 	= prod.getNumberOfFollowing();
		int payoff 		= prod.getPayoff();
		int docs 		= prod.getUploadedDocumentSize();

		Object[] rowData = {producerId, tag, followers, following, payoff, docs};

		producersTableModel.addRow(rowData);
	}

	/**
	 * add a row to the consumer table
	 * @param con, the consumer to be added
	 */
	private void updateConsumerTable(Consumer con) {
		int producerId	= con.getID();
		String tag 		= con.getTag();
		int followers 	= con.getNumberOfFollowers();
		int following 	= con.getNumberOfFollowing();
		int payoff 		= con.getPayoff();

		Object[] rowData = {producerId, tag, followers, following, payoff};

		consumersTableModel.addRow(rowData);
	}

	/**
	 * add a row to the documents table
	 * @param doc, the document to be added
	 */
	private void updateDocumentTable(Document doc) {
		if(doc == null) {
			return;
		}
		String docName	= doc.getName();
		String tag 		= doc.getTag();
		int likes 		= doc.getLikers().size();
		int prodID 		= doc.getProducer().getID();

		Object[] rowData = {docName, tag, likes, prodID};
		documentsTableModel.addRow(rowData);
	}

	/**
	 * Create a Text Field with a JLabel beside it
	 * @param label, text for the JLabel
	 * @param field, JTextField to add a label to
	 * @return JPanel containing the two elements
	 */
	private JPanel buildLabelField(String label, JTextField field) {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		field.setMinimumSize(new Dimension(35, 25));
		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.0;

		JPanel labelField = new JPanel(layout);
		labelField.add(new JLabel(label), con);

		con.weighty = 1.0;
		labelField.add(field, con);

		return labelField;
	}


	/**
	 * Creates a ScrollPane containing a JTable with tableModel and header
	 * @param tableModel, DefaultTableModel to be used by the JTable
	 * @param header, strings to be set as the header for the JTable
	 * @return JScrollPane, containing a JTable using tableModel and header
	 */
	private JScrollPane buildTable(DefaultTableModel tableModel, String[] header) {
		tableModel.setColumnIdentifiers(header);
		JTable table = new JTable(tableModel);
		JScrollPane pane = new JScrollPane(table);
		pane.setMinimumSize(new Dimension(300,200));
		return pane;
	}
}
