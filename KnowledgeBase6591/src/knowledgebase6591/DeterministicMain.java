/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knowledgebase6591;

import java.util.Vector;
import java.util.*;
import org.jgrapht.alg.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.util.Vector;
import org.jgrapht.graph.DefaultEdge;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Toshiba
 */
 /*public class Comp6591_311016 {
   
   
    public static void main(String args []){
  long startTime = System.currentTimeMillis();      
   
        // constructs a directed graph with the specified vertices and edges
        DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        //directedGraph.edgeSet().clear();
        //directedGraph.edgeSet();
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");
        directedGraph.addVertex("5");
        directedGraph.addVertex("6");
        directedGraph.addVertex("7");
       
        directedGraph.addEdge("1", "2");
        
        directedGraph.addEdge("2", "3");
        directedGraph.addEdge("2", "4");
        
        directedGraph.addEdge("3", "4");
        directedGraph.addEdge("3", "5");
        directedGraph.addEdge("3", "6");
        
     
        directedGraph.addEdge("5", "4");
        directedGraph.addEdge("5", "6");
       
       
        directedGraph.addEdge("4", "6");
        directedGraph.addEdge("4", "7");
        
        directedGraph.addEdge("6", "7");
        
      
      AsUndirectedGraph g = new  AsUndirectedGraph(directedGraph);
      System.out.println("G.vertexSet():"+g.vertexSet());
      System.out.println("G.edgeSet():"+g.edgeSet());
      
      Test x= new Test();
      System.out.println("Core decomposition of every vertex of the graph");
      x.deterministic(g);  
     long endTime   = System.currentTimeMillis();
     long totalTime = endTime - startTime;
     long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);
    // System.out.println("Run_Time:"+minutes+" minutes");
     //System.out.println(totalTime/60000);
     System.out.println(totalTime);
}
    }

*/public class DeterministicMain {
   
    
    public static void main(String args []){
       
   long startTime = System.currentTimeMillis();

        // constructs a directed graph with the specified vertices and edges
        DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        //directedGraph.edgeSet().clear();
        //directedGraph.edgeSet();
        for (int i=1; i<=365000;i++)
        directedGraph.addVertex(Integer.toString(i));
      
       for(int i=1;i<364990;i++)
        directedGraph.addEdge(Integer.toString(i), Integer.toString(i+1));
       for(int i=1;i<364899;i++)
        directedGraph.addEdge(Integer.toString(i), Integer.toString(i+2));
       for(int i=1;i<364904;i++)
        directedGraph.addEdge(Integer.toString(i), Integer.toString(i+3));
       for(int i=1;i<364950;i++)
        directedGraph.addEdge(Integer.toString(i+2),Integer.toString(i+5));
    
        
      
      AsUndirectedGraph g = new  AsUndirectedGraph(directedGraph);
      System.out.println("G.vertexSet():"+g.vertexSet());
      System.out.println("G.edgeSet():"+g.edgeSet());
      
      DeterministicAlgo x= new DeterministicAlgo();
      System.out.println("Core decomposition of every vertex of the graph");
      x.deterministic(g);  
      long endTime   = System.currentTimeMillis();
     long totalTime = endTime - startTime;
     long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);
    // System.out.println("Run_Time:"+minutes+" minutes");
     System.out.println(totalTime/60000);
     System.out.println(totalTime);
}
    }
     


    


