import java.lang.Math;
import java.util.ArrayList;


import medianProblem.*;
public class App {
    public static void main(String[] args){
    	long startTime = System.currentTimeMillis();
    	
    	int p=3;// Number of facilities to open
    	int facilityNumber = 20;
    	int pointNumber = 60;

        Operators operator = new Operators();
        double[][] facs_coordinates = operator.createDistanceMatrix(facilityNumber,2);
        double[][] points_coordinates = operator.createDistanceMatrix(pointNumber,2);
        Facility[] facs = new Facility[facilityNumber];
        Point[] points = new Point [pointNumber];
        
        System.out.println("Initial Supply Capacities of facilities");
        for (int i=0; i<facs.length;i++){
            Facility f1 = new Facility(i, facs_coordinates[i][0], facs_coordinates[i][1], 20*Math.random()+50);
            facs[i] = f1;
            System.out.println("Facility "+i+": "+ facs[i].supply);
        }

        System.out.println("Initial Demand Amounts of facilities");
        double totalPointDemand = 0;
        for (int i=0; i<points.length;i++){
            Point p1 = new Point (i, points_coordinates[i][0], points_coordinates[i][1], 2*Math.random()+1);
            points[i] = p1;
            System.out.println("Point " + i + ": " + points[i].getDemand() );
            totalPointDemand += points[i].getDemand();
            
        }
        
       

        ArrayList<Integer> openedFacilities = new ArrayList<>();
        ArrayList<Double> totalDistances = new ArrayList<>();
        ArrayList<ArrayList<Point>> assignmentList = new ArrayList<>();
        ArrayList<ArrayList<Point>> tempAssignmentList = new ArrayList<>();
        Facility[] tempFacs = new Facility[facs.length];
        
        
        
        for(int y=0;y<p;y++) {
        	
        	System.out.println("--");
        	System.out.println("---  ITERATION " + (y+1)+ "  ---");
        	System.out.println("--");
        	
        	totalDistances.clear();
        
        	double min = 1000000;// M to sort
        	int facToAddID = 0; //we will add best facility from this iteration
        	
        	for(int x=0;x<facs.length;x++) {
        		if(openedFacilities.contains(x)==false) {// Looking for facility x is already open or not
               		
               	    for(int i=0;i<tempFacs.length;i++) {
               	    	tempFacs[i]=new Facility(0,0,0,facs[i].supply);
               	    }
               		
            		for(int i=0;i<facs.length;i++) {
        	        	ArrayList<Point> pointList = new ArrayList<Point>();
        	        	tempAssignmentList.add(pointList);
            		}

               		//System.out.println("---TRYING TO ADD FACILITY "+ x+"---");
               		openedFacilities.add(x); //trying to add facility x -> check its total distance
                    double[][] distanceMatrix = operator.distanceMatrix(facs, points);
                    double totalDist = 0;
                    int numberOfAssignments = 0;
                     
//tentative ASSIGNMENTs of all points 
//WITH DEMAND
                    double totalSupplyOpened = 0;
                    
                    for(int j=0;j<openedFacilities.size();j++) {
                    	totalSupplyOpened += facs[openedFacilities.get(j)].getSupply();
                    }
                    if(totalSupplyOpened >= totalPointDemand) {
                    	 while(numberOfAssignments<points.length) {
                         	
                            	int minPointIndex = (int) operator.findMinIndex(distanceMatrix,openedFacilities)[0][1];
                            	int minFacIndex = (int) operator.findMinIndex(distanceMatrix,openedFacilities)[0][0];
                            	double minDist = operator.findMinIndex(distanceMatrix,openedFacilities)[0][2];
                            	
                            		if(tempFacs[minFacIndex].getSupply() >= points[minPointIndex].getDemand()) {  //ENOUGH SUPPLY
                                	tempAssignmentList.get(minFacIndex).add(points[minPointIndex]);
                                	
                                	for(int j=0;j<facs.length;j++) {// from dist matrix - Removing points which are assigned - point them as assigned!
                                		distanceMatrix[j][minPointIndex] = -1;
                                	}
                                	System.out.println("Facility "+ minFacIndex +" is assigned to point "+ minPointIndex +" and it has a distance of "+ minDist);
                                	totalDist += minDist;
                                	tempFacs[minFacIndex].supply-=(points[minPointIndex].getDemand());
                                	numberOfAssignments++;
                                	System.out.println("SUPPLY left of this fac: " + tempFacs[minFacIndex].getSupply());
                                	
                            		}else { //NOT ENOUGH
                            		distanceMatrix[minFacIndex][minPointIndex] = -1; 
                            	}
                         }
                    }else {//WITHOUT DEMAND
                        while(numberOfAssignments<points.length) {
                        	int minPointIndex = (int) operator.findMinIndex(distanceMatrix,openedFacilities)[0][1];
                           	int minFacIndex = (int) operator.findMinIndex(distanceMatrix,openedFacilities)[0][0];
                           	double minDist = operator.findMinIndex(distanceMatrix,openedFacilities)[0][2];
                           	
                           	tempAssignmentList.get(minFacIndex).add(points[minPointIndex]);
                           	
                           	for(int j=0;j<facs.length;j++) {// from dist matrix - Removing points which are assigned - point them as assigned!
                           		distanceMatrix[j][minPointIndex] = -1;
                           	}
                           	System.out.println("Facility "+ minFacIndex +" is assigned to point "+ minPointIndex +" and it has a distance of "+ minDist);
                           	totalDist += minDist;
                           	numberOfAssignments++;
                        }
                    }  
                    
                    System.out.println("Total distance is "+totalDist);
                    System.out.println();
                    openedFacilities.remove(new Integer(x));   
                    
                    if(totalDist<min) {
                    	assignmentList.clear();
                    	  for(int n=0;n<facs.length;n++) {
                          	assignmentList.add(n, new ArrayList<Point>());
                          }
                    	facToAddID=x;
                    	min=totalDist;
                    	for(int i=0;i<facs.length;i++) {
                    		for(int j=0;j<tempAssignmentList.get(i).size();j++) {
                    			assignmentList.get(i).add(tempAssignmentList.get(i).get(j)) ;
                    		}
                    	}
                    }
                    tempAssignmentList.clear();
                 }
        	}
        	System.out.println("Facility " + facToAddID + " is added and the total distance is "+ min );
            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            openedFacilities.add(facToAddID);
            
        }
        System.out.println("::::::::::::::::::::");
        System.out.println(":::: :SOLUTION: ::::");
        System.out.println("::::::::::::::::::::");
        boolean unfeasible = false;
        for(int f: openedFacilities) {
        	System.out.print("Facility " + f +  " will serve to the points: ");
        	double totalDemand = 0;
    		for(int j=0;j<assignmentList.get(f).size();j++) {
    			System.out.print((int)assignmentList.get(f).get(j).getId() + ", ");
    			totalDemand += assignmentList.get(f).get(j).getDemand();
    		}
    		double remainingSupply = facs[f].getSupply() - totalDemand;
    		System.out.print(" It supplied: " + totalDemand + " and its remaining supply is " + remainingSupply);
    		System.out.println();
    		if(remainingSupply < 0) {
    			unfeasible = true;
    		}
        }
        if(unfeasible) {
        	System.out.println("!!!!!! WARNING !!!!!!");
        	System.out.println("We couldn't find a feasible solution with p="+p);
        	System.out.println("You need to increase number of facilities to be opened(p)!!!!");
        }
        
        
        long endTime = System.currentTimeMillis();
        double searchTime = (double)(endTime - startTime);
        
        System.out.println();
        System.out.println("execution time is "+ searchTime/1000 + " sec");
    }
}