package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class InvalidFileException extends Exception {
    private final int lineWithError;
    public InvalidFileException(int lineWithError) {
        this.lineWithError = lineWithError;
    }

    int getLineWithError() {
        return lineWithError;
    }
}
