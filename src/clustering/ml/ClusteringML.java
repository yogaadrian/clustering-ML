/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.ml;

import function.LoadData;
import weka.core.Instances;

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
        myagnes.buildClusterer(instances);
        myagnes.printCluster();
    }
    
}
