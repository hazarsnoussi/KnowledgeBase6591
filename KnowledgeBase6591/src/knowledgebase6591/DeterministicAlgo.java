package knowledgebase6591;
import java.util.Vector;
import java.util.*;
import org.jgrapht.alg.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.util.Vector;
import org.jgrapht.graph.DefaultEdge;


public class DeterministicAlgo {
    public static void  deterministic (AsUndirectedGraph g){
        Vector  c = new Vector(20);
        Vector<Integer>d=new Vector<>();
        Vector <Vector> D= new <Vector>Vector();
         int vertex;
        Vector p= null;
        Vector vectorBuffer= new Vector();
        
        int i;
        d.add(0,0);
        int max=0;
        
        for (i=1;i<=g.vertexSet().size();i++){
        String vertexName=Integer.toString(i);
        d.add(i, g.degreeOf(vertexName));
        if (max<g.degreeOf(vertexName))
            max=g.degreeOf(vertexName);
        }
        
        D.add(new Vector());
        for (i=1;i<=g.vertexSet().size();i++)
            D.add(new Vector());
       
        for(int j=1;j<d.size();j++){
               int k=d.elementAt(j);
                p =D.elementAt(k);
               p.add(j);
        }
      /*System.out.println("Vector D"); 
      for (i=1;i<D.size();i++) 
       System.out.println("D["+i+"]"+D.elementAt(i).toString()); */
     
    //part 2 of the algorithm 
    for(i=0;i<=g.vertexSet().size();i++)    
    c.add(i,0);
    
     for(int k=1;k<=g.vertexSet().size();k++){
               
        int j=0;
        Vector q =D.elementAt(k);
        while((j<=q.size())&&(q.size()>0)){ 
          vertex =(int)(q.elementAt(j));
         
          c.setElementAt(k, vertex);
         
          q.removeElementAt(j);
          j--;
          
          for (i=1;i<=g.vertexSet().size();i++){
             if ((g.containsEdge(Integer.toString(vertex),Integer.toString(i)))&&(d.elementAt(i)>k)&&(i!=vertex)){ 
                 int m=1;boolean exist=false;
                  while ((m<=g.vertexSet().size())&&(!exist)){
                   if (D.elementAt(m).contains(i))
                    {
                     exist=true;
                    
                     p=D.elementAt(m);
                     p.removeElement(i);
                     
                     vectorBuffer=D.elementAt(m-1);
                    vectorBuffer.add(i);
                     vectorBuffer.sort(null);
                   
                     d.setElementAt(d.elementAt(i)-1,i); 
                     g.removeEdge(Integer.toString(i),Integer.toString(vertex));
                      
                    }
                   m++;
                  }
              } 
          }
          
          j++;
         
          }
        }            
          for (i=1;i<=g.vertexSet().size();i++)
          System.out.println("c["+i+"]="+c.elementAt(i));
       }
    }
