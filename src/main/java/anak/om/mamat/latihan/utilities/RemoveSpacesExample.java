package anak.om.mamat.latihan.utilities;

public class RemoveSpacesExample {
    public static void main(String[] args) {
        String originalString = "   Hello, World!   skfjsklfjkslf      ";
        System.out.println("Original: '" + originalString + "'");

        // Menggunakan replaceAll() untuk menghapus semua spasi di dalam string
        // String stringWithoutSpaces = originalString.replaceAll("\\s+", "");
        // System.out.println("Without spaces: '" + stringWithoutSpaces + "'");

        String trimmedString = originalString.trim();
        System.out.println("Without spaces: '" + trimmedString + "'");
    }
}
