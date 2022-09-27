package knowledgebase6591;

import java.io.*;
import java.util.*;

public class KNCoreDecomp {

	private HashMap<String, HashMap<String, Float>> m_graph;
	private HashMap<String, double[]> m_kCoreXVals;
	private float m_netaThreshold = 0.04f;

	public static void main(String args[]) {

		

		HashMap<String, HashMap<String, Float>> graph = new HashMap<String, HashMap<String, Float>>();
		//for(int i = 1; i <= 7; i++) 
		//	graph.put(Integer.toString(i), new HashMap<String, Float>());

		float[] tempProb = { 0.9f, 0.5f, 0.2f, 0.6f, 0.6f, 0.4f, 0.1f, 0.8f, 0.1f, 0.5f, 0.5f };

		/*
		// vertex 1
		graph.get("1").put("2", 0.9f);

		// vertex 2
		graph.get("2").put("1", 0.9f);
		graph.get("2").put("3", 0.5f);
		graph.get("2").put("4", 0.2f);

		// vertex 3
		graph.get("3").put("2", 0.5f);
		graph.get("3").put("4", 0.6f);
		graph.get("3").put("5", 0.6f);
		graph.get("3").put("6", 0.4f);

		// vertex 4
		graph.get("4").put("2", 0.2f);
		graph.get("4").put("3", 0.6f);
		graph.get("4").put("5", 0.1f);
		graph.get("4").put("6", 0.8f);
		graph.get("4").put("7", 0.1f);

		// vertex 5
		graph.get("5").put("3", 0.6f);
		graph.get("5").put("4", 0.1f);
		graph.get("5").put("6", 0.5f);

		// vertex 6
		graph.get("6").put("3", 0.4f);
		graph.get("6").put("4", 0.8f);
		graph.get("6").put("5", 0.5f);
		graph.get("6").put("7", 0.5f);

		// vertex 7
		graph.get("7").put("4", 0.1f);
		graph.get("7").put("6", 0.5f);
		 */

		int ctr = 0;
		try{
			BufferedReader br;
			if(args.length == 0)
				br = new BufferedReader(new FileReader("graph1.txt"));
			else
				br = new BufferedReader(new FileReader(args[0]));
			try {
				String line = br.readLine();

				while (line != null) {
					String[] data = line.split("\\s+");

					if(data[0].compareTo("#") != 0)
					{
						if(!graph.containsKey(data[0]))
							graph.put(data[0], new HashMap<String, Float>());
						graph.get(data[0]).put(data[1], tempProb[ctr%11]);

						if(!graph.containsKey(data[1]))
							graph.put(data[1], new HashMap<String, Float>());
						graph.get(data[1]).put(data[0], tempProb[ctr%11]);

						ctr++;
					}
					line = br.readLine();
				}
			} finally {
				br.close();
			}
		} catch(Exception ex) { }

		System.out.println("Number of vertices: " + graph.size());
		System.out.println("Number of edges: " + ctr);

		Iterator<String> itr = graph.keySet().iterator();
		String filename= "degGraph1.txt";

		try {
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			fw.write("Vertex	Core" + System.getProperty("line.separator"));
			while(itr.hasNext()) {
				String vrtx = itr.next();
				fw.write(vrtx + "	" + graph.get(vrtx).size() + System.getProperty("line.separator"));
			}
			fw.close();
		} catch(Exception ex) { }
		
		float mVal = 0.04f;
		if(args.length > 1)
			mVal = Float.valueOf(args[1]);
		
		long strtTime = System.currentTimeMillis();
		HashMap<String, Integer> c  = (new KNCoreDecomp()).DoDecomposition(graph, mVal);
		strtTime = System.currentTimeMillis() - strtTime;
		System.out.println("Execution time(ms): " + strtTime);

		itr = c.keySet().iterator();
		filename= "opGraph1.txt";

		try {
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			fw.write("Vertex	Core" + System.getProperty("line.separator"));
			while(itr.hasNext()) {
				String vrtx = itr.next();
				fw.write(vrtx + "	" + c.get(vrtx) + System.getProperty("line.separator"));
			}
			fw.close();
		} catch(Exception ex) { }


	}

