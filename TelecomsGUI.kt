package com.example.ui

import com.example.domain.GraphDomain
import javax.swing.* // Importing Swing components for the GUI

class TelecomsGUI(private val app: IntegratedApp) {
    // Function to create the GUI panel for the Telecoms feature
    fun createPanel(): JPanel {
        val panel = JPanel() // The main panel to hold all components
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS) // Arrange components vertically

        val graph = GraphDomain() // Create a GraphDomain object to store and process the graph data

        // Dropdown menus for selecting two cities
        val city1Dropdown = JComboBox<String>() // Dropdown for the first city
        val city2Dropdown = JComboBox<String>() // Dropdown for the second city

        // Label to display the result of the distance query
        val resultLabel = JLabel("Distance will be displayed here.")

        // Button to load the graph data from a CSV file
        val loadFileButton = JButton("Load CSV File").apply {
            addActionListener {
                // Open a file chooser for the user to select a CSV file
                val fileChooser = JFileChooser()
                fileChooser.fileFilter = javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv") // Restrict to CSV files
                val result = fileChooser.showOpenDialog(null) // Display the file chooser dialog

                // Process the selected file
                val selectedFile = if (result == JFileChooser.APPROVE_OPTION) fileChooser.selectedFile else null
                if (selectedFile != null && selectedFile.exists()) {
                    graph.loadFromCSV(selectedFile.absolutePath) // Load the graph data from the file
                    JOptionPane.showMessageDialog(null, "File loaded successfully.") // Display success message

                    // Populate the dropdowns with the vertices (cities)
                    val vertices = graph.getVertices() // Retrieve the list of vertices from the graph
                    city1Dropdown.removeAllItems()
                    city2Dropdown.removeAllItems()
                    vertices.forEach {
                        city1Dropdown.addItem(it) // Add each city to the first dropdown
                        city2Dropdown.addItem(it) // Add each city to the second dropdown
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No file selected or file does not exist.") // Display error message
                }
            }
        }

        // Button to query the distance between two selected cities
        val queryButton = JButton("Find Distance").apply {
            addActionListener {
                // Retrieve the selected cities from the dropdowns
                val city1 = city1Dropdown.selectedItem as? String
                val city2 = city2Dropdown.selectedItem as? String

                if (city1 != null && city2 != null) {
                    // Query the distance between the two cities
                    val distance = graph.getDistance(city1, city2)
                    resultLabel.text = if (distance != null) {
                        "Distance between $city1 and $city2: $distance km" // Display the distance if available
                    } else {
                        "No direct connection between $city1 and $city2." // Handle case where no connection exists
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select both cities.") // Prompt the user to select cities
                }
            }
        }

        // Button to navigate back to the main menu
        val backButton = JButton("Back to Menu").apply {
            addActionListener { app.switchToPanel("MainMenu") } // Switch back to the main menu panel
        }

        // Add components to the panel in the specified order
        panel.add(loadFileButton) // Add the button for loading the CSV file
        panel.add(JLabel("Select City 1:")) // Add a label for the first dropdown
        panel.add(city1Dropdown) // Add the first dropdown
        panel.add(JLabel("Select City 2:")) // Add a label for the second dropdown
        panel.add(city2Dropdown) // Add the second dropdown
        panel.add(queryButton) // Add the button to query distances
        panel.add(resultLabel) // Add the label to display results
        panel.add(backButton) // Add the button to go back to the main menu

        return panel // Return the constructed panel
    }
}
