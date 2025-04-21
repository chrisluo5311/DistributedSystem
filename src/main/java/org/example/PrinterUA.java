package org.example;
import ch.ethz.iks.slp.*;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class PrinterUA {
    public static void main(String[] args) throws Exception {
        // --- 1.  obtain a Locator ------------------------------------------
        Locator locator = ServiceLocationManager.getLocator(Locale.ENGLISH);

        // --- 2.  look for services of the desired type ----------------------
//        ServiceType lprType = new ServiceType("service:meow");
        ServiceType lprType = new ServiceType("service:dog");
        // parameters:  type, scopes (null ⇒ default), attrs (null ⇒ no filter)
        ServiceLocationEnumeration results   = locator.findServices(lprType, null, null);

        // --- 3.  present the results ---------------------------------------
        if (!results.hasMoreElements()) {
            System.out.println("No meow printers found.❌");
        } else {
            System.out.println("✅Discovered printers:");
            while (results.hasMoreElements()) {
                ServiceURL serviceURL = (ServiceURL) results.nextElement();
                System.out.println(" • " + serviceURL);

                // Retrieve and print attributes for the serviceURL
                ServiceLocationEnumeration attributes = locator.findAttributes(serviceURL, null, null);
                Set<Object> uniqueAttributes = new HashSet<>();
                while (attributes.hasMoreElements()) {
                    uniqueAttributes.add(attributes.nextElement());
                }
                for (Object attribute : uniqueAttributes) {
                    System.out.println(attribute);
                }
            }
        }
    }
}
