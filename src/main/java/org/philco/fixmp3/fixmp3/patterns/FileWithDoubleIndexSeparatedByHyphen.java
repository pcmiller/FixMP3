package org.philco.fixmp3.fixmp3.patterns;

public class FileWithDoubleIndexSeparatedByHyphen implements Antipattern {
    final String pattern = "([0-9]+)-([0-9]+)(.*)";
    final String replacement = "$2$3";

    @Override
    public boolean match(String filename) {
        String first = filename.replaceFirst(pattern, "$1");
        String second = filename.replaceFirst(pattern, "$2");

        try {
            int firstNumber = Integer.parseInt(first);
            int secondNumber = Integer.parseInt(second);
            return firstNumber == secondNumber;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String fix(String filename) {
        return filename.replaceFirst(pattern, replacement);
    }
}
