package com.example.googlemapsapipractice;

import java.util.ArrayList;

public class TSP {
    final int INT_MAX = 99999999;
    public static int n = 4;
    //mang sau de luu khoang cach giua cac dinh
    public static double[][] dist = new double[1000][1000];
    //mang sau de luu cac khoang cach da duoc tinh truoc (quy hoach dong):
    public static double[][] tempDist = new double[1000][1000];
    //string luu tien trinh di:
    public static int[][] path = new int[1000][1000];
    public static int VISITED_ALL = (1 << n) - 1;
    public static int start;
    public static String pathString = "";
    public static ArrayList<Integer> pathArr;

    public static double CalculateTSP(int mask, int pos) {
        //neu da tham het cac dinh thi return:
        if (mask == VISITED_ALL) {
            return dist[pos][0];
        }

        //neu khoang cach da duoc tinh truoc thi tai su dung:
        if (tempDist[mask][pos] != -1) {
            return tempDist[mask][pos];
        }

        double minWeight = 999999;
        int minPos = 999999;
        //dung for de tim den nhung dinh chua duoc tham
        for (int i = 0; i < n; i++) {
            //neu chua dinh chua duoc tham thi:
            if ((mask & (1 << i)) == 0) {
                double newWeight = dist[pos][i] + CalculateTSP(mask | (1 << i), i);
                if (minWeight > newWeight) {
                    minWeight = newWeight;
                    minPos = i;
                }
            }
        }
        //luu vi tri cac dinh de quang duong ngan nhat
        path[mask][pos] = minPos;

        return tempDist[mask][pos] = minWeight;
    }
    //ham chay sau khi da tinh duoc TSP
    public static void showPath_TSP(int mask, int pos) {
        if (pos == start) {
            pathString = start + " ";
            pathArr.add(start);
        }
        if (path[mask][pos] != -1) {
            int minPos = path[mask][pos];
            pathString += minPos + " ";
            pathArr.add(minPos);
            showPath_TSP(mask | (1 << minPos), minPos);
        } else {
            pathString += start + " ";
            pathArr.add(start);
        }
    }
    public static void init() {
        pathArr = new ArrayList<>();
        path = new int[1 << n][1000];
        tempDist = new double[1 << n][1000];
        VISITED_ALL = (1 << n) - 1;
        //khoi tao 1 mang ban luu khoang cach da duoc tinh, gia tri ban dau la -1
        for (int i = 0; i < (1 << n); i++) {
            for (int j = 0; j < n; j++) {
                tempDist[i][j] = -1;
            }
        }
        for (int i = 0; i < (1 << n); i++) {
            for (int j = 0; j < n; j++) {
                path[i][j] = -1;
            }
        }
    }
}
