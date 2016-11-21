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
    private Instance[] centroids;
    
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
        int instanceLength = instances.get(0).numAttributes();
        centroids = new Instance[numCluster];
        for(int i = 0; i < numCluster; i++){
            int random = ThreadLocalRandom.current().nextInt(0, instanceLength + 1);
            centroids[i] = instances.get(random);
        }
        
        boolean convergence = false;
        int iterationCount = 0;
        int numInstance = instances.size();
        while(!convergence || iterationCount < numIteration){
            // assign each object to the group that has the closest centroid
            int[] assignment = new int[numInstance];
            for(int i = 0; i < numInstance; i++){
                int tmpCluster = 0;
                double minDistance = Double.MAX_VALUE;
                for(int j = 0; j < numCluster; j++){
                    double distance = distanceFunction.distance(instances.get(i), centroids[j]);
                    if(distance < minDistance){
                        tmpCluster = j;
                        minDistance = distance;
                    }
                }
                assignment[i] = tmpCluster;
            }
            iterationCount++;
            
            // recalculate the position of centroids
            double[][] sumPosition = new double[numCluster][instanceLength];
            int[] countPosition = new int[numCluster];
            for(int i = 0; i < numInstance; i++){
                Instance in = instances.get(i);
                for(int j = 0; j < instanceLength; j++){
                    sumPosition[assignment[i]][j] += in.weight() * in.value(j);
                }
                countPosition[assignment[i]]++;
            }
        }
    }

    @Override
    public int numberOfClusters() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
