package XMLHandling;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class Decompression {
    public static void decompressGzip(Path source, Path target) throws IOException {
        try (GZIPInputStream gis = new GZIPInputStream(
                new FileInputStream(source.toFile()))) {
            Files.copy(gis, target);
        }
    }

    public static void main(String[] args) throws IOException {
        Path source = Paths.get("dblp.xml.gz");
        Path target = Paths.get("newXML.xml");
        decompressGzip(source, target);
    }
}
