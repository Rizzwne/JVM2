package com.example.ui

import com.example.domain.GraphDomain
import javax.swing.* // Importing Swing components for the GUI

class GraphGUI(private val app: IntegratedApp) {
    // Function to create the GUI panel for the Graph feature
    fun createPanel(): JPanel {
        val panel = JPanel() // The main panel to hold all components
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS) // Arrange components vertically

        val graph = GraphDomain() // Create a GraphDomain object to store and process the graph data

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
                } else {
                    JOptionPane.showMessageDialog(null, "No file selected or file does not exist.") // Display error message
                }
            }
        }

        // Text area to display the vertices of the graph
        val verticesTextArea = JTextArea("Vertices:\n").apply {
            isEditable = false // Make the text area read-only
        }

        // Text area to display the edges of the graph
        val edgesTextArea = JTextArea("Edges:\n").apply {
            isEditable = false // Make the text area read-only
        }

        // Button to display the graph data
        val showGraphButton = JButton("Show Graph").apply {
            addActionListener {
                // Retrieve and display the vertices and edges from the graph
                val verticesText = "Vertices:\n" + graph.getVertices().joinToString("\n") { it }
                val edgesText = "Edges:\n" + graph.getEdges().joinToString("\n") {
                    "${it.from.name} -> ${it.to.name} : ${it.weight} km"
                }

                verticesTextArea.text = verticesText // Update the vertices text area
                edgesTextArea.text = edgesText // Update the edges text area
            }
        }

        // Button to navigate back to the main menu
        val backButton = JButton("Back to Menu").apply {
            addActionListener { app.switchToPanel("MainMenu") } // Switch back to the main menu panel
        }

        // Add components to the panel in the specified order
        panel.add(loadFileButton) // Add the button for loading the CSV file
        panel.add(showGraphButton) // Add the button to display the graph
        panel.add(JScrollPane(verticesTextArea)) // Add the vertices text area inside a scrollable pane
        panel.add(JScrollPane(edgesTextArea)) // Add the edges text area inside a scrollable pane
        panel.add(backButton) // Add the button to go back to the main menu

        return panel // Return the constructed panel
    }
}
