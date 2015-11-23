package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

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

	private JPanel chartPanel;

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
	private boolean onGoing;

	public MainWindow(Simulation sim){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.BOTH;
		JPanel panel = new JPanel(layout);
		this.sim = sim;
		this.setTitle("SocialSim: A File-Sharing Simulation");
		initialized = false;
		onGoing = false;

		// Initialize Components
		numberOfTurnsField 			= new JTextField(20);
		numberOfProdsField 			= new JTextField(20);
		numberOfConsField 			= new JTextField(20);
		numberOfTagsField 			= new JTextField(20);
		numberOfSearchResultsField 	= new JTextField(20);
		startButton					= new JButton("Start");
		
		documentsTableModel			= new DefaultTableModel(5, 4) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		consumersTableModel			= new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		producersTableModel			= new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		chartPanel 					= new JPanel(new BorderLayout());
		chartPanel.setMinimumSize(new Dimension(200, 200));

		// Setting up ActionListioner
		startButton.addActionListener(ae -> {
			try{
				if(!initialized){
					clearTableModels();
					numberOfTurns 			= new Integer(numberOfTurnsField.getText());
					numberOfProds 			= new Integer(numberOfProdsField.getText());
					numberOfCons  			= new Integer(numberOfConsField.getText());
					numberOfTags  			= new Integer(numberOfTagsField.getText());
				}
				numberOfSearchResults = new Integer(numberOfSearchResultsField.getText());
			}catch (Exception e) {
				JOptionPane alertWindow = new JOptionPane();
				alertWindow.showMessageDialog(this, "All fields must contain a number", "Invalid Input", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(valuesValid()) {
				if(!initialized) {
					this.sim.startGame(numberOfTurns, numberOfTags, numberOfProds, numberOfCons);
					startButton.setText("Step");
					initialized = true;
					setFieldEnabled(false);
				}
				initialized = this.sim.takeTurn(numberOfSearchResults);
				numberOfTurnsField.setText( (Integer.parseInt(numberOfTurnsField.getText()) - 1) + "" );
				plotData();

				if(!initialized) {
					setFieldEnabled(true);
					numberOfTurnsField.setText("");
					numberOfProdsField.setText("");
					numberOfConsField.setText("");
					numberOfTagsField.setText("");
					numberOfSearchResultsField.setText("");
					startButton.setText("Start");
				}
			}
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
		panel.add(producerTableLabel, con);

		con.gridwidth = GridBagConstraints.REMAINDER;	// End of row
		panel.add(consumerTableLabel, con);

		//Build Tables 	-- Third to eighth rows
		con.gridheight = 5;
		con.gridwidth = 2;
		panel.add(buildTable(documentsTableModel, documentTableHeader), con);
		panel.add(buildTable(producersTableModel, producerTableHeader), con);

		con.gridwidth = GridBagConstraints.REMAINDER;  // End of row
		panel.add(buildTable(consumersTableModel, consumerTableHeader), con);

		//Build Graph   -- Ninth to Thirteenth rows
		con.gridheight = 4;
		con.gridwidth = GridBagConstraints.REMAINDER;  //End of row
		panel.add(chartPanel, con);

		getContentPane().add(panel);
		pack();
		setSize(1000,470);
		//pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Update the tables displayed in the GUI
	 * @param docs, Documents to add
	 * @param users, Users in sim to add
	 */
	public void updateTables (List<Document> docs, List<User> users) {
		clearTableModels();

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
	 * Set the fields to be enabled or not
	 * @param b, enable fields true/false
	 */
	private void setFieldEnabled(boolean b) {
		numberOfTurnsField.setEnabled(b);
		numberOfProdsField.setEnabled(b);
		numberOfConsField.setEnabled(b);
		numberOfTagsField.setEnabled(b);
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
		field.setMinimumSize(new Dimension(25, 25));
		field.setMaximumSize(new Dimension(30,25));
		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.0;

		JPanel labelField = new JPanel(layout);
		labelField.add(new JLabel(label), con);

		con.weighty = 1.0;
		labelField.add(field, con);

		return labelField;
	}

	/**
	 * Check to see if the current values are valid for a simulation
	 * @return true if valid false if invalid
	 */
	private boolean valuesValid() {
		if(numberOfTurns < 0 || numberOfProds < 0 || numberOfCons < 0 || numberOfTags < 0 || numberOfSearchResults < 0){
			JOptionPane alertWindow = new JOptionPane();
			alertWindow.showMessageDialog(this, "Negative Number", "Invalid Input", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else
			return true;
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
		addDoubleClickListener(table);
		JScrollPane pane = new JScrollPane(table);
		pane.setMinimumSize(new Dimension(100,200));
		return pane;
	}

	/**
	 * Attach the mouse listener to each JTable
	 * @param table, to have listener attach
	 */
	private void addDoubleClickListener(JTable table) {
		table.addMouseListener(new MouseAdapter () {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2 && row != -1) {
					
					int id = -1;
					String name = "";
					Object o = table.getValueAt(row, 0);
					System.out.println(o);
					System.out.println(o.getClass().getName());
					if(o instanceof Integer){
						System.out.println("in integer");
						id = (Integer) o;
					}
					else if(o instanceof String){
						System.out.println("in String");
						name = (String) o;
					}
					
					if(table.getModel() == documentsTableModel){
						new DoubleClickWindow(sim.getDocument(name));
					}
					else {
						System.out.println(id+"");
						new DoubleClickWindow(sim.getUserById(id), sim.getTags().toArray(new String[sim.getTags().size()]));
					}
				}
			}
		});
	}

	/**
	 * Plot data on a bar graph
	 */
	private void plotData() {
		DefaultCategoryDataset data = buildData(producersTableModel, consumersTableModel);
		JFreeChart chart = ChartFactory.createBarChart("User Payoffs",
				"User (ID)",
				"Payoff (int)",
				data);
		ChartPanel myChart = new ChartPanel(chart);
		chartPanel.removeAll();
		chartPanel.add(myChart, BorderLayout.CENTER);
		chartPanel.validate();
	}

	/**
	 * Build Data to plot for either the Consumer or Producer
	 * @param model DefaultTableModel to build plot data from
	 * @return HashMap of plot data key is x value, value is y value
	 */
	private DefaultCategoryDataset buildData(DefaultTableModel model, DefaultTableModel model2) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < model.getRowCount(); i++) 
		{
			dataset.addValue(new Double((Integer)model.getValueAt(i, 4)), "", (Integer)model.getValueAt(i, 0));
		}
		for(int i = 0; i < model2.getRowCount(); i++) 
		{
			dataset.addValue(new Double((Integer)model2.getValueAt(i, 4)), "", (Integer)model2.getValueAt(i, 0));
		}
		return dataset;
	}
}
