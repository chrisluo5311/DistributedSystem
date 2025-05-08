//package org.example.jslp;
//
//import ch.ethz.iks.slp.*;
//import java.net.InetAddress;
//import java.util.Dictionary;
//import java.util.Hashtable;
//import java.util.Locale;
//
//public class PrinterSA{
//    public static void main(String[] args) throws Exception {
//        // --- 1.  basic information we want to publish -----------------------
//        String hostIp   = InetAddress.getLocalHost().getHostAddress();
//        String urlStr1   = "service:meow://" + hostIp + "/printer-meow-meow";
//        String urlStr2   = "service:dog://" + hostIp + "/printer-woof-woof";
//        ServiceURL url1  = new ServiceURL(urlStr1, ServiceURL.LIFETIME_DEFAULT);
//        ServiceURL url2  = new ServiceURL(urlStr2, ServiceURL.LIFETIME_DEFAULT);
//
//        // RFC‑2608 attribute syntax  ➜  "(key=value)"
//        Dictionary<String, String> attrs1 = new Hashtable<>();
//        attrs1.put("Printer-type", "Meow meow ultra");
//        attrs1.put("Power-on", "true");
//        attrs1.put("location", "Meow Building 2nd floor");
//
//        Dictionary<String, String> attrs2 = new Hashtable<>();
//        attrs2.put("Printer-type", "Woof woof ultra");
//        attrs2.put("Power-on", "false");
//        attrs2.put("location", "Woof Building 1nd floor");
//
//        // --- 2.  obtain an Advertiser and register --------------------------
//        Advertiser advertiser = ServiceLocationManager.getAdvertiser(Locale.ENGLISH);
//        advertiser.register(url1, attrs1);          // may throw ServiceLocationException
//        advertiser.register(url2, attrs2);          // may throw ServiceLocationException
//        System.out.println("✔  Registered printer:  " + urlStr1);
//        System.out.println("✔  Registered printer:  " + urlStr2);
//
//        // --- 3.  keep the process alive so that the advertisement survives --
//        // (You would normally hook this into the life‑cycle of your real service.)
//        Thread.sleep(10 * 60 * 1000);
//
//        // --- 4.  tidy up -----------------------------------------------------
//        advertiser.deregister(url1);
//        advertiser.deregister(url2);
//        System.out.println("✖  Deregistered printer and exiting.");
//    }
//}
