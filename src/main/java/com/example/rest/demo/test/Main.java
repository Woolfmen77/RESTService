//package com.example.rest.demo.test;
//
//public class Main {
//    public static void main(String[] args) throws InterruptedException {
//
//        Object sync = new Object();
//
//        new Thread(() -> {
//            synchronized (sync){
//                try {
//                    System.out.println("Ждем..........");
//                    sync.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Проснулся");
//                int i = 0;
//                while (i < 100){
//                    System.out.println("*********   *********    ************");
//                    i+=5;
//                }
//            }
//        }).start();
//
//        Thread.sleep(5000);
//
//        new Thread(()-> {
//            synchronized (sync) {
//                System.out.println( "Нотифай");
//                sync.notify();
//                System.out.println( "Нотифай..............");
//            }
//        }).start();
//    }
//}
