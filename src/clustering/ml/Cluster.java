/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.ml;

import java.util.ArrayList;
import weka.core.Instance;

/**
 *
 * @author Moch Ginanjar Busiri
 */
public class Cluster{
        private ArrayList<Instance> instance;
        
        public Cluster(ArrayList<Instance> instance) {
            this.instance = instance;
        }
        
        public ArrayList<Instance> getCluster() {
            return this.instance;
        }
        
        public void setCluster(ArrayList<Instance> instance) {
            this.instance = instance;
        }
    }
