import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Vikas@13";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createGUI();
        });
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Insert Data", createInsertPanel());
        tabbedPane.addTab("Update Data", createUpdatePanel());
        tabbedPane.addTab("Delete Data", createDeletePanel());
        tabbedPane.addTab("Display Data", createDisplayPanel());
        tabbedPane.addTab("Search ", createSearchPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
    
    private static JPanel createSearchPanel() {
        // Main panel to hold the split pane
        JPanel panel = new JPanel(new BorderLayout());

        // Left panel for search box and button
        JPanel leftPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        JLabel idLabel = new JLabel("ID");
        JTextField idField = new JTextField();
        JButton search = new JButton("Search");

        leftPanel.add(idLabel);
        leftPanel.add(idField);
        leftPanel.add(search);

        JTextArea ar = new JTextArea("");
        ar.setEditable(false);

        JScrollPane rightPanel = new JScrollPane(ar);

        // Create a split pane to separate left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200); // Set initial divider location (adjust as needed)
        splitPane.setResizeWeight(0.3);    // Allocate 30% space to the left panel

        // Add the split pane to the main panel
        panel.add(splitPane, BorderLayout.CENTER);

        // Add action listener for the search button
        search.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText()); // Parse ID from input
                ar.setText(""); // Clear previous search results

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                    String query = "SELECT * FROM students_data WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);

                    // Execute the query
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        // Process and display the first row of results
                        int idResult = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String address=resultSet.getString("address");
                        String mobileno=resultSet.getString("mobile_no");
                        String email=resultSet.getString("email_id");
                        String branch=resultSet.getString("branch");
                        int year=resultSet.getInt("year");
                        int age = resultSet.getInt("age");

                        ar.append("ID: " + idResult + "\n");
                        ar.append("Name: " + name + "\n");
                        ar.append("Age: " + age + "\n");
                        ar.append("Address: " + address + "\n");
                        ar.append("Mobile No: " + mobileno + "\n");
                        ar.append("Email: " + email + "\n");
                        ar.append("Branch: " + branch + "\n");
                        ar.append("Y: " + year + "\n");
                        ar.append("----------------------\n");
                    } else {
                        // If no rows are found
                        ar.append("No record found.\n");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid ID.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }



    private static JPanel createInsertPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel mobileLabel = new JLabel("Mobile No:");
        JTextField mobileField = new JTextField();
        JLabel emailidLabel = new JLabel("Email ID:");
        JTextField emailField = new JTextField();
        JLabel branchLabel = new JLabel("Branch:");
        JTextField branchField = new JTextField();
        JLabel studyYear=new JLabel("Year of study");
        JTextField sfield=new JTextField();
        JButton insertButton = new JButton("Insert");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(mobileLabel);
        panel.add(mobileField);
        panel.add(emailidLabel);
        panel.add(emailField);
        panel.add(branchLabel);
        panel.add(branchField);
        panel.add(studyYear);
        panel.add(sfield);
        panel.add(new JLabel());
        panel.add(insertButton);

        insertButton.addActionListener(e -> {
        	
        		String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String address=addressField.getText();
                String mobileno=mobileField.getText();
                String email=emailField.getText();
                String branch=branchField.getText();
                int year=Integer.parseInt(sfield.getText());
        	
            
            
            
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "INSERT INTO students_data(name,address, mobile_no,email_id, branch,year,age) VALUES (?, ?, ?,?,?,?,?)";
                PreparedStatement p = connection.prepareStatement(query);
                p.setString(1,name );
                p.setString(2, address);
                p.setString(3, mobileno);
                p.setString(4, email);
                p.setString(5, branch);
                p.setInt(6, year);
                p.setInt(7, age);
                

                int rowsAffected = p.executeUpdate();
     
                if (rowsAffected > 0) 
                {
                	
                    JOptionPane.showMessageDialog(panel, "Data Inserted Successfully");
                    nameField.setText("");
                    ageField.setText("");
                    addressField.setText("");
                    emailField.setText("");
                    branchField.setText("");
                    sfield.setText("");
                    mobileField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Insert Failed");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });
        return panel;
    }

    private static JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel mobileLabel = new JLabel(" New Mobile NO:");
        JTextField mobileField = new JTextField();
        JLabel yearLabel = new JLabel(" Updated Year:");
        JTextField yearField = new JTextField();
        JLabel EIdLabel = new JLabel(" New Email ID:");
        JTextField eidField = new JTextField();
        
        JButton updateButton = new JButton("Update");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(mobileLabel);
        panel.add(mobileField);
        panel.add(yearLabel);
        panel.add(yearField);
        panel.add(EIdLabel);
        panel.add(eidField);
        panel.add(new JLabel());
        panel.add(updateButton);

        updateButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String newn = mobileField.getText();
            int y=Integer.parseInt(yearField.getText());
            String email_id=eidField.getText();
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "UPDATE students_data SET mobile_no = ?,year=?,email_id=? WHERE id = ?";
                PreparedStatement p = connection.prepareStatement(query);
                
                
                p.setString(1, newn); 
                p.setInt(2, y);       
                p.setString(3, email_id); 
                p.setInt(4, id);
             

                int rowsAffected = p.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(panel, "Data Updated Successfully");
                    mobileField.setText("");
                	idField.setText("");
                	eidField.setText("");
                	yearField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Update Failed");
                } 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private static JPanel createDeletePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JButton deleteButton = new JButton("Delete");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel());
        panel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "DELETE FROM students_data WHERE id = ?";
                PreparedStatement p = connection.prepareStatement(query);
                p.setInt(1, id);

                int rowsAffected = p.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(panel, "Data Deleted Successfully");
                	idField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Delete Failed");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private static JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        JButton refreshButton = new JButton("Refresh");

        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            textArea.setText("");

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "SELECT * FROM students_data";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                	int idResult = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String address=resultSet.getString("address");
                    String mobileno=resultSet.getString("mobile_no");
                    String email=resultSet.getString("email_id");
                    String branch=resultSet.getString("branch");
                    int year=resultSet.getInt("year");
                    int age = resultSet.getInt("age");

                    textArea.append("ID: " + idResult + "\n");
                    textArea.append("Name: " + name + "\n");
                    textArea.append("Age: " + age + "\n");
                    textArea.append("Address: " + address + "\n");
                    textArea.append("Mobile No: " + mobileno + "\n");
                    textArea.append("Email: " + email + "\n");
                    textArea.append("Branch: " + branch + "\n");
                    textArea.append("Y: " + year + "\n");
                    textArea.append("----------------------\n");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }
}
