import java.lang.Math;
import java.util.ArrayList;

import medianProblem.*;
public class App {
    public static void main(String[] args){
        Operators operator = new Operators();
        double[][] facs_coordinates = operator.createDistanceMatrix(5,2);
        double[][] points_coordinates = operator.createDistanceMatrix(20,2);
        Facility[] facs = new Facility[5];
        Point[] points = new Point [20];
        for (int i=0; i<facs.length;i++){
            Facility f1 = new Facility(i, facs_coordinates[i][0], facs_coordinates[i][1], 20*Math.random()+50);
            facs[i] = f1;
        }
        for (int i=0; i<points.length;i++){
            Point p1 = new Point (i, points_coordinates[i][0], points_coordinates[i][1], 2*Math.random()+1);
            points[i] = p1;
        }
        double[][] distanceMatrix = operator.distanceMatrix(facs, points);
        ArrayList<Facility> openedFacilities = new ArrayList<>();
        ArrayList<Point> unassignedPoints = new ArrayList<>();
        ArrayList<ArrayList<Point>> assignmentList = new ArrayList<>();
        for (int i=0;i<points.length;i++){
            unassignedPoints.add(points[i]);
        }
        while (unassignedPoints.size() != 0){
            if (openedFacilities.size() > 0){
                int[][] assignment = operator.findMinIndex(distanceMatrix);
                if (openedFacilities.get(openedFacilities.size()-1).supply - assignment[0][1] > 0 ){
                    openedFacilities.get(openedFacilities.size()-1).supply -= points[assignment[0][1]].demand;
                    unassignedPoints.remove(points[assignment[0][1]]);
                    assignmentList.get(openedFacilities.size()-1).add(points[assignment[0][1]]);
                }
                else{
                    // distance matrixte önceki lastı maxla, sonrasında yeniden findmin kullan
                }
            }
            else{
                // sıfırdan distance aç
            }
        }
    }
}



