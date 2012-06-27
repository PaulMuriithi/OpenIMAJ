/**
 * Copyright (c) 2011, The University of Southampton and the individual contributors.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * 	Redistributions of source code must retain the above copyright notice,
 * 	this list of conditions and the following disclaimer.
 *
 *   *	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 *
 *   *	Neither the name of the University of Southampton nor the names of its
 * 	contributors may be used to endorse or promote products derived from this
 * 	software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.openimaj.tools.similaritymatrix.modes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.openimaj.math.matrix.similarity.SimilarityMatrix;

public class ConnectedComponents extends ThresholdOption implements ToolMode {
	@Override
	public void process(SimilarityMatrix matrix, File output) throws Exception {
		UndirectedGraph<String, DefaultEdge> graph = matrix.toUndirectedUnweightedGraph(threshold);
		
		ConnectivityInspector<String, DefaultEdge> conn = new ConnectivityInspector<String, DefaultEdge>(graph);
		List<Set<String>> sets = conn.connectedSets();
		
		if (output == null) {
			for (int i=0; i<sets.size(); i++) {
				System.out.println("== Set #" + i + " ==");
				for (String s : sets.get(i)) {
					System.out.println(s);
				}
				System.out.println();
			}
		} else { 
			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			
			for (int i=0; i<sets.size(); i++) {
				bw.write("== Set #" + i + " ==\n");
				for (String s : sets.get(i)) {
					bw.write(s + "\n");
				}
				bw.write("\n");
			}
			
			bw.close();
		}
	}
}