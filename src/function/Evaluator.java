/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import weka.clusterers.AbstractClusterer;
import weka.clusterers.ClusterEvaluation;
import weka.core.Instances;

/**
 *
 * @author yoga
 */
public class Evaluator {

    public static void evaluate(AbstractClusterer ac, Instances ins) throws Exception {
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(ac);
        eval.evaluateClusterer(ins);
        System.out.println(eval.clusterResultsToString());
    }
}
