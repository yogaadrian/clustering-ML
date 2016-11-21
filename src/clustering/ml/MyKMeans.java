/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.ml;

import static clustering.ml.MyAgnes.distanceFunction;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import weka.clusterers.AbstractClusterer;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Azwar
 */
public class MyKMeans extends AbstractClusterer{
    private int numCluster = -1;
    private int numIteration = -1;
    private Random rg;
    private static DistanceFunction distanceFunction = new EuclideanDistance();
    private Instances instances;
    private ArrayList<Cluster> centroids = new ArrayList<Cluster>();
    
    public MyKMeans(int cluster, int iteration){
        this.numCluster = numCluster;
        this.numIteration = iteration;
        rg = new Random(System.currentTimeMillis());
    }

    @Override
    public void buildClusterer(Instances data) throws Exception {
        instances = data;
        distanceFunction.setInstances(instances);
        
        if(data.size() == 0){
            throw new RuntimeException("The dataset should not be empty");
        }
        if(numCluster == 0){
            throw new RuntimeException("There should be at least one cluster");
        }
        
        // initialize centroid
        int instanceLength = instances.size();
        for(int i = 0; i < numCluster; i++){
            int random = ThreadLocalRandom.current().nextInt(0, instanceLength + 1);
            centroids.add((Cluster) instances.get(random));
        }
        
        // assign instances to centroid until iteration or convegence
        boolean convergence = false;
        int iteration = 0;
        while(!convergence || iteration < numIteration){
            for(int i = 0; i < instanceLength; i++){
                double minDistance = Double.MAX_VALUE;
                for(int j = 0; j < numCluster; j++){
                    double distance = distanceFunction.distance(instances.get(i), (Instance) centroids.get(j));
                    
                }
            }
            
            iteration++;
        }
        
    }

    @Override
    public int numberOfClusters() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
