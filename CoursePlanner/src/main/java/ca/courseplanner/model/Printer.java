package ca.courseplanner.model;

import java.util.List;

public class Printer {

    public static void printToTerminal(List<OfferingData> offeringDataList) {
        OfferingData firstOfferingData = offeringDataList.get(0);
        String currentCourseName = firstOfferingData.getCourseName();
        String currentSemester = firstOfferingData.getSemester();
        String currentLocation = firstOfferingData.getLocation();

        printCourseName(currentCourseName);
        printSectionInfo(firstOfferingData);

        for (OfferingData offeringData : offeringDataList) {
            if (!offeringData.getCourseName().equals(currentCourseName)) {
                currentCourseName = offeringData.getCourseName();
                printCourseName(currentCourseName);
                printSectionInfo(offeringData);
                printCourseType(offeringData);
            } else if (offeringData.getCourseName().equals(currentCourseName)
                    && offeringData.getLocation().equals(currentLocation)
                    && offeringData.getSemester().equals(currentSemester)) {

                    printCourseType(offeringData);
            } else if (offeringData.getCourseName().equals(currentCourseName)
                    && (!offeringData.getLocation().equals(currentLocation)
                    || !offeringData.getSemester().equals(currentSemester))) {

                printSectionInfo(offeringData);
                printCourseType(offeringData);
            }
            currentLocation = offeringData.getLocation();
            currentSemester = offeringData.getSemester();
        }
    }

    private static void printCourseName(String currentCourseName) {
        System.out.println(currentCourseName);
    }

    private static void printCourseType(OfferingData offeringData) {
        System.out.println("\t\t" + "Type=" + offeringData.getComponent()
                + ", Enrollment=" + offeringData.getEnrollmentTotal()
                + "/" + offeringData.getEnrollmentCap());
    }

    private static void printSectionInfo(OfferingData offeringData) {
        System.out.println("\t" + offeringData.getSemester() + " in "
                + offeringData.getLocation() + " by "
                + offeringData.getInstructor());
    }

}
