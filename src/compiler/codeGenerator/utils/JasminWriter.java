package compiler.codeGenerator.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The JasminWriter class outputs JVM instructions to a file with the ".j" extension
 * and name equal to the class name.  
 */
public final class JasminWriter {

    private PrintWriter writer;

    /**
     * Constructor of the JasminWriter class
     * @param className The name of the class. This is the name of the file being generated.
     */
    public JasminWriter(String className) {

        File file = new File(Config.classFilesPath, className + ".j");
        File path = new File(Config.classFilesPath);
        try {
            if(!path.exists())path.mkdirs();
            if(!file.exists())file.createNewFile();
            this.writer = new PrintWriter(file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR: could not generate .j file for class " + className);
            System.exit(1);
        } catch(IOException e)
        {
            System.out.println("ERROR: could not create .j file for class " + className);
            System.exit(1);
        }
    }

    /**
     * Writes a set of components to the file, in the same order as they are provided
     * @param fileComponents The components to be written to the file.
     */
    public void writeFile(String... fileComponents) {
        for(String component: fileComponents) {
            this.writer.write(component + '\n');
        }
        this.writer.flush();
    }

    @Override 
    protected void finalize() throws Throwable {
        this.writer.close();
    }
}