/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.ml;

import function.Evaluator;
import function.LoadData;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.HierarchicalClusterer;
import static weka.clusterers.HierarchicalClusterer.TAGS_LINK_TYPE;
import weka.core.Instances;
import weka.core.SelectedTag;

/**
 *
 * @author yoga
 */
public class ClusteringML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Instances instances= LoadData.getData("weather.nominal.arff");
        MyAgnes myagnes= new MyAgnes(2,MyAgnes.COMPLETE);
        MyKMeans mykmeans = new MyKMeans(2, 10);
        
        mykmeans.buildClusterer(instances);
//        mykmeans.printCluster();
        myagnes.buildClusterer(instances);
        //myagnes.printCluster();
        

        HierarchicalClusterer hc= new HierarchicalClusterer();
        hc.setLinkType(new SelectedTag(1, TAGS_LINK_TYPE));
        hc.buildClusterer(instances);
        Evaluator.evaluate(hc, instances);
        System.out.println("-----------------------------");
        Evaluator.evaluate(mykmeans, instances);

    }
    
}
