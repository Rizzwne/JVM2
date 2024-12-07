package com.example.domain

import java.io.File
import java.util.PriorityQueue

// Data class to represent a vertex (node) in the graph
data class Vertex(val name: String)

// Data class to represent an edge (connection) between two vertices with a weight (distance)
data class Edge(val from: Vertex, val to: Vertex, val weight: Int)

// Class to represent the graph structure and its operations
class GraphDomain {
    private val vertices = mutableSetOf<Vertex>() // Set to store unique vertices
    private val edges = mutableListOf<Edge>() // List to store edges

    // Function to add a vertex to the graph
    fun addVertex(name: String): Boolean {
        // Add the vertex only if it doesn't already exist
        if (vertices.none { it.name == name }) {
            vertices.add(Vertex(name))
            return true
        }
        return false
    }

    // Function to add an edge between two vertices
    fun addEdge(from: String, to: String, weight: Int): Boolean {
        // Find or validate the vertices involved in the edge
        val fromVertex = vertices.find { it.name == from } ?: return false
        val toVertex = vertices.find { it.name == to } ?: return false
        // Add the edge only if it doesn't already exist
        if (edges.none { it.from == fromVertex && it.to == toVertex }) {
            edges.add(Edge(fromVertex, toVertex, weight))
            return true
        }
        return false
    }

    // Function to retrieve a sorted list of vertex names
    fun getVertices(): List<String> = vertices.map { it.name }.sorted()

    // Function to retrieve the list of edges
    fun getEdges(): List<Edge> = edges

    // Function to find the distance (weight) between two vertices
    fun getDistance(from: String, to: String): Int? {
        // Check if there's an edge between the two vertices in either direction
        return edges.find {
            (it.from.name == from && it.to.name == to) ||
                    (it.from.name == to && it.to.name == from)
        }?.weight
    }

    // Function to load graph data from a CSV file
    fun loadFromCSV(filePath: String) {
        File(filePath).forEachLine { line ->
            val parts = line.split(",") // Split the line into parts
            if (parts.size == 3) {
                val from = parts[0].trim() // Source vertex
                val to = parts[1].trim() // Destination vertex
                val weight = parts[2].trim().toIntOrNull() // Weight of the edge
                if (weight != null) {
                    addVertex(from) // Add the source vertex
                    addVertex(to) // Add the destination vertex
                    addEdge(from, to, weight) // Add the edge
                }
            }
        }
    }

    // Function to calculate the Minimum Spanning Tree (MST) using Prim's Algorithm
    fun calculateMST(): Pair<List<Edge>, Int> {
        if (vertices.isEmpty()) return Pair(emptyList(), 0) // Return empty MST if no vertices exist

        val mstEdges = mutableListOf<Edge>() // List to store the edges of the MST
        val visited = mutableSetOf<Vertex>() // Set to track visited vertices
        val pq = PriorityQueue<Edge>(compareBy { it.weight }) // Priority queue to pick the smallest edge

        val startVertex = vertices.first() // Start from the first vertex in the set
        visited.add(startVertex)
        // Add all edges connected to the starting vertex to the priority queue
        pq.addAll(edges.filter { it.from == startVertex || it.to == startVertex })

        var totalWeight = 0 // Variable to track the total weight of the MST

        // While there are edges to process and not all vertices are visited
        while (pq.isNotEmpty() && visited.size < vertices.size) {
            val edge = pq.poll() // Get the edge with the smallest weight

            // Skip edges that connect already visited vertices
            if (edge.from in visited && edge.to in visited) continue

            // Add the edge to the MST and update the total weight
            mstEdges.add(edge)
            totalWeight += edge.weight

            // Determine the new vertex to visit
            val newVertex = if (edge.from in visited) edge.to else edge.from
            visited.add(newVertex)

            // Add new edges connected to the newly visited vertex
            pq.addAll(edges.filter {
                (it.from == newVertex || it.to == newVertex) &&
                        (it.from !in visited || it.to !in visited)
            })
        }

        // Return the list of MST edges and the total weight
        return Pair(mstEdges, totalWeight)
    }
}
