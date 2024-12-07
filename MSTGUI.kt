package com.example.ui

import com.example.domain.GraphDomain
import javax.swing.* // Importing Swing components for the GUI

class MSTGUI(private val app: IntegratedApp) {
    // Function to create the GUI panel for the MST (Minimum Spanning Tree) feature
    fun createPanel(): JPanel {
        val panel = JPanel() // The main panel to hold all components
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS) // Arranging components vertically

        val graph = GraphDomain() // Create a GraphDomain object to store and process the graph data

        // Button to load graph data from a CSV file
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

        // Text area to display the MST results
        val resultTextArea = JTextArea("MST Results:").apply {
            isEditable = false // Make the text area read-only
        }

        // Button to calculate the Minimum Spanning Tree (MST)
        val calculateMSTButton = JButton("Calculate MST").apply {
            addActionListener {
                // Calculate the MST and retrieve its edges and total weight
                val (mstEdges, totalWeight) = graph.calculateMST()

                // Update the results text area with the MST details
                resultTextArea.text = if (mstEdges.isNotEmpty()) {
                    "Minimum Spanning Tree:\n" + mstEdges.joinToString("\n") {
                        "${it.from.name} -> ${it.to.name} : ${it.weight} km"
                    } + "\n\nTotal Weight: $totalWeight km"
                } else {
                    "No MST available (Graph might be disconnected)." // Handle cases where MST cannot be calculated
                }
            }
        }

        // Button to navigate back to the main menu
        val backButton = JButton("Back to Menu").apply {
            addActionListener { app.switchToPanel("MainMenu") } // Switch back to the Main Menu panel
        }

        // Add components to the main panel in the specified order
        panel.add(loadFileButton) // Add the button for loading the CSV file
        panel.add(calculateMSTButton) // Add the button for calculating the MST
        panel.add(JScrollPane(resultTextArea)) // Add the results text area inside a scrollable pane
        panel.add(backButton) // Add the button to go back to the main menu

        return panel // Return the constructed panel
    }
}