	/***
	 * 
	 * @param graph 		Key: vertex , value[0] connected vertex, value[1] weight 
	 * @param kCoreValArr
	 * @param neValArr
	 */
	public HashMap<String, Integer> DoDecomposition(HashMap<String, HashMap<String, Float>> graph, float neValArr) {

		// create data structures here
		HashMap<Integer, Vector<String>> D = new HashMap<Integer, Vector<String>>();
		HashMap<String, Integer> c = new HashMap<String, Integer>(); 
		HashMap<String, Integer> d = new HashMap<String, Integer>();
		String[] vertxSet = Arrays.copyOf(graph.keySet().toArray(), graph.keySet().size(), String[].class);

		int ne_DegVal, ne_DegVal2;
		String vertx;

		m_netaThreshold = neValArr;
		m_graph = graph;
		m_kCoreXVals = new HashMap<String, double[]>();

		for(int i = 0; i < vertxSet.length; i++) {
			ne_DegVal = initNeDeg(vertxSet[i]);

			//System.out.println("Initial core number of \"" + vertxSet[i] + "\" is " + ne_DegVal);
			d.put(vertxSet[i], ne_DegVal);

			if(!D.containsKey(ne_DegVal))
				D.put(ne_DegVal, new Vector<String>());
			D.get(ne_DegVal).add(vertxSet[i]);
		}

		//System.out.println("Step 2: recomputing core numbers..");

		for(ne_DegVal = 0; ne_DegVal < graph.size(); ne_DegVal++) {

			if(!D.containsKey(ne_DegVal))
				continue;

			//ne_DegVal = j;

			while(D.get(ne_DegVal).size() > 0) {
				vertx = D.get(ne_DegVal).get(0);
				c.put(vertx, ne_DegVal);

				//System.out.println("Core number of \"" + vertx + "\" is " + ne_DegVal);

				D.get(ne_DegVal).removeElement(vertx);

				// or is deletion here?
				vertxSet = Arrays.copyOf(graph.get(vertx).keySet().toArray(), graph.get(vertx).keySet().size(), String[].class);
				// (String[])graph.get(vertx).keySet().toArray();

				for(int i = 0; i < vertxSet.length; i++) {
					if(d.get(vertxSet[i]) <= ne_DegVal)
						continue;

					ne_DegVal2 = recomputeNeDeg(vertxSet[i], graph.get(vertx).get(vertxSet[i]), d.get(vertxSet[i]));

					D.get(d.get(vertxSet[i])).remove(vertxSet[i]);

					if(!D.containsKey(ne_DegVal2))
						D.put(ne_DegVal2, new Vector<String>());
					D.get(ne_DegVal2).add(vertxSet[i]);
					d.put(vertxSet[i], ne_DegVal2);

				}

				// remove the vertex from graph

				deleteNod(graph, vertx);
			}
		}

		return c;
	}

	private void deleteNod(HashMap<String, HashMap<String, Float>> graph, String node){

		for(String parNodes : graph.get(node).keySet()){
			graph.get(parNodes).remove(node);
		}

		graph.remove(node);
	}

	private int recomputeNeDeg(String vrtx, double pe, int netaDegV){

		int k = 0;
		double[] xVals = m_kCoreXVals.get(vrtx);
		double[] newXVals = new double[netaDegV];

		for(k = 0; k < netaDegV; k++) {
			newXVals[k] = -1;
		}

		//recomputeX(d.get(vrtx), newXVals, vrtx, xVals, d.get(vrtx));

		double prVal = 1;

		for(k = 0; k < netaDegV; k++) {

			prVal -= recomputeX(k, newXVals, vrtx, xVals, pe);

			if(prVal < m_netaThreshold)
				break;
		}

		return k-1;
	}

	private double recomputeX(int i, double[] newXVals, String vrtx, double[] xVals, double pe){

		if(newXVals[i] == -1) {

			if(i == 0)
				newXVals[0] = (xVals[1]/ (1 - pe));
			else
				newXVals[i] = ((xVals[i+1] - (pe * recomputeX(i-1, newXVals, vrtx, xVals, pe)))/ (1 - pe));
		}

		return newXVals[i];
	}

	private int initNeDeg(String vrtx){

		int k = 0;

                double[][] xVals = new double[m_graph.get(vrtx).size()+ 1][m_graph.get(vrtx).size() + 1];

		for(int h = 0; h < m_graph.get(vrtx).size() + 1; h++)
			for(int j = 0; j < m_graph.get(vrtx).size() + 1; j++)
				xVals[h][j] = -2;

		//xVals[m_graph.get(vrtx).size() - 1][0] = 1;
		double prVal = 1;

		for(k = 0; k <= m_graph.get(vrtx).size(); k++) {

			prVal -= computeX(m_graph.get(vrtx).size(), k, vrtx, xVals);

			if(prVal < m_netaThreshold)
				break;
		}

		if(!m_kCoreXVals.containsKey(vrtx))
			m_kCoreXVals.put(vrtx, xVals[m_graph.get(vrtx).size()]);

		/*
		System.out.println("Values of " + vrtx);
		for(int i = 0; i < xVals[m_graph.get(vrtx).size()].length; i++)
			System.out.print(xVals[m_graph.get(vrtx).size()][i] + ", ");
		System.out.println("");
		 */

		return k-1;
	}

	private double computeX(int h, int j, String vrtx, double[][] xVals){

		if(j < 0 || h < 0)
			return 0;

		if(xVals[h][j] == -2) {

			if(h == 0 && j == 1)
				xVals[h][j] = 1;
			else if(h == 0)
				xVals[h][j] = 0;
			else if(j > h)
				xVals[h][j] = 0;
			else
				xVals[h][j] = ((float)m_graph.get(vrtx).values().toArray()[h-1] * computeX(h-1, j-1, vrtx, xVals))
				+ ((1.0 - (float)m_graph.get(vrtx).values().toArray()[h-1]) * computeX(h-1, j, vrtx, xVals)); 
		}
		return xVals[h][j];
	}
}