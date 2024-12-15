package codepilotunittest.representations;

/**
 * Base interface for all source code elements in the project.
 * Acts as a common abstraction for classes, methods, and other source elements.
 */
public interface SrcElement {
    /**
     * Provides a string representation of the element for debugging or display purposes.
     *
     * @return A string describing the element.
     */
    String toString();
}
