/**
 * Retrieves JMV information like number of threads running,
 * memory usage, etc.
 *
 * @author Damian Fanaro (damianfanaro@gmail.com)
 */
public final class AppInformation {

    public static String getCurrentState() {

        StringBuilder result = new StringBuilder();

        int mb = 1024 * 1024;

        Runtime runtime = Runtime.getRuntime();

        double usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / mb;
        double freeMemory = runtime.freeMemory() / mb;
        double totalMemory = runtime.totalMemory() / mb;
        double maxMemory = runtime.maxMemory() / mb;

        result.append("APP STATUS (JSON format) -> ");
        result.append("{");
        result.append("\"usedMemory\"").append(":").append("\"").append(Double.toString(usedMemory)).append("\"").append(", ");
        result.append("\"freeMemory\"").append(":").append("\"").append(Double.toString(freeMemory)).append("\"").append(", ");
        result.append("\"totalMemory\"").append(":").append("\"").append(Double.toString(totalMemory)).append("\"").append(", ");
        result.append("\"maxMemory\"").append(":").append("\"").append(Double.toString(maxMemory)).append("\"");
        result.append("}");

        return result.toString();
    }
}
