package com.example.ui

import javax.swing.* // Importing Swing components for GUI
import java.awt.* // Importing AWT components for layout management

fun main() {
    // The entry point for the application
    IntegratedApp().createAndShowGUI()
}

class IntegratedApp {
    // A CardLayout is used to switch between multiple panels
    private val cardLayout = CardLayout()
    private val mainPanel = JPanel(cardLayout) // The main panel that will hold all feature panels

    fun createAndShowGUI() {
        // Creating the main JFrame for the application
        val frame = JFrame("Integrated Application")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE // Ensure the app exits when the window is closed
        frame.setSize(800, 600) // Setting the dimensions of the window

        // Creating individual panels for each feature
        val mainMenuPanel = createMainMenu() // The main menu panel
        val telecomsPanel = TelecomsGUI(this).createPanel() // The Telecoms functionality panel
        val graphPanel = GraphGUI(this).createPanel() // The Graph functionality panel
        val mstPanel = MSTGUI(this).createPanel() // The MST (Minimum Spanning Tree) functionality panel

        // Adding each panel to the CardLayout with a unique identifier
        mainPanel.add(mainMenuPanel, "MainMenu")
        mainPanel.add(telecomsPanel, "Telecoms")
        mainPanel.add(graphPanel, "Graph")
        mainPanel.add(mstPanel, "MST")

        // Adding the main panel (with CardLayout) to the JFrame
        frame.contentPane.add(mainPanel)
        frame.isVisible = true // Making the frame visible to the user
    }

    // A function to switch between panels using their identifiers
    fun switchToPanel(panelName: String) {
        cardLayout.show(mainPanel, panelName) // Show the panel corresponding to the given name
    }

    // Function to create the Main Menu panel
    private fun createMainMenu(): JPanel {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS) // Setting the layout to stack components vertically
        panel.border = BorderFactory.createEmptyBorder(50, 50, 50, 50) // Adding padding around the panel

        // Creating the welcome message with a large heading
        val welcomeLabel = JLabel("<html><h1>Welcome to the JVM</h1></html>", JLabel.CENTER)
        welcomeLabel.alignmentX = Component.CENTER_ALIGNMENT // Centre-align the label

        // Creating a button for the Telecoms feature
        val telecomsButton = JButton("Telecoms").apply {
            alignmentX = Component.CENTER_ALIGNMENT // Centre-align the button
            addActionListener { switchToPanel("Telecoms") } // Switch to the Telecoms panel when clicked
        }

        // Creating a button for the Graph feature
        val graphButton = JButton("Graph").apply {
            alignmentX = Component.CENTER_ALIGNMENT // Centre-align the button
            addActionListener { switchToPanel("Graph") } // Switch to the Graph panel when clicked
        }

        // Creating a button for the MST (Minimum Spanning Tree) feature
        val mstButton = JButton("MST").apply {
            alignmentX = Component.CENTER_ALIGNMENT // Centre-align the button
            addActionListener { switchToPanel("MST") } // Switch to the MST panel when clicked
        }

        // Adding components to the main menu panel in a well-spaced and centred manner
        panel.add(welcomeLabel) // Adding the welcome message
        panel.add(Box.createRigidArea(Dimension(0, 30))) // Adding vertical spacing
        panel.add(telecomsButton) // Adding the Telecoms button
        panel.add(Box.createRigidArea(Dimension(0, 10))) // Adding vertical spacing
        panel.add(graphButton) // Adding the Graph button
        panel.add(Box.createRigidArea(Dimension(0, 10))) // Adding vertical spacing
        panel.add(mstButton) // Adding the MST button

        return panel // Returning the fully constructed Main Menu panel
    }
}
